package com.amittaigames.lgl3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL15;

public class Mesh {

	private int vPos;
	private int vColor;
	private int vList;
	
	private int vertexCount;
	private int listCount;
	
	public Mesh(float[] pos, float[] color, int[] list) {
		vertexCount = pos.length / 2;
		listCount = list.length;
		
		vPos = GL15.glGenBuffers();
		updatePosition(pos);
		
		vColor = GL15.glGenBuffers();
		updateColor(color);
		
		vList = GL15.glGenBuffers();
		IntBuffer buf = Buffers.createIntBuffer(list);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vList);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void updatePosition(float[] pos) {
		FloatBuffer buf = Buffers.createFloatBuffer(pos);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vPos);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void updateColor(float[] color) {
		FloatBuffer buf = Buffers.createFloatBuffer(color);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vColor);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public int getPos() {
		return vPos;
	}

	public int getColor() {
		return vColor;
	}

	public int getList() {
		return vList;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public int getListCount() {
		return listCount;
	}
	
}