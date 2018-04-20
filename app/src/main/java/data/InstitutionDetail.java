package data;

import java.io.Serializable;


public class InstitutionDetail implements Serializable {
	
	private String text;
	private String editText;

	public static final String NAME = "שם המוסד";
	public static final String NUMBER = "סמל מוסד";
	public static final String ADDRESS = "כתובת";
	public static final String SETTLEMENT = "יישוב";
	public static final String PHONE_NUMBER = "טלפון";
	public static final String AREA = "מחוז";
	public static final String OWNERSHIP = "בעלות";
	
	public InstitutionDetail( String text ) {
		setText(text);
	}
	
	public void setText( String text ) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void setEditText( String editText ) {
		this.editText = editText;
	}
	
	public String getEditText() {
		return editText;
	}
}
