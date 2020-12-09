package Engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class PaintPanel extends JPanel {
	
	private Engine2D engine;
	
	public PaintPanel(Engine2D engine) {
		super();
		this.engine = engine;
	}
	
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		
		engine.paintFrame(gr);
		
		try {
			Thread.sleep(Math.max(0, 16 - Math.round(engine.frameTime)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.repaint();
    }
}
