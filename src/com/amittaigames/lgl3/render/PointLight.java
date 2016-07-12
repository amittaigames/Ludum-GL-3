package com.amittaigames.lgl3.render;

import java.util.ArrayList;
import java.util.List;

public class PointLight {

	private float x;
	private float y;
	private int power;
	private int intensity;
	
	public static List<PointLight> list = new ArrayList<PointLight>();
	
	public PointLight (float x, float y, int power, int intensity) {
		this.x = x;
		this.y = y;
		this.power = power;
		this.intensity = intensity;
		list.add(this);
	}
	
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public int getPower() {
		return power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	public int getIntensity() {
		return intensity;
	}
	
	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}
	
}