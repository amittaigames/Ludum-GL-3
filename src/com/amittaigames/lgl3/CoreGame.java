package com.amittaigames.lgl3;

public abstract class CoreGame {

	public CoreGame() {
		Natives.load();
	}
	
	public abstract void init();
	public abstract void render(Render r);
	public abstract void update();
	
}