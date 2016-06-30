package com.amittaigames.lgl3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Buffers {

	public static FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buf = BufferUtils.createFloatBuffer(data.length);
		buf.put(data);
		buf.flip();
		return buf;
	}
	
	public static IntBuffer createIntBuffer(int[] data) {
		IntBuffer buf = BufferUtils.createIntBuffer(data.length);
		buf.put(data);
		buf.flip();
		return buf;
	}
	
}