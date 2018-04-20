package com.elitzur_software.momentekenmobile;

public class NavigationItem {
	private String text;
	private int icon;
	
	public NavigationItem( String text, int icon ) {
		this.setText(text);
		this.setIcon(icon);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
}
