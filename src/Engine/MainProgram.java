package Engine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.SwingUtilities;

public class MainProgram extends Engine2D {
	
	private boolean draggedAlive = false;
	private int playSpeed = 1;
	private boolean play = false;
	
	public static void main(String[] args) {
		new MainProgram().createWindow(1600, 1000);
	}
	
	CellManager cellManager;
	
	public MainProgram() {
		super();
		camera.setMaxZoom(15);
		camera.setMinZoom(0.1);
		cellManager = new CellManager();
		cellManager.cells[100][102].toggle();;
		
	}
	
	
	void drawFrame(Graphics2D g) {
		int minX = (int) Math.round(-300);
		int maxX = (int) Math.round(300);
		
		g.setStroke(new BasicStroke(1f));
		
		for(int i = minX; i < maxX; i++) {
			g.drawLine(i * 10, minX * 10, i * 10, maxX * 10);
		}
		
		int minY = (int) Math.round(-300);
		int maxY = (int) Math.round(300);
		
		for(int i = minY; i < maxY; i++) {
			g.drawLine(minY * 10, i * 10, maxY * 10, i * 10);
		}
		
		g.setColor(Color.RED);
		//g.setStroke(new BasicStroke(1f));
		
		for(int i = minX; i < maxX; i++) {
			for(int j = minY; j < maxY; j++) {
				if(cellManager.cellAlive(i + maxX, j + maxY)) {
					
					g.setColor(Color.RED);
					g.fillRect(i * 10, j * 10, 10, 10);
					
					g.setColor(Color.BLACK);
					g.drawRect(i * 10, j * 10, 10, 10);
					
				}
			}
		}
		
		if(play) {
			for(int i = 0; i < playSpeed; i++) {
				cellManager.forward();
			}
		}
	}
	
	void drawAbsolute(Graphics2D g) {
		g.setColor(
				new Color(0, 0, 0, 0.6f)
				);
		g.fillRect(0, 0, 200, 25);
		g.setColor(Color.white);
		g.drawString("FrameTime:" + frameTime, 10, 15);
	}

	@Override
	void mouseDragged(MouseEvent e) {
		
		if(SwingUtilities.isLeftMouseButton(e) || SwingUtilities.isMiddleMouseButton(e)) {
			camera.moveWithScale(this.mouseX - this.prefMouseX, this.mouseY - this.prefMouseY);
		} 
		
		if(SwingUtilities.isRightMouseButton(e)) {
			int x = (camera.toRealWorldPosX(mouseX) +3000)/ 10;
			int y = (camera.toRealWorldPosY(mouseY) +3000)/ 10;
			
			if(!draggedAlive) {
				cellManager.cells[x][y].setAlive();
			}else {
				cellManager.cells[x][y].setDead();
			}
		}
	}

	@Override
	void mouseReleased(MouseEvent e) {
		
	}

	@Override
	void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		camera.zoomIn(-notches * 0.1);
	}

	@Override
	void mousePressed(MouseEvent e) {
		int x = (camera.toRealWorldPosX(mouseX) +3000)/ 10;
		int y = (camera.toRealWorldPosY(mouseY) +3000)/ 10;
		draggedAlive = cellManager.cells[x][y].isAlive();
	}
	
	@Override
	void mouseClicked(MouseEvent e) {
		int x = (camera.toRealWorldPosX(mouseX) +3000)/ 10;
		int y = (camera.toRealWorldPosY(mouseY) +3000)/ 10;
		cellManager.cells[x][y].toggle();

		
	}

	@Override
	void mouseMoved(MouseEvent e) {
		
	}
	
	public void println(Object o) {
		System.out.println(o);
	}


	@Override
	void keyTyped(KeyEvent e) {

		//space
		if(keyPressed == KeyEvent.VK_SPACE) {
			play = !play;
			
		}
		
		// Pressed key '1'
		if(keyPressed == KeyEvent.VK_1) {
			playSpeed = 1;
		}
		
		// Pressed key '2'
		if(keyPressed == KeyEvent.VK_2) {
			playSpeed = 4;
		}
		
		// Pressed key '3'
		if(keyPressed == KeyEvent.VK_3) {
			playSpeed = 8;
		}
		
		
		
	}


	@Override
	void keyPressed(KeyEvent e) {
		
	}


	@Override
	void keyReleased(KeyEvent e) {
		
	}
}
