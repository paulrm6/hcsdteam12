package hcsdteam12.calendar.data;

/**
 * For testing purposes, XML is implemented to obtain access to appointment data or to edit appointment data stored in an XML document.
 *
 * @author Seng Kin(Terence), Kong
 **/

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XML implements ErrorHandler {
    private String findException(SAXParseException handler) {
        String key = handler.getSystemId();
        if (key == null) {
            key = "null";
        }
        String info = " Identifier " + key + " Line = " + handler.getLineNumber() + ": " + handler.getMessage();
        return info;
    }

    //Classes representing SAX error handlers.
    public void warning(SAXParseException handler) throws SAXException {
        String text_error = "Warning: " + findException(handler);
        throw new SAXException(text_error);
    }

    public void error(SAXParseException handler) throws SAXException {
        String text_error = "Failure: " + findException(handler);
        throw new SAXException(text_error);
    }

    public void fatalError(SAXParseException handler) throws SAXException {
        String text_error = "Complete Failure: " + findException(handler);
        throw new SAXException(text_error);
    }
}