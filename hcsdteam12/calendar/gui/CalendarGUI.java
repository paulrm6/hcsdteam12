/**
 * Created by Joe on 16/12/2015.
 */
package hcsdteam12.calendar.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class CalendarGUI {

    private int day, month, year;

    public CalendarGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(750,450);
        frame.setTitle("Calendar");

        JPanel  tabsPanel = new JPanel(),
                dentistPanel = new JPanel(),
                hygienestPanel = new JPanel(),
                panel = new JPanel();
        JButton dentist, hygienist, both;
        JLabel partnerid = new JLabel("2");

        dentist = new JButton("Dentist");
        hygienist = new JButton("Hygienist");
        both = new JButton("Both");
        both.setForeground(Color.RED);

        dentist.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                hygienist.setForeground(Color.BLACK);
                both.setForeground(Color.BLACK);
                dentist.setForeground(Color.RED);
                partnerid.setText("0");
            }
        });

        hygienist.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                dentist.setForeground(Color.BLACK);
                both.setForeground(Color.BLACK);
                hygienist.setForeground(Color.RED);
                partnerid.setText("1");
            }
        });

        both.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                dentist.setForeground(Color.BLACK);
                hygienist.setForeground(Color.BLACK);
                both.setForeground(Color.RED);
                partnerid.setText("2");
            }
        });

        tabsPanel.add(dentist);
        tabsPanel.add(hygienist);
        tabsPanel.add(both);

        // ---------
        String[] columns = {"Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday"};

        GregorianCalendar cal = new GregorianCalendar();
        day = cal.get(GregorianCalendar.DAY_OF_MONTH);
        month = cal.get(GregorianCalendar.MONTH)+1;
        year = cal.get(GregorianCalendar.YEAR);
        String date;
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        int week = 0;
        Object[][] data = new String[2][7]; // [rows][columns]
        for (int j = 0; j < 2; j++) {
            for (int i = dow; dow < 7; dow++) {
                date = day + "/" + month + "/" + year;
                data[j][dow] = date;
                day++;
                week++;
                if (week > 7) {
                    break;
                }
            }
            dow = 0;
        }
        DefaultTableModel model = new DefaultTableModel(data,columns);
        GregorianCalendar cal1 = new GregorianCalendar(year, month, day);
        JTable table = new JTable(model);
        table.setRowHeight(45);
        table.setPreferredScrollableViewportSize(new Dimension(700,90));



        JScrollPane scrollPane = new JScrollPane(table);
        dentistPanel.add(scrollPane);


        // --------------

        JTextArea appointmentDisplay = new JTextArea(15,100);
        appointmentDisplay.setEditable(false);
        panel.add(appointmentDisplay);

        frame.add(tabsPanel,BorderLayout.NORTH);
        frame.add(dentistPanel,BorderLayout.CENTER);
        frame.add(appointmentDisplay,BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new CalendarGUI();
    }
}
