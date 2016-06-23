package test;

import com.amittaigames.lgl3.CoreGame;
import com.amittaigames.lgl3.Plane;
import com.amittaigames.lgl3.Render;
import com.amittaigames.lgl3.Window;

public class Test extends CoreGame {

	private Plane plane;
	
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
		
	}

}