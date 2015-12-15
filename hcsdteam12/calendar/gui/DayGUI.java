/** 
 * Implemented to display the window that allow appointments to be added or cancelled.
 * 
 * @author Seng Kin(Terence), Kong
 **/

package hcsdteam12.calendar.gui;
import hcsdteam12.calendar.data.*;

import java.awt.*;
import javax.swing.*;

public class DayGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	public JLabel narrativeText; //JLabel stores date in text form
	private Day day; //Day object
	
	public static Color BACKGROUND_COLOUR = Color.white;
	public static Color HIGHLIGHT_COLOUR = Color.lightGray;

	public DayGUI() {
		super();
		day = new Day();
		String data = day.toString();
		narrativeText = new JLabel(data);
		add(narrativeText);
		highlight(false);
	}

	public void highlight(boolean setHighlight) {
	if (setHighlight)
		setBackground(HIGHLIGHT_COLOUR);
	else
		setBackground(BACKGROUND_COLOUR);	
	}
}