package hcsdteam12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Created by Adam on 16/12/2015.
 */
public class Calendar extends JFrame {

    private static final long serialVersionUID = 1L;
    // Creation of all variables needed for the form
    private JLabel totalCostLabel;
    private JButton update = new JButton("Update Appointment");
    private JButton delete = new JButton("Delete Appointment");
    private JButton day1 = new JButton(), day2 = new JButton(), day3 = new JButton(), day4 = new JButton(), day5 = new JButton();
    private JScrollPane appointments;
    private JPanel weekPanel = new JPanel();
    private JPanel editButtons = new JPanel();

    /**
    * A class to display the calendar
    *
    * @param partnerId A parameter representing whether its the partner viewing the calendar or not and their ID
    **/
    public Calendar(int partnerId) {

        String date;
        date = JOptionPane.showInputDialog(null, "Enter the date of the week start (dd/mm/yyyy):");
        while (!date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
            date = JOptionPane.showInputDialog(null, "Enter the date of the week start (dd/mm/yyyy):");
        }
        System.out.println(date);
        date = Appointment.changeDateFromForm(date);
        System.out.println(date);
        if (checkDateIsWeekStart(date)) {
            day1.setText(Appointment.changeDateFromDatabase(date));
            day2.setText(Appointment.changeDateFromDatabase(addDay(date, 1)));
            day3.setText(Appointment.changeDateFromDatabase(addDay(date, 2)));
            day4.setText(Appointment.changeDateFromDatabase(addDay(date, 3)));
            day5.setText(Appointment.changeDateFromDatabase(addDay(date, 4)));
            setLayout(new GridBagLayout());
            weekPanel.add(day1);
            weekPanel.add(day2);
            weekPanel.add(day3);
            weekPanel.add(day4);
            weekPanel.add(day5);
            add(weekPanel, 0, 0, 1, 1);
            editButtons.add(update);
            editButtons.add(delete);
            add(editButtons, 0, 2, 1, 1);
            addScrollPane(getDaysAppointments(date, partnerId), 0, 1, 1, 1);
            setSize(600, 400);
            setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "This date is not a week beginning");
        }

        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        day1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JScrollPane scrollPane = getDaysAppointments(Appointment.changeDateFromForm(day1.getText()), partnerId);
                addScrollPane(scrollPane, 0, 1, 1, 1);
                revalidate();
                repaint();
            }
        });

        day2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JScrollPane scrollPane = getDaysAppointments(Appointment.changeDateFromForm(day2.getText()), partnerId);
                addScrollPane(scrollPane, 0, 1, 1, 1);
                revalidate();
                repaint();
            }
        });

        day3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JScrollPane scrollPane = getDaysAppointments(Appointment.changeDateFromForm(day3.getText()), partnerId);
                addScrollPane(scrollPane, 0, 1, 1, 1);
                revalidate();
                repaint();
            }
        });

        day4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JScrollPane scrollPane = getDaysAppointments(Appointment.changeDateFromForm(day4.getText()), partnerId);
                addScrollPane(scrollPane, 0, 1, 1, 1);
                revalidate();
                repaint();
            }
        });

        day5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JScrollPane scrollPane = getDaysAppointments(Appointment.changeDateFromForm(day5.getText()), partnerId);
                addScrollPane(scrollPane, 0, 1, 1, 1);
                revalidate();
                repaint();
            }
        });
    }

    private static String addDay(String date, int days) {
        int year = Integer.parseInt(date.split("-")[0]);
        int month = Integer.parseInt(date.split("-")[1]);
        int day = Integer.parseInt(date.split("-")[2]);
        LocalDate current = LocalDate.of(year, month, day);
        current = current.plusDays(days);
        return current.toString();
    }

    private static boolean checkDateIsWeekStart(String date) {
        int year = Integer.parseInt(date.split("-")[0]);
        int month = Integer.parseInt(date.split("-")[1]);
        int day = Integer.parseInt(date.split("-")[2]);
        LocalDate current = LocalDate.of(year, month, day);
        if (current.getDayOfWeek() == DayOfWeek.MONDAY) {
            return true;
        } else {
            return false;
        }
    }

    public JScrollPane getDaysAppointments(String date, int partnerId) {
        Object rowData[][] = {{}};
        JScrollPane scrollPane = new JScrollPane();
        String query;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team012?user=team012&password=8b4c5e49");
            Statement stmt = con.createStatement();
            if (partnerId == 0) {
                query = "SELECT startTime, endTime, patients.id, partners.id FROM appointment INNER JOIN patients ON " +
                        "appointment.patientid=patients.id INNER JOIN partners ON appointment.partnerid=partners.id WHERE appointment.date='" + date + "';";
            } else {
                query = "SELECT startTime, endTime, patients.id, partners.id FROM appointment INNER JOIN patients ON " +
                        "appointment.patientid=patients.id INNER JOIN partners ON appointment.partnerid=partners.id WHERE appointment.date='" + date + "' AND partnerid=" + partnerId + ";";
            }
            System.out.println(query);
            ResultSet result = stmt.executeQuery(query);
            if (result.next()) {
                result.last();
                Object[][] resultSet = new Object[result.getRow()][4];
                result.absolute(0);
                int row = 0;
                while (result.next()) {
                    for (int i = 0; i < 4; i++) {
                        resultSet[row][i] = result.getObject(i + 1);
                    }
                    row++;
                }
                rowData = resultSet;
                Object columnNames[] = {"Start Time", "End Time", "Patient Name", "Partner Name"};
                JTable table = new JTable(rowData, columnNames);
                scrollPane = new JScrollPane(table);
                result.close();
                stmt.close();
                con.close();
            } else {
                JOptionPane.showMessageDialog(null, "There are no appointments for this day");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return scrollPane;
    }

    public void add(Component c, int x, int y, int w, int h) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipadx = 20;
        gbc.ipady = 5;
        add(c, gbc);
    }

    public void addScrollPane(Component c, int x, int y, int w, int h) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 20;
        gbc.ipady = 5;
        add(c, gbc);
    }

}