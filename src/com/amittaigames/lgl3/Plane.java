package com.amittaigames.lgl3;

public class Plane {

	private Mesh mesh;
	private float x;
	private float y;
	private float width;
	private float height;
	
	public Plane(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		float[] pos = {
			this.x, this.y,
			this.x + this.width, this.y,
			this.x + this.width, this.y + this.height,
			this.x, this.y + this.height
		};
		
		float[] color = {
			1, 1, 1,
			1, 1, 1,
			1, 1, 1,
			1, 1, 1
		};
		
		int[] index = {
			0, 1, 2,
			0, 3, 2
		};
		
		this.mesh = new Mesh(pos, color, index);
	}
	
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
		
		float[] pos = {
			this.x, this.y,
			this.x + this.width, this.y,
			this.x + this.width, this.y + this.height,
			this.x, this.y + this.height
		};
		
		mesh.updatePosition(pos);
	}
	
	public void setColor(int r, int g, int b) {
		float rr = Render.rgbConvert(r);
		float gg = Render.rgbConvert(g);
		float bb = Render.rgbConvert(b);
		
		float[] color = {
			rr, gg, bb,
			rr, gg, bb,
			rr, gg, bb,
			rr, gg, bb
		};
		
		mesh.updateColor(color);
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
}