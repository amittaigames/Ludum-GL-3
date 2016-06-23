package com.amittaigames.lgl3;

public class Mouse {
	
	public static final int MOUSE_LEFT			= 0x00;
	public static final int MOUSE_RIGHT			= 0x01;
	
	public static int getX() {
		return org.lwjgl.input.Mouse.getX();
	}
	
	public static int getY() {
		return org.lwjgl.input.Mouse.getY();
	}
	
	public static boolean isClicked(int button) {
		return org.lwjgl.input.Mouse.isButtonDown(button);
	}
	
}