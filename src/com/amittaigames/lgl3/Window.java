package com.amittaigames.lgl3;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.ImageIOImageData;

import com.amittaigames.lgl3.render.Render;

public class Window {

	public static int WIDTH;
	public static int HEIGHT;
	private static long lastFrame;
	
	private static long lastFPS;
	private static int LAST = 0;
	private static int FPS;
	
	private static Render r = new Render();
	private static List<String> enables = new ArrayList<String>();
	
	public static void init(String title, int width, int height, CoreGame game) {
		try {
			Display.setTitle(title);
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setVSyncEnabled(true);
			setIcon("/lgl/icon");
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
		
		GL11.glReadBuffer(GL11.GL_FRONT);
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
		getDelta();
		lastFPS = getTime();
		
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
	
	
	private static int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}
	
	
	private static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public static int getCurrentFPS() {
		int ret = LAST;
		if (getTime() - lastFPS > 1000) {
			ret = FPS;
			LAST = FPS;
			FPS = 0;
			lastFPS += 1000;
		}
		FPS++;
		return ret;
	}
	
	public static void setTitle(String title) {
		Display.setTitle(title);
	}
	
	public static void setIcon(String fName) {
		StringBuilder a = new StringBuilder().append(fName);
		StringBuilder b = new StringBuilder().append(fName);
		
		if (Debug.getOSName().contains("Windows")) {
			a.append("16.png");
			b.append("32.png");
		}
		
		if (Debug.getOSName().contains("Mac")) {
			a.append("128.png");
			b.append("128.png");
		}
		
		if (Debug.getOSName().contains("Linux")) {
			a.append("32.png");
			b.append("32.png");
		}
		
		try {
			Display.setIcon(new ByteBuffer[] {
					new ImageIOImageData().imageToByteBuffer(ImageIO.read(Window.class.getResourceAsStream(a.toString())), false, false, null),
					new ImageIOImageData().imageToByteBuffer(ImageIO.read(Window.class.getResourceAsStream(b.toString())), false, false, null)
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getWidth() {
		return Display.getDisplayMode().getWidth();
	}
	
	public static int getHeight() {
		return Display.getDisplayMode().getHeight();
	}
	
}