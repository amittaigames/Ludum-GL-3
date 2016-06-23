package com.amittaigames.lgl3;

import org.newdawn.slick.TrueTypeFont;

public class FontGL {

	private String name;
	private TrueTypeFont font;
	
	public FontGL(String name, TrueTypeFont font) {
		this.name = name;
		this.font = font;
	}

	public String getName() {
		return name;
	}

	public TrueTypeFont getFont() {
		return font;
	}
	
}