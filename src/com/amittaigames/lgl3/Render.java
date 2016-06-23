package com.amittaigames.lgl3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class Render {

	public void drawMesh(Mesh m) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, m.getPos());
		GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, 0);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, m.getColor());
		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, m.getList());
		GL11.glDrawElements(GL11.GL_TRIANGLES, m.getListCount(), GL11.GL_UNSIGNED_INT, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
	}
	
	public void drawPlane(Plane p) {
		drawMesh(p.getMesh());
	}
	
	public void clear(int r, int g, int b) {
		GL11.glClearColor(rgbConvert(r), rgbConvert(g), rgbConvert(b), 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public static float rgbConvert(int rgb) {
		return (float)rgb/255.0f;
	}
	
}