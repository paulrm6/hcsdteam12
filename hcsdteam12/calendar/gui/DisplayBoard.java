/**
 * DisplayBoard.java is responsible for setting the colour of the calendar components.
 * 
 * @author Seng Kin(Terence), Kong
 * **/

package hcsdteam12.calendar.gui;

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class DisplayBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	private Color BACKGROUND_COLOUR = Color.white;
   
    //The appointment form's background colour is set to white
	public DisplayBoard() { 
		super();
		setBackground (BACKGROUND_COLOUR);
	}
   
    //Fix the current data.
	public void setPresentDay (LinkedList<?> days) {}
   
    //A Graphics object is deployed to visually show the JPanel and repaints this JPanel
	public void paintComponent (Graphics g) {
   	  super.paintComponent(g);   	  
	}
}