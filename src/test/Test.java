package test;

import com.amittaigames.lgl3.CoreGame;
import com.amittaigames.lgl3.Window;
import com.amittaigames.lgl3.input.Keys;
import com.amittaigames.lgl3.input.Mouse;
import com.amittaigames.lgl3.render.PointLight;
import com.amittaigames.lgl3.render.Render;
import com.amittaigames.lgl3.render.TexturedPlane;

public class Test extends CoreGame {
	
	private TexturedPlane plane;
	
	public static void main(String[] args) {
		Window.enable("textures", "alpha");
		Window.init("Lighting Test", 800, 600, new Test());
	}
	
	@Override
	public void init() {
		plane = new TexturedPlane("/textures/NewLogo512.png", 100, 100, 128, 128);
		new PointLight(100, 100, 5, 2);
	}

	@Override 
	public void render(Render r) {
		r.clear(0, 128, 128);
		
		r.drawTexture(plane);
		
		r.drawLighting(Window.WIDTH / 20, 12, 21, 48, 228);
	}

	@Override
	public void update() {
		Window.setTitle("Lighting Test - FPS: " + Window.getCurrentFPS());
		
		if (Keys.isPressed(Keys.KEY_D)) {
			plane.translate(5, 0);
		}
		if (Keys.isPressed(Keys.KEY_A)) {
			plane.translate(-5, 0);
		}
		if (Keys.isPressed(Keys.KEY_S)) {
			plane.translate(0, 5);
		}
		if (Keys.isPressed(Keys.KEY_W)) {
			plane.translate(0, -5);
		}
		
		if (Keys.isPressed(Keys.KEY_ESCAPE)) {
			System.exit(0);
		}
		
		PointLight.list.get(0).setX(Mouse.getX());
		PointLight.list.get(0).setY(Mouse.getY());
	}

}