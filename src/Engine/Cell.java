package Engine;

public class Cell {
	int x, y, level;
	
	boolean goingToDie, goingToLife = false;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Cell(int x, int y, int level) {
		this.x = x;
		this.y = y;
		this.level = level;
	}
	
	public Cell(int x, int y, boolean alive) {
		this.x = x;
		this.y = y;
		if(alive)
			this.level = 3;
	}
	
	public void addLevel() {
		this.level++;
	}
	
	public void resetLevel() {
		this.level = 0;
	}
	
	public boolean isAlive() {
		return (this.level == 3 || this.level == 2);
	}
	
	void goingToDie() {
		goingToDie = true;
	}
	
	void goingToLife() {
		goingToLife = true;
	}

	public void forward() {
		if(goingToLife)
			level = 3;
		else if(goingToDie)
			level = 0;
		goingToDie = false;
		goingToLife = false;
	}
	
	void toggle() {
		if(level == 3)
			level = 0;
		else 
			level = 3;
	}
	
	void setAlive() {
		level = 3;
	}
	
	void setDead() {
		level = 0;
	}
	
}
