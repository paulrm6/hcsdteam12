/**
* DetailsManager.java serve as a data management control. Most of the classes housed here are more for testing purposes, to see whether
* data can be stored and retrieved effectively.
*
* @author Seng Kin(Terence), Kong.
**/

package hcsdteam12.calendar.data;

import java.util.*;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

//Manages all the data that is retrieved from and written onto the hard drive.
public class DetailsManager {
    private static String FILE_NAME = "calendar.xml";
    private static boolean AUTO_SAVE = true;
    private Document record;
    private boolean legitimate;
    private String fileName;
    private boolean selfSave;
    
    //Builds new DetailsManager with the given FILE_NAME and use the AUTO_SAVE configuration before storing in home directory.
    public DetailsManager() throws IOException {
        this(System.getProperty("user.dir") + System.getProperty("file.divider") + FILE_NAME);
    }
    
    //Builds a new info manager with the stated filename 
    public DetailsManager(String fileName) throws IOException {
        this(fileName, AUTO_SAVE);
    }

    //Builds a new info manager with the stated filename and autosave.
    public DetailsManager(String fileName, boolean selfSave) throws IOException {
        String divider = System.getProperty("line.divider");
        legitimate = false; //If all components are not initialized properly.
        this.selfSave = selfSave;
        setFileName(fileName);
       
        //Step 1 of reading an XML document: put together the factory and its configurations
        DocumentBuilderFactory assemble = DocumentBuilderFactory.newInstance();
        assemble.setValidating(false);
        assemble.setIgnoringComments(false);
        assemble.setIgnoringElementContentWhitespace(false);
        assemble.setCoalescing(false);
        assemble.setExpandEntityReferences(true);
        
        //Step 2 of reading an XML document: produce a DocumentBuilder
        DocumentBuilder produce = null;
        try {
        	produce = assemble.newDocumentBuilder();
        }
        catch (ParserConfigurationException pass) {
            throw new IOException("Misconfigured parser" + divider + pass.getMessage());
        }
        produce.setErrorHandler(new XML());
        
        //Step 3 of reading an XML document: divide the file into parts that is required to display info on calendar.
        Document rec = null;
        try {
            File direct = new File(fileName);
            if (direct.isFile()) {
            	rec = produce.parse(direct);
            }
            else {
                String newFile = "<?xml version=\"1.0\"?><calendar/>";
                InputSource inputorigin = new InputSource(new StringReader(newFile));
                rec = produce.parse(inputorigin);
            }
        }
        catch (SAXException pass) {
            throw new IOException("Failed to read file." + divider + pass.getMessage());
        }
        establishDocument(rec);
        legitimate = true;
    }
    
