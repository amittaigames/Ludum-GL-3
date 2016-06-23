package com.amittaigames.lgl3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Natives {

	// Don't use this class in your code!
		// All of this is automatically used by Ludum GL
		
		public static void load() {
			try {
				File ludumgl = new File(SystemData.HOME + "/.ludumgl/");
				if (!ludumgl.exists()) {
					ludumgl.mkdir();
					load();
				}
				File natives = new File(ludumgl.getAbsolutePath() + "/natives/");
				if (!natives.exists()) {
					System.out.println("[Ludum GL] Copying natives...");
					natives.mkdir();
					loadNatives();
				}
				System.out.println("[Ludum GL] Loading natives...");
				System.setProperty("org.lwjgl.librarypath", natives.getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void loadNatives() throws Exception {
			if (SystemData.OS.contains("Mac")) {
				loadNative("liblwjgl.dylib");
			}
			if (SystemData.OS.contains("Windows")) {
				if (SystemData.ARCH.equals("x86"))
					loadNative("lwjgl.dll");
				else
					loadNative("lwjgl64.dll");
			}
			if (SystemData.OS.contains("Linux")) {
				if (SystemData.ARCH.equals("x86"))
					loadNative("liblwjgl.so");
				else
					loadNative("liblwjgl64.so");
			}
		}
		
		private static void loadNative(String name) throws IOException {
			InputStream in = Natives.class.getResourceAsStream("/natives/" + name);
			byte[] buf = new byte[1024];
			int read = -1;
			File nat = new File(SystemData.HOME + "/.ludumgl/natives/" + name);
			FileOutputStream fos = new FileOutputStream(nat);
			
			while ((read = in.read(buf)) != -1) {
				fos.write(buf, 0, read);
			}
			in.close();
			fos.close();
		}
	
}