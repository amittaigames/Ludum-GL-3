package com.amittaigames.lgl3.render;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import com.amittaigames.lgl3.Buffers;
import com.amittaigames.lgl3.Debug;
import com.amittaigames.lgl3.Window;
import com.amittaigames.lgl3.math.Matrix;

public class Render {
	
	private static TrueTypeFont font;

	public void drawMesh(Mesh m) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, m.getPos());
		GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, 0);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, m.getColor());
		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, m.getList());
		GL11.glDrawElements(GL11.GL_TRIANGLES, m.getListCount(), GL11.GL_UNSIGNED_INT, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
	}
	
	public void drawPlane(Plane p) {
		drawMesh(p.getMesh());
	}
	
	public void drawText(String text, int x, int y, Color color) {
		TextureImpl.bindNone();
		Color.white.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		font.drawString(x, y, text, color);
	}
	
	public void drawTexture(TexturedPlane t) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, t.getMesh().getTextureCoords());
		GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, t.getMesh().getTextureID());
		
		GL11.glPushMatrix();
		GL11.glTranslatef(t.getX() + (t.getWidth() / 2), t.getY() + (t.getHeight() / 2), 0);
		GL11.glRotatef(t.getAngle(), t.getRotateX(), t.getRotateY(), t.getRotateZ());
		GL11.glTranslatef(-(t.getX() + (t.getWidth() / 2)), -(t.getY() + (t.getHeight() / 2)), 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, t.getMesh().getPos());
		GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, 0);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, t.getMesh().getColor());
		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, t.getMesh().getList());
		GL11.glDrawElements(GL11.GL_TRIANGLES, t.getMesh().getListCount(), GL11.GL_UNSIGNED_INT, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		GL11.glPopMatrix();
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	}
	
	public void drawLighting(int scale, int r, int g, int b, int a) {
		// SETUP
		int WIDTH = Window.WIDTH;
		int HEIGHT = Window.HEIGHT;
		
		int imgOffset = WIDTH - HEIGHT;
		
		int[] pixels = new int[scale * scale];
		BufferedImage img = new BufferedImage(scale, scale, BufferedImage.TYPE_INT_ARGB_PRE);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = (a << 24) | (r << 16) | (g << 8) | (b);
		}
		
		for (PointLight light : PointLight.list) {
			try {
				int round = WIDTH / scale;
				
				int power = light.getPower();
				
				int centerX = (int) light.getX() / round;
				int centerY = (int) (light.getY() / round) + ((imgOffset / 2) / round) - (scale / round);
				
				Matrix filterMatrix = new Matrix(power, power);
				float n = 1;
				for (int i = 0; i < (power / 2); i++) {
					n /= 2;
				}
				filterMatrix.populateFilterReverse(n, light.getIntensity());
				//System.out.println(filterMatrix.toString());
				
				Matrix lightMatrix = new Matrix(power, power);
				lightMatrix.fill(a);
				
				Matrix finalMatrix = Matrix.multiplyFilter(filterMatrix, lightMatrix);
				
				//System.out.println(finalMatrix.toString());
				//System.exit(0);
				
				float[] fdata = finalMatrix.getDataSingle();
				int index = 0;
				
				int startX = (centerX - (power / 2));
				int startY = (centerY - (power / 2));
				
				for (int y = startY; y < (centerY + (power / 2)) + 1; y++) {
					for (int x = startX; x < (centerX + (power / 2)) + 1; x++) {
						pixels[y * img.getWidth() + x] = (((int)fdata[index]) << 24) | (r << 16) | (g << 8) | (b);
						index++;
					}
				}
				
				//System.exit(0);
			} catch (Exception e) {
				// Somtimes throws ArrayIndexOutOfBoundsException
				// If it's out of bounds there's probably nothing wrong
			}
		}
		
		img.setRGB(0, 0, scale, scale, pixels, 0, scale);
		
		// SETUP TEXTURE
		ByteBuffer bTexture = BufferUtils.createByteBuffer(img.getWidth() * img.getHeight() * 4);
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				int pixel = pixels[y * img.getWidth() + x];
				bTexture.put((byte) ((pixel >> 16) & 0xff));
				bTexture.put((byte) ((pixel >> 8)  & 0xff));
				bTexture.put((byte) ((pixel >> 0)  & 0xff));
				bTexture.put((byte) ((pixel >> 24) & 0xff));
			}
		}
		bTexture.flip();
		
		int vTexture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, vTexture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, img.getWidth(), img.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bTexture);
		
		// RENDER
		float[] pos = {
			0, -imgOffset / 2,
			0, WIDTH + (imgOffset / 2),
			WIDTH, WIDTH + (imgOffset / 2),
			WIDTH, -imgOffset / 2
		};
		FloatBuffer bPos = Buffers.createFloatBuffer(pos);
		int vPos = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vPos);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, bPos, GL15.GL_STATIC_DRAW);
		GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, 0);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		
		float[] color = {
			1, 1, 1,
			1, 1, 1,
			1, 1, 1,
			1, 1, 1
		};
		FloatBuffer bColor = Buffers.createFloatBuffer(color);
		int vColor = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vColor);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, bColor, GL15.GL_STATIC_DRAW);
		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		
		float[] uv ={
			0, 0,
			0, 1,
			1, 1,
			1, 0
		};
		FloatBuffer bUV = Buffers.createFloatBuffer(uv);
		int vUV = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vUV);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, bUV, GL15.GL_STATIC_DRAW);
		GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		
		int[] list = {
			0, 1, 2,
			0, 3, 2
		};
		IntBuffer bList = Buffers.createIntBuffer(list);
		int vList = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vList);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, bList, GL15.GL_STATIC_DRAW);
		GL11.glDrawElements(GL11.GL_TRIANGLES, list.length, GL11.GL_UNSIGNED_INT, 0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		
		GL15.glDeleteBuffers(vPos);
		GL15.glDeleteBuffers(vColor);
		GL15.glDeleteBuffers(vList);
		GL15.glDeleteBuffers(vUV);
		GL11.glDeleteTextures(vTexture);
	}
	
	public void drawDebugInfo() {
		drawText("Operating System: " + Debug.getOSName() + " (" + Debug.getOSVersion() + ")", 5, 5, Color.white);
		drawText("JRE Architecture: " + Debug.getArchitecture(), 5, 5 + (font.getHeight()), Color.white);
		drawText("Memory: " + Debug.getFreeMemory() + "/" + Debug.getTotalMemory() + " MB", 5, 5 + (font.getHeight() * 2), Color.white);
		drawText("OpenGL Version: " + Debug.getOpenGLVersion() + " (" + Debug.getOpenGLVendor() + ")", 5, 5 + (font.getHeight() * 3), Color.white);
	}
	
	public void clear(int r, int g, int b) {
		GL11.glClearColor(rgbConvert(r), rgbConvert(g), rgbConvert(b), 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public static float rgbConvert(int rgb) {
		return (float)rgb/255.0f;
	}
	
	public static void setFont(TrueTypeFont font) {
		Render.font = font;
	}
	
}