    //Returns a list of appointments between 2 dates. It includes the first day but excludes the last day given.
    public List obtainAppointmentBetweenDates(GregorianCalendar startDateBound, GregorianCalendar endDateBound) throws OutOfDateHandler {
        boolean analyze = false;
        if (isAppointmentLegitimate() == false) {
            return null; //If appointment is not valid
        }
        
        if (!startDateBound.before(endDateBound)) { //Ensure that the first day is before 
            throw new OutOfDateHandler("The first date must be before the second date.");
        }
        
        LinkedList Days = new LinkedList();
        
        //Generate Day object with the starting data and add to linked list
        GregorianCalendar nextDay = (GregorianCalendar) startDateBound.clone();
        while (nextDay.before(endDateBound)) {
        	Days.add(new Day(nextDay));
            nextDay.add(nextDay.DAY_OF_MONTH, 1);
        }
        if (analyze) {
            System.out.println("=========================");
            System.out.println("Retrieving appointments: ");
        }
        
        Document rec = retrieveDocument();
        Element source = rec.getDocumentElement();
        
        if (analyze) {
            System.out.println("Source node: " + source.getTagName());
            System.out.println("The next series of nodes should just be appointments.");
        }
        
        NodeList consults = source.getChildNodes();
        for (int i = 0; i < consults.getLength(); i++) {
            Element presentAppointment = (Element)consults.item(i);
            
            if (analyze) {
                System.out.println("Nodes under the top node: " + presentAppointment.getTagName());
            }
            
            NodeList collectNodes = presentAppointment.getChildNodes(); //Obtain the value of the fields of an appointment stored in the nodes.
            Hashtable labels = new Hashtable();
            if (analyze) {
                System.out.println("About to read each label of the appointment");
            }
            for (int j = 0; j < collectNodes.getLength(); j++) {
                Element presentLabel = (Element) collectNodes.item(j);
                String fieldName = presentLabel.getTagName();
                if (analyze) {
                    System.out.println("Reading label: " + fieldName);
                }
                String labelValue = "";
                NodeList fieldValueNodes = presentLabel.getChildNodes();
                for (int k = 0; k < fieldValueNodes.getLength(); k++) {
                    Text string = (Text)fieldValueNodes.item(k);
                    labelValue += string.getData();
                }
                if (analyze) {
                    System.out.println("Reading the value of the label: " + labelValue);
                }
                labels.put(fieldName, labelValue);
            }
            
            //Construct a new Appointment object with the data found
            Appointment consult = new Appointment( 
                        Integer.parseInt((String)labels.get("Start Hour")),
                        Integer.parseInt((String)labels.get("Start Minute")),
                        Integer.parseInt((String)labels.get("Start Day")),
                        Integer.parseInt((String)labels.get("Start Month")),
                        Integer.parseInt((String)labels.get("Start Year")),
                        Integer.parseInt((String)labels.get("Duration")),
                        (String)labels.get("Patient Name"), (String)labels.get("Healthcare Plan"), (String)labels.get("Patient ID"),
                        Integer.parseInt((String)labels.get("Alert Time")),
                        (String)labels.get("Partner"), (String)labels.get("Description"));
            
            LinkedList reoccuringList = new LinkedList();
            StringTokenizer token = new StringTokenizer((String)labels.get("Reoccur On Day"));
            while (token.hasMoreTokens()) {
                reoccuringList.add(token.nextToken(","));
            }
            int[] sortReoccurList = new int[reoccuringList.size()];
            for (int j = 0; j < reoccuringList.size(); j++) {
                sortReoccurList[j] = Integer.parseInt((String)reoccuringList.get(j));
            }
            
            consult.establishRecurrence(sortReoccurList, Integer.parseInt((String)labels.get("Reoccur On")), Integer.parseInt((String)labels.get("Frequency Of Reoccurence")), Integer.parseInt((String)labels.get("Number of Reoccurences")));
            
            if (analyze) {
                System.out.println("Working out the reoccurences of an appointment.");
            }
            //Figure out which days the appointment occurs on
            LinkedList daysAppointmentReoccur = obtainReoccurences(consult, startDateBound, endDateBound);
            if (analyze) { 
                System.out.println("This appointment is scheduled on: ");
            }

            //Figure out the number of days between the 1st day and the reoccuring day before adding the appointment to the Day object.
            int numberOfDaysBetweenReoccurence = 0;
            nextDay = (GregorianCalendar)startDateBound.clone();
            Iterator iterate = daysAppointmentReoccur.iterator();
            while (iterate.hasNext()) {
                GregorianCalendar apptOccursOn = (GregorianCalendar)iterate.next();                
                if (analyze) {
                    System.out.println("\t" + apptOccursOn);
                }
                while(nextDay.before(apptOccursOn)) {
                	numberOfDaysBetweenReoccurence++;
                    nextDay.add(nextDay.DAY_OF_MONTH, 1);
                }
                Day calDayOfAppt = (Day)Days.get(numberOfDaysBetweenReoccurence);
                calDayOfAppt.addAppointment(consult);
            }
            if (analyze) {
                System.out.println("This appointment is done.");
            }
        }
        return Days;
    }
    
    //Takes an appointment and builds a linked list, each of which represent the day when the appointment is supposed to take place.
    private static LinkedList obtainReoccurences(Appointment consultation, GregorianCalendar startDate, GregorianCalendar stopDate) {
        LinkedList output = new LinkedList();
        if (!startDate.before(stopDate)) {
            return output;
        }
        
        //Manages the first reoccurence.
        GregorianCalendar occurrenceDay = new GregorianCalendar(consultation.retrieveStartYear(), consultation.retrieveStartMonth(), consultation.retrieveStartDay());
        
        //If the first reoccurence is after stopDate, then it is redundant when the reoccurence returns because those dates has to be after stopDate too.
        if (!occurrenceDay.before(stopDate)) {
            return output;
        }
        
        //Caps the number of reoccurence the appointment can have.
        for (int i = 0; i < consultation.getNumberOfReoccurences()+1; i++) {
            if (!occurrenceDay.before(startDate)) {
            	output.add(occurrenceDay); //Place the reoccuring day into the linked list if it is after the StartDate
            }
            occurrenceDay = obtainNextReoccurence(consultation, occurrenceDay);
            if (occurrenceDay == null) {
                break; //Figure out the next day to be reoccured.
            }
            if (!occurrenceDay.before(stopDate)) {
                break; //Keep reoccuring while the day of recouccurence is still within limits.
            }
        }
        return output;
    }

