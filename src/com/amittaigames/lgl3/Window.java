package com.amittaigames.lgl3;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Window {

	public static int WIDTH;
	public static int HEIGHT;
	
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
		
		WIDTH = width;
		HEIGHT = height;
		
		start(game);
	}
	
	public static void enable(String... args) {
		for (String s : args) {
			enables.add(enables.size(), s);
		}
	}
	
	public static void takeScreenshot(String out) {
		int[] pixels = new int[WIDTH * HEIGHT];
		int index;
		ByteBuffer buf = ByteBuffer.allocateDirect(WIDTH * HEIGHT * 3);
		
		GL11.glReadPixels(0, 0, WIDTH, HEIGHT, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buf);
		
		BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < pixels.length; i++) {
			index = i * 3;
			pixels[i] = (buf.get(index) << 16) + (buf.get(index + 1) << 8) + (buf.get(index + 2) << 0);
		}
		img.setRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
		
		AffineTransform at = AffineTransform.getScaleInstance(1, -1);
		at.translate(0, -img.getHeight());
		
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		img = op.filter(img, null);
		
		try {
			ImageIO.write(img, "PNG", new File(out));
		} catch (Exception e) {
			e.printStackTrace();
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