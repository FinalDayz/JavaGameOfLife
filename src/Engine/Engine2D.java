package Engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JFrame;

public abstract class Engine2D {
	
	private JFrame windowFrame;
	private PaintPanel drawPanel;
	
	public int width, height,
		frameWidth, frameHeight;
	
	public double frame = 0;
	public Camera camera;
	
	public int mouseX = -1;
	public int mouseY = -1;
	
	public int prefMouseX = -1;
	public int prefMouseY = -1;
	
	public double frameTime = 0;
	private double totalFrameTime = 0;
	
	private int curFrameTimeMeassure = 0;
	private int frameTimeMeassure = 50;
	
	protected int keyPressed = -1;
	
	public Engine2D() {
		camera = new Camera(Camera.MODE_CENTER);
	}
	
	public void createWindow(int width, int height) {
		frameWidth = width;
		frameHeight = height;
		
		windowFrame = new JFrame();
		windowFrame.setSize(width, height);
		windowFrame.setVisible(true);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		drawPanel = new PaintPanel(this);
		
		windowFrame.setContentPane(drawPanel);
		
		
		initialize(this);
		
	}
	
	
	protected void initialize(final Engine2D engine) {
		
		windowFrame.addKeyListener(new KeyAdapter() {
			@Override
		    public void keyTyped(KeyEvent e) {
				engine.keyTyped(e);
			}

		    @Override
		    public void keyPressed(KeyEvent e) {
		    	keyPressed = e.getKeyCode();
		    	engine.keyPressed(e);
		    }

		    @Override
		    public void keyReleased(KeyEvent e) {
		    	if(keyPressed == e.getKeyCode())
		    		keyPressed = -1;
		    	engine.keyReleased(e);
		    }
		});
		
		windowFrame.addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				engine.mousePressed(e);
		    }
			@Override
			public void mouseReleased(MouseEvent e) {
				engine.mouseReleased(e);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				engine.mouseClicked(e);
			}
		
		});
		
		windowFrame.addMouseWheelListener(new MouseAdapter() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				engine.mouseWheelMoved(e);
			}
		});
		
		windowFrame.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMove(e);
				engine.mouseDragged(e);
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseMove(e);
				
				
				engine.mouseMoved(e);
			}
		});
		
		java.awt.EventQueue.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	        	engine.width = drawPanel.getWidth();
	        	engine.height = drawPanel.getHeight();
	        	camera.initialize(engine.width, engine.height, windowFrame.getWidth(),windowFrame.getHeight() + 20);
	        }
	    });
	}
	
	protected void mouseMove(MouseEvent e) {
		prefMouseX = mouseX;
		prefMouseY = mouseY;
		
		if(mouseX == -1 && mouseY == -1) {
			prefMouseX = e.getX();
			prefMouseY = e.getY();
		}
		
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	abstract void keyTyped(KeyEvent e);

    abstract void keyPressed(KeyEvent e);

    abstract void keyReleased(KeyEvent e);
	
	abstract void mouseMoved(MouseEvent e);

	abstract void mouseDragged(MouseEvent e);

	abstract void mouseReleased(MouseEvent e);

	abstract void mouseWheelMoved(MouseWheelEvent e);
	
	abstract void mousePressed(MouseEvent e);
	
	abstract void mouseClicked(MouseEvent e);

	protected void paintFrame(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		
		g.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		long beginFrame = System.nanoTime();
		camera.displayGraphics(g);
		
		
		frame++;
		
		drawFrame(g);
		camera.drawAbsolute(g);
		drawAbsolute(g);
		totalFrameTime += (System.nanoTime() - beginFrame) / 1000.0 / 1000.0;
		curFrameTimeMeassure++;
		
		if(curFrameTimeMeassure > frameTimeMeassure) {
			frameTime = totalFrameTime / curFrameTimeMeassure;
			curFrameTimeMeassure = 0;
			totalFrameTime = 0;
		}
		
	}
	
	abstract void drawFrame(Graphics2D g);
	
	abstract void drawAbsolute(Graphics2D g);
	
	
	public void println(Object o) {
		System.out.println(o);
	}
}
