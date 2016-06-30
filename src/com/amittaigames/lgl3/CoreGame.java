package com.amittaigames.lgl3;

import com.amittaigames.lgl3.render.Render;

public abstract class CoreGame {

	public CoreGame() {
		Natives.load();
	}
	
	public abstract void init();
	public abstract void render(Render r);
	public abstract void update();
	
}