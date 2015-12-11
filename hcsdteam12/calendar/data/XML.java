package hcsdteam12.calendar.data;

import org.xml.sax.*;

/**
* For testing purposes, XML is implemented to obtain access to appointment data or to edit appointment data stored in an XML document. 
*
* @author  Seng Kin(Terence), Kong
**/

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