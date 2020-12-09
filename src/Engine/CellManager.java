package Engine;

public class CellManager {
	Cell[][] cells;
	
	public CellManager() {
		cells = new Cell[600][600];
		
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells[x].length; y++) {
				cells[x][y] = new Cell(x, y);
			}
		}
	}
	
	
	public void forward() {
		for(int x = 1; x < cells.length-1; x++) {
			for(int y = 1; y < cells[x].length-1; y++) {
				
				Cell cell = cells[x][y];
				
				calculateCells(x, y, cell);
			}
		}
		
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells[x].length; y++) {
				
				handleCell(cells[x][y]);
				
			}
		}
		
	}
	
	public void handleCell(Cell cell) {
		cell.forward();
	}
	
	public void calculateCells(int x, int y, Cell cell) {
		int aliveAround = 0;
		for(int x2 = x - 1; x2 <= x + 1; x2++) {
			for(int y2 = y - 1; y2 <= y + 1; y2++) {
				if(!(x2 == x && y2 == y)) {
					//if(cell.isAlive())
					//System.out.println(x2 + " "+ y2+" - " + x+" "+ y);
					if(cells[x2][y2].isAlive()) {
						aliveAround++;
					}
				}
			}
		}
		
		
		if(cell.isAlive()) {
			//System.out.println(aliveAround);
			if(aliveAround < 2 || aliveAround > 3) {
				cell.goingToDie();
			}
			
		}else if(aliveAround == 3) {
			cell.goingToLife();
		}
	}
	
	
	
	
	public boolean cellAlive(int x, int y) {
		return cells[x][y].isAlive();
	}
}
