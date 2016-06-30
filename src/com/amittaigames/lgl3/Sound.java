package com.amittaigames.lgl3;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class Sound {

	private static class SoundData {
		private int buf;
		private boolean loop;
		private String name;
		
		public SoundData(int buf, boolean loop, String name) {
			this.buf = buf;
			this.loop = loop;
			this.name = name;
		}
	}
	
	private static class SoundLink {
		private SoundData data;
		@SuppressWarnings("unused")
		private int source;
		
		public SoundLink(SoundData data, int source) {
			this.data = data;
			this.source = source;
		}
	}
	
	private static List<SoundData> sounds = new ArrayList<SoundData>();
	private static List<Integer> sources = new ArrayList<Integer>();
	private static List<SoundLink> links = new ArrayList<SoundLink>();
	private static boolean isInitialized = false;
	
	public static void init() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		isInitialized = true;
		
		// Listener data
		AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}
	
	private static void addSound(SoundData sound) {
		for (int i = 0; i < sounds.size(); i++) {
			if (sounds.get(i).name.equals(sound.name)) {
				System.out.println("Sound file '" + sound.name + "' already exists");
				return;
			}
		}
		
		sounds.add(sound);
	}
	
	public static SoundData getSound(String name) {
		for (int i = 0; i < sounds.size(); i++) {
			if (sounds.get(i).name.equals(name)) {
				return sounds.get(i);
			}
		}
		
		return null;
	}
	
	public static SoundLink getLink(String name) {
		for (int i = 0; i < links.size(); i++) {
			if (links.get(i).data.name.equals(name)) {
				return links.get(i);
			}
		}
		
		return null;
	}
	
	public static void playSound(String name) {
		int source = AL10.alGenSources();
		sources.add(source);
		
		SoundData data = getSound(name);
		if (data == null) {
			System.out.println("Could not find sound '" + name + "'");
			return;
		}
		
		links.add(new SoundLink(data, source));
		
		AL10.alSourcef(source, AL10.AL_GAIN, 1);
		AL10.alSourcef(source, AL10.AL_PITCH, 1);
		AL10.alSource3f(source, AL10.AL_POSITION, 0, 0, 0);
		
		if (data.loop)
			AL10.alSourcef(source, AL10.AL_LOOPING, 1);
		
		AL10.alSourcei(source, AL10.AL_BUFFER, data.buf);
		AL10.alSourcePlay(source);
	}
	
	public static void playSound(String name, int volume) {
		int source = AL10.alGenSources();
		sources.add(source);
		
		SoundData data = getSound(name);
		if (data == null) {
			System.out.println("Could not find sound '" + name + "'");
			return;
		}
		
		AL10.alSourcef(source, AL10.AL_GAIN, (float)volume/100f);
		AL10.alSourcef(source, AL10.AL_PITCH, 1);
		AL10.alSource3f(source, AL10.AL_POSITION, 0, 0, 0);
		
		if (data.loop)
			AL10.alSourcef(source, AL10.AL_LOOPING, 1);
		
		AL10.alSourcei(source, AL10.AL_BUFFER, data.buf);
		AL10.alSourcePlay(source);
	}
	
	public static void loadWAVBuffer(String fName, boolean loop) {
		int vAudio = AL10.alGenBuffers();
		WaveData wavFile = WaveData.create(new BufferedInputStream(Sound.class.getResourceAsStream(fName)));
		AL10.alBufferData(vAudio, wavFile.format, wavFile.data, wavFile.samplerate);
		wavFile.dispose();
		
		String[] last = fName.split("/");
		String[] name = last[last.length - 1].split("[.]");
		
		addSound(new SoundData(vAudio, loop, name[0]));
	}

	public static void destroy() {
		for (SoundData data : sounds) {
			AL10.alDeleteBuffers(data.buf);
		}
		
		for (Integer i : sources) {
			AL10.alDeleteSources(i);
		}
		
		AL.destroy();
	}
	
	public static boolean isInitialized() {
		return isInitialized;
	}
	
}