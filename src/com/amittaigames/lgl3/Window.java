package com.amittaigames.lgl3;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Window {

	private static Render r = new Render();
	private static List<String> enables = new ArrayList<String>();
	
	public static void init(String title, int width, int height, CoreGame game) {
		try {
			Display.setTitle(title);
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		for (String s : enables) {
			if (s.equalsIgnoreCase("textures")) {
				GL11.glEnable(GL11.GL_TEXTURE_2D);
			}
			if (s.equalsIgnoreCase("alpha")) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			}
		}
		
		start(game);
	}
	
	public static void enable(String... args) {
		for (String s : args) {
			enables.add(enables.size(), s);
		}
	}
	
	private static void start(CoreGame game) {
		game.init();
		
		while (!Display.isCloseRequested()) {
			game.render(r);
			game.update();
			
			Display.update();
			Display.sync(60);
		}
		
		if (Sound.isInitialized())
			Sound.destroy();
		
		Display.destroy();
		System.exit(0);
	}
	
}