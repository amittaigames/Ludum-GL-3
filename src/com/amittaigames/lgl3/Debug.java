package com.amittaigames.lgl3;

import org.lwjgl.opengl.GL11;

public class Debug {

	public static String getOSName() {
		return System.getProperty("os.name");
	}
	
	public static String getOSVersion() {
		return System.getProperty("os.version");
	}
	
	public static String getUserHome() {
		return System.getProperty("user.home");
	}
	
	public static String getArchitecture() {
		return System.getProperty("os.arch");
	}
	
	public static int getFreeMemory() {
		return (int) Runtime.getRuntime().freeMemory() / 1000000;
	}
	
	public static int getTotalMemory() {
		return (int) Runtime.getRuntime().maxMemory() / 1000000;
	}
	
	public static String getOpenGLVersion() {
		return GL11.glGetString(GL11.GL_VERSION).split(" ")[0];
	}
	
	public static String getOpenGLVendor() {
		return GL11.glGetString(GL11.GL_VENDOR);
	}
	
	public static void printInfo() {
		System.out.println("Operating System: " + getOSName() + " (" + getOSVersion() + ")");
		System.out.println("JRE Architecture: " + getArchitecture());
		System.out.println("Memory: " + getFreeMemory() + "/" + getTotalMemory() + " MB");
		System.out.println("OpenGL Version: " + getOpenGLVersion() + " (" + getOpenGLVendor() + ")");
	}
	
}