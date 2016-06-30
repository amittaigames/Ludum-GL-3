package com.amittaigames.lgl3.render;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.TrueTypeFont;

public class FontHandler {

	private static List<FontGL> fonts = new ArrayList<FontGL>();
	
	// Sets the font used by the renderer
	public static void setFont(String name) {
		Render.setFont(getFont(name));
	}
	
	// Registers a font to be used by Ludum GL
	public static void registerFont(Font f, boolean antiAliasing) {
		System.out.println("[Ludum GL] Loading font: " + f.getName() + " " + f.getSize());
		fonts.add(new FontGL(f.getName() + " " + f.getSize(), new TrueTypeFont(f, antiAliasing)));
	}
	
	// Loads a custom font
	public static void loadFont(String fontName) {
		try {
			Font f = Font.createFont(Font.PLAIN, FontHandler.class.getResourceAsStream(fontName));
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static TrueTypeFont getFont(String name) {
		TrueTypeFont ttf = null;
		for (int i = 0; i < fonts.size(); i++) {
			FontGL font = fonts.get(i);
			if (font.getName().equals(name)) {
				ttf = font.getFont();
				break;
			}
		}
		if (ttf == null) {
			System.out.println("Could not load font: " + name);
		}
		return ttf;
	}
	
}