    //Determine the next day in which the appointment is supposed to reoccur.
    private static GregorianCalendar obtainNextReoccurence(Appointment consultation, GregorianCalendar day) {
        if (!consultation.isAppointmentReoccuring()) {
            return null; //If no reoccurence is set for an appointment.
        }
        
        GregorianCalendar nextReoccurenceDay = (GregorianCalendar)day.clone();
        
        //To be determined by the reoccurence configured when the appointment was first made.
        switch (consultation.getRetrieveReoccurOn()) {
            case Appointment.WEEKLY_REOCCURENCE:
                int[] reoccuringDays = consultation.getReoccurWhichDay();
                
                //If a weekly reoccurence was given but no further mention about which day of the week, assume it is the same.
                if (reoccuringDays.length == 0) {
                	nextReoccurenceDay.add(nextReoccurenceDay.DAY_OF_MONTH, 7);
                    return nextReoccurenceDay;
                }
                
                //If a weekly reoccurence was ordered and it takes place on a different day of a week.
                for (int i = 0; i < 7; i++) {
                	nextReoccurenceDay.add(nextReoccurenceDay.DAY_OF_MONTH, 1);
                    int newDayOfWeek = nextReoccurenceDay.get(nextReoccurenceDay.DAY_OF_WEEK);
                
                    //If the appointment is scheduled to be held on a given day of a week and reoccur on the same day, return that given day.  
                    for (int j = 0; j < reoccuringDays.length; i++) {
                        if (reoccuringDays[i] == newDayOfWeek) {
                            return nextReoccurenceDay;
                        }
                    }
                }
                return null; //If the weekdays stated are out of the range of the Gregorian Calendar.
            case Appointment.MONTHLY_REOCCURENCE:
            	nextReoccurenceDay.add(nextReoccurenceDay.MONTH, 1);
                return nextReoccurenceDay;
            case Appointment.YEARLY_REOCCURENCE:
            	nextReoccurenceDay.add(nextReoccurenceDay.YEAR, 1);
                return nextReoccurenceDay;
        }
        return null;
    }
    
