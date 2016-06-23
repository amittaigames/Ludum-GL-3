package com.amittaigames.lgl3;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Window {

	private static Render r = new Render();
	
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
		
		start(game);
	}
	
	private static void start(CoreGame game) {
		game.init();
		
		while (!Display.isCloseRequested()) {
			game.render(r);
			game.update();
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
		System.exit(0);
	}
	
}