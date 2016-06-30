package com.amittaigames.lgl3.render;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.amittaigames.lgl3.Buffers;

public class TexturedMesh extends Mesh {

	private int vCoords;
	private int texture;
	
	public TexturedMesh(String texName, float[] pos, float[] color, int[] list) {
		super(pos, color, list);
		
		Texture tex = null;
		try {
			tex = TextureLoader.getTexture("PNG", this.getClass().getResourceAsStream(texName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (tex == null) {
			return;
		}
		
		this.texture = tex.getTextureID();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		this.vCoords = GL15.glGenBuffers();
		float[] coords = {
			0, 0,
			1, 0,
			1, 1,
			0, 1
		};
		FloatBuffer buf = Buffers.createFloatBuffer(coords);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vCoords);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public int getTextureCoords() {
		return vCoords;
	}
	
	public int getTextureID() {
		return texture;
	}
	
}