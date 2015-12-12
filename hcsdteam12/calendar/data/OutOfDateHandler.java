package hcsdteam12.calendar.data;
/**
 * Serves as handler that manages warnings and errors when 
 * retrieving invalid dates.
 * 
 * @author Seng Kin(Terence), Kong
 **/

public class OutOfDateHandler extends java.lang.Exception {
	private static final long serialVersionUID = 1L;
    public OutOfDateHandler() {}
    public OutOfDateHandler(String message) {
        super(message);
    }
}