package com.amittaigames.lgl3.math;

public class Point {

	private float x;
	private float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public static float distance(Point a, Point b) {
		float x = Math.abs(a.getX() - b.getX());
		float y = Math.abs(a.getY() - b.getY());
		
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public float distance(Point b) {
		float x = Math.abs(this.x - b.getX());
		float y = Math.abs(this.y - b.getY());
		
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}