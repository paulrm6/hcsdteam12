package hcsdteam12.calendar.gui;

/**
 * CalendarPresentation.java has the job of displaying days in a calendar format by using the JTable structure.
 * It also houses 2 inner classes to exhibit and keep data in the calendar.
 * 
 * @author Seng Kin(Terence), Kong
 **/

import hcsdteam12.calendar.data.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.border.Border;

public class CalendarPresentation extends JPanel {
	private static final long serialVersionUID = 1L;
	private CalendarFrame frame;
    private MonthViewDataModel myModel; //Information is kept in this private instance for display inside of the JTable
    private JTable table; //MonthViewDataModel work together with this private instance to bring up information onto the calendar  
	JScrollPane scrollPane; //To provide scrolling abilities.

	//Sets a reference to the CalendarFrame
    public CalendarPresentation (CalendarFrame ref) {
        super();	
        frame = ref;	
    }
    
    //Utilized to set the information that needs to be displayed onto the calendar.
    public void establishPresentDay(java.util.List tobestored) {
    	myModel = new MonthViewDataModel(tobestored);
        table = new JTable(myModel);
        table.addMouseListener((MouseListener)frame);
        table.setRowHeight (100);
        table.setPreferredScrollableViewportSize(new Dimension(700, 500));

        scrollPane = new JScrollPane(table); //Create the scroll functionality for the calendar.
        buildDayRenderer(table);
        this.add(scrollPane, BorderLayout.CENTER); //Create the scroll functionality for the calendar
    }
    
    //Brings up appointments that are scheduled for a given day on the calendar
    public Day bringAppointmentsOnDay() {
       int weekNumber = table.getSelectedRow();
       int rowNumber = table.getSelectedColumn();
       return((Day)(myModel.getValueAt(weekNumber, rowNumber)));
    }
    
    //To override the standard operating practice of the JTable to bring up information regarding appointments 
    class DayRenderer extends JLabel implements TableCellRenderer {
		private static final long serialVersionUID = 1L;
		
        Border unselectedBorder = null;
        Border selectedBorder = null;
        boolean isBordered = true; //To enable the table cell to display its surrounding border.

        public DayRenderer(boolean isBordered) {
            super();
            this.setVerticalAlignment(SwingConstants.NORTH);
            this.setBackground(Color.white);
            this.isBordered = isBordered;
            setOpaque(true); //To bring up the background.
        }

        //Return the calendar with already pre-loaded information about appointments.
        public Component getTableCellRendererComponent(JTable calendar, Object day, boolean isChosen, boolean hasContent, int weeks, int days) {
            Iterator repeat = ((Day)day).iterator();
            String tempmemory;
            
            //To support further testing functions, I convert JLabels into HTML elements.
            tempmemory = "<html><p><font size=\"+1\">";            
            tempmemory += Integer.toString(((Day)day).retrieveDay());            
            tempmemory += "</font>";
            
          	Appointment appointment;
            int minute;
            int hour;
            String minString;
			String meridianString;
            
            //Go through the day and get the data to display
            while(repeat.hasNext()){
            	tempmemory += "<p>";
            	appointment = (Appointment)repeat.next();
				if(appointment.hasStartTime()){
					hour = appointment.retrieveStartHour();
					if(hour > 12){
						meridianString = "PM";
					}
					else{
						meridianString = "AM";	
					}
					//Convert 24-hour clock to a 12-hour clock
					if(hour == 0){
						hour = 12;	
					}
					else{
						hour = hour%12;
					}
					//add preceding zero to minutes less than 10
					minute = appointment.retrieveStartMinute();
					if(minute < 10){
						minString = new String("0" + Integer.toString(minute));
					}
					else{
						minString = Integer.toString(minute);
					}
					//create the string containing a data summary
					tempmemory += hour + ":" + minString + meridianString + " " + appointment.retrievePatientName();
				}
				else{
					tempmemory += appointment.retrievePatientName();	
				}
            }
            this.setText(tempmemory);            
            return this;
        }
	}

    //Assemble the renderer with the Day class
    private void buildDayRenderer(JTable calendar) {
        table.setDefaultRenderer(Day.class, new DayRenderer(true));
    }

    //To store the details of appointments to be viewed within the JTable 
    class MonthViewDataModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
        final String[] columnHeaders = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Object[][] data;
        
        public MonthViewDataModel(java.util.List dataToBeKept) {
        	super();
            int days = 7; //Representing 7 days in a week
			int weeks = dataToBeKept.size() / 7; //Calculate number of rows needed in the calendar.
			this.data = new Day[weeks][days]; //2D array
			for(int curRow = 0; curRow<weeks; curRow++) {
				for(int curCol=0; curCol<7; curCol++) {
					this.data[curRow][curCol] = (Day)dataToBeKept.remove(0);
				}
			}
		}

        //Made to return the number of columns required on a calendar.
        public int getColumnCount() {
            return columnHeaders.length;
        }

        //Made to return the number of rows required on a calendar.
        public int getRowCount() {
            return data.length;
        }

        //Made to return the name of a column.
        public String getColumnName(int day) {
            return columnHeaders[day];
        }

        //Retrieve information from a specific position within the calendar.
        public Object getValueAt(int week, int day) {
            return data[week][day];
        }

        //Add information on a specific position within the calendar. 
        public void setValueAt(Object value, int week, int day) {
            data[week][day] = value;
            fireTableCellUpdated(week, day);

        }
    }
}