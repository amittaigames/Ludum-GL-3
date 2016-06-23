package test;

import com.amittaigames.lgl3.CoreGame;
import com.amittaigames.lgl3.Mesh;
import com.amittaigames.lgl3.Render;
import com.amittaigames.lgl3.Window;

public class Test extends CoreGame {

	private Mesh m;
	
	public static void main(String[] args) {
		Window.init("Ludum GL 3", 800, 600, new Test());
	}
	
	@Override
	public void init() {
		float[] pos = {
			100, 100,
			100, 200,
			200, 200,
			200, 100
		};
		
		float[] color = {
			1, 1, 1,
			1, 1, 1,
			1, 1, 1,
			1, 1, 1
		};
		
		int[] list = {
			0, 1, 2,
			0, 3, 2
		};
		
		m = new Mesh(pos, color, list);
	}

	@Override
	public void render(Render r) {
		r.clear(0, 128, 128);
		
		r.drawMesh(m);
	}

	@Override
	public void update() {
		
	}

}