    //Saves details of an appointment into the XML tree data structure.
    public boolean saveAppointment(Appointment consult) {
        if (!consult.isAppointmentLegitimate()) {
            return false; 
        }
        
        //Place the new appointment node into the XML tree data structure.
        Document rec = retrieveDocument();
        Element root = rec.getDocumentElement();
        Element consultationElement = rec.createElement("appointment");
        root.appendChild(consultationElement);
        
        //Grab the data of an appointment and place it into the XML data tree structure.
        Element startHourElement = rec.createElement("Start Hour"); //Make a new element node in order to house the info
        Text startHourText = rec.createTextNode(Integer.toString(consult.retrieveStartHour())); //Make a text node before storing the info
        startHourElement.appendChild(startHourText); //Child of an element node is the text node.
        consultationElement.appendChild(startHourElement); //The element is now the child of an appointment node.
        
        //The above is repeated for each element
        Element startMinute = rec.createElement("Start Minute");
        Text startMinuteText = rec.createTextNode(Integer.toString(consult.retrieveStartMinute()));
        startMinute.appendChild(startMinuteText);
        consultationElement.appendChild(startMinute);
        
        Element startDay = rec.createElement("Start Day");
        Text startDayText = rec.createTextNode(Integer.toString(consult.retrieveStartDay()));
        startDay.appendChild(startDayText);
        consultationElement.appendChild(startDay);
        
        Element startMonth = rec.createElement("Start Month");
        Text startMonthText = rec.createTextNode(Integer.toString(consult.retrieveStartMonth()));
        startMonth.appendChild(startMonthText);
        consultationElement.appendChild(startMonth);
        
        Element startYear = rec.createElement("Start Year");
        Text startYearText = rec.createTextNode(Integer.toString(consult.retrieveStartYear()));
        startYear.appendChild(startYearText);
        consultationElement.appendChild(startYear);
        
        Element duration = rec.createElement("Duration");
        Text durationText = rec.createTextNode(Integer.toString(consult.retrieveDuration()));
        duration.appendChild(durationText);
        consultationElement.appendChild(duration);
        
        Element patientName = rec.createElement("Patient Name");
        Text patientNameText = rec.createTextNode(consult.retrievePatientName());
        patientName.appendChild(patientNameText);
        consultationElement.appendChild(patientName);
        
        Element healthcarePlan = rec.createElement("Healthcare Plan");
        Text healthcareText = rec.createTextNode(consult.retrievePatientName());
        healthcarePlan.appendChild(healthcareText);
        consultationElement.appendChild(healthcarePlan);
        
        Element patientID = rec.createElement("Patient ID");
        Text patientIDText = rec.createTextNode(consult.retrievePatientID());
        patientID.appendChild(patientIDText);
        consultationElement.appendChild(patientID);
        
        Element alert = rec.createElement("Alert Time");
        Text alertText = rec.createTextNode(Integer.toString(consult.retrieveAlertTime()));
        alert.appendChild(alertText);
        consultationElement.appendChild(alert);
        
        Element partner = rec.createElement("Partner");
        Text partnerText = rec.createTextNode(consult.retrievePartner());
        partner.appendChild(partnerText);
        consultationElement.appendChild(partner);
        
        Element description = rec.createElement("Description");
        Text descriptionText = rec.createTextNode(consult.retrieveDescription());
        description.appendChild(descriptionText);
        consultationElement.appendChild(description);
        
        Element reoccurOnDay = rec.createElement("Reoccur On Day");
        String reoccurOnDayString = "";
        for (int i = 0; i < consult.getReoccurWhichDay().length; i++) {
        	reoccurOnDayString += consult.getReoccurWhichDay()[i] + ",";
        }
        Text repeatDayText = rec.createTextNode(reoccurOnDayString);
        reoccurOnDay.appendChild(repeatDayText);
        consultationElement.appendChild(reoccurOnDay);
        
        Element repeatOn = rec.createElement("Reoccur On");
        Text reoccurOnText = rec.createTextNode(Integer.toString(consult.getRetrieveReoccurOn()));
        repeatOn.appendChild(reoccurOnText);
        consultationElement.appendChild(repeatOn);
        
        Element repeatRate = rec.createElement("Frequency Of Reoccurence");
        Text reoccurRateText = rec.createTextNode(Integer.toString(consult.getReoccurRate()));
        repeatRate.appendChild(reoccurRateText);
        consultationElement.appendChild(repeatRate);
        
        Element numberOfReoccurences = rec.createElement("Number of Reoccurences");
        Text repeatNumberText = rec.createTextNode(Integer.toString(consult.getNumberOfReoccurences()));
        numberOfReoccurences.appendChild(repeatNumberText);
        consultationElement.appendChild(numberOfReoccurences);
        
        //Allow the appointment to locate where it is stored within the XML data tree structure.
        consult.establishXML(consultationElement);
        
        if (isSelfSave()) {
            return writeXML();
        }
        else {
            return true;
        }
    }
    
    //Deletes information of an appointment from the XML data framework. It will not generate a new XML file.
    public boolean deleteAppointment(Appointment consultation) {
        if (!consultation.isAppointmentLegitimate()) {
            return false; //If the appointments are illegitimate, do not perform any operations.
        }
        
        Element consultationElement = consultation.fetchXMLDocument();
        if (consultationElement == null) {
            return false; //Discards the appointment
        }
        
        Node parentNode = consultationElement.getParentNode();
        parentNode.removeChild(consultationElement);
        consultation.establishXML(null);
        if (isSelfSave()) {
            return writeXML();
        }
        else {
            return true;
        }
    }
    
    public boolean writeXML() {
        try {
            //Prints XML
            TransformerFactory creator = TransformerFactory.newInstance();
            Transformer convert = creator.newTransformer();
            
            DOMSource source = new DOMSource(retrieveDocument());
            File outputFile = new File(retrieveFilename());
            StreamResult output = new StreamResult(outputFile);
            convert.transform(source, output);
        } 
        catch (TransformerConfigurationException pass) {
            return false;
        }
        catch (TransformerException pass) {
            return false;
        }
        return true;
    }
    
    private boolean isSelfSave() { return selfSave; }
    private boolean isAppointmentLegitimate() { return legitimate; }
    private void establishDocument(Document record) { this.record = record; }
    private void setFileName(String fileName) { this.fileName = fileName; }
    private Document retrieveDocument() { return record; }
    private String retrieveFilename() { return fileName; }
}