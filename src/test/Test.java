package test;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

import com.amittaigames.lgl3.CoreGame;
import com.amittaigames.lgl3.Window;
import com.amittaigames.lgl3.input.Mouse;
import com.amittaigames.lgl3.render.FontHandler;
import com.amittaigames.lgl3.render.Render;
import com.amittaigames.lgl3.render.TexturedPlane;

public class Test extends CoreGame {

	private List<TexturedPlane> planes = new ArrayList<TexturedPlane>();
	private boolean clicked = false;
	
	public static void main(String[] args) {
		Window.enable("textures", "alpha");
		Window.init("Performance Test", 800, 600, new Test());
	}
	
	@Override
	public void init() {
		FontHandler.registerFont(new Font("Arial", Font.PLAIN, 14), true);
	}

	@Override 
	public void render(Render r) {
		r.clear(255, 128, 128);
		
		for (TexturedPlane plane : planes) {
			r.drawTexture(plane);
		}
		
		FontHandler.setFont("Arial 14");
		r.drawDebugInfo();
		
		r.drawText("Object Count: " + planes.size(), 15, Window.getHeight() - 30, Color.white);
	}

	@Override
	public void update() {
		Window.setTitle("Performance Test - FPS: " + Window.getCurrentFPS());
		
		if (Mouse.isClicked(Mouse.MOUSE_LEFT)) {
			if (!clicked) {
				planes.add(new TexturedPlane("/lgl/icon128.png", Mouse.getX() - 32, Mouse.getY() - 32, 64, 64));
				clicked = true;
			}
		} else {
			clicked = false;
		}
		
		for (TexturedPlane plane : planes) {
			plane.rotate(0, 0, 1, 1.5f);
		}
	}

}