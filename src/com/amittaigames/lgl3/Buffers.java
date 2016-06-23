package com.amittaigames.lgl3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.sun.prism.impl.BufferUtil;

public class Buffers {

	public static FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buf = BufferUtil.newFloatBuffer(data.length);
		buf.put(data);
		buf.flip();
		return buf;
	}
	
	public static IntBuffer createIntBuffer(int[] data) {
		IntBuffer buf = BufferUtil.newIntBuffer(data.length);
		buf.put(data);
		buf.flip();
		return buf;
	}
	
}