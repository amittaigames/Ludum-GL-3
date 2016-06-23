package test;

import com.amittaigames.lgl3.CoreGame;
import com.amittaigames.lgl3.Keys;
import com.amittaigames.lgl3.Plane;
import com.amittaigames.lgl3.Render;
import com.amittaigames.lgl3.Window;

public class Test extends CoreGame {

	private Plane plane;
	private float speed = 3.5f;
	
	public static void main(String[] args) {
		Window.init("Ludum GL 3", 800, 600, new Test());
	}
	
	@Override
	public void init() {
		plane = new Plane(100, 100, 100, 100);
	}

	@Override
	public void render(Render r) {
		r.clear(0, 128, 128);
		
		r.drawPlane(plane);
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