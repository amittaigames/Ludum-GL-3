package test;

import com.amittaigames.lgl3.CoreGame;
import com.amittaigames.lgl3.Keys;
import com.amittaigames.lgl3.Render;
import com.amittaigames.lgl3.TexturedPlane;
import com.amittaigames.lgl3.Window;

public class Test extends CoreGame {

	private TexturedPlane plane;
	private float speed = 3.5f;
	
	public static void main(String[] args) {
		Window.enable("textures", "alpha");
		Window.init("Ludum GL 3", 800, 600, new Test());
	}
	
	@Override
	public void init() {
		plane = new TexturedPlane("/textures/NewLogo512.png", 100, 100, 128, 128);
	}

	@Override 
	public void render(Render r) {
		r.clear(0, 128, 128);
		
		r.drawTexture(plane);
	}

	@Override
	public void update() {
		if (Keys.isPressed(Keys.KEY_D)) {
			plane.translate(speed, 0);
		}
		if (Keys.isPressed(Keys.KEY_A)) {
			plane.translate(-speed, 0);
		}
		if (Keys.isPressed(Keys.KEY_W)) {
			plane.translate(0, -speed);
		}
		if (Keys.isPressed(Keys.KEY_S)) {
			plane.translate(0, speed);
		}
	}

}