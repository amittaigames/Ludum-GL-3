package com.amittaigames.lgl3.render;

public class TexturedPlane {

	private TexturedMesh mesh;
	private float x;
	private float y;
	private float width;
	private float height;
	
	private float rx;
	private float ry;
	private float rz;
	private float angle;
	
	public TexturedPlane(String fName, float x, float y, float width, float height) {
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
		
		this.mesh = new TexturedMesh(fName, pos, color, index);
	}
	
	public void rotate(float rx, float ry, float rz, float angle) {
		this.angle += angle;
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
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
	
	public TexturedMesh getMesh() {
		return mesh;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getRotateX() {
		return rx;
	}
	
	public float getRotateY() {
		return ry;
	}
	
	public float getRotateZ() {
		return rz;
	}
	
	public float getAngle() {
		return angle;
	}

}