package Engine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Camera {
	public static int MODE_CENTER = 1;
	public static int MODE_LEF_TUP = 2;
	
	private int mode = -1;
	private double scaleX = 1;
	private double scaleY = 1;
	private double scale = 1;
	
	private double positionX, positionY, 
	width, height, 
	maxZoom, minZoom,
	frameWidth, frameHeight;
	
	
	
	public Camera(int mode) {
		this.mode = mode;
	}
	
	void initialize(int width, int height, int frameWidth, int frameHeight) {
		this.width = width;
		this.height = height;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
	}
	
	void displayGraphics(Graphics2D g) {
		AffineTransform at = new AffineTransform();
		
		if(mode == 1)
			at.translate(width / 2.0, height / 2.0);
	
	
		at.scale(scaleX * scale, scaleY * scale);
		
		at.translate(positionX, positionY);
		g.transform(at);
	}
	
	void drawAbsolute(Graphics2D g) {
		AffineTransform at = new AffineTransform();
		
	
		at.translate(-positionX, -positionY);
		at.scale(1 / (scaleX * scale), 1 /( scaleY * scale));
		
		if(mode == 1)
			at.translate(-width / 2.0, -height / 2.0);
		g.transform(at);
	}
	
	public void move(int X, int Y) {
		this.positionX += X;
		this.positionY += Y;
	}
	
	public void moveWithScale(int X, int Y) {
		this.positionX += X / scale * scaleX;
		this.positionY += Y / scale * scaleX;
	}
	public void zoomIn(double zoomPercent) {
		this.scale *= 1 + zoomPercent;
		this.scale = Math.max(minZoom, scale);
		this.scale = Math.min(maxZoom, scale);
	}
	
	public void zoomOut(double zoomPercent) {
		this.scale /= 1 + zoomPercent;
		this.scale = Math.max(minZoom, scale);
		this.scale = Math.min(maxZoom, scale);
	}
	
	public double getScale() {
		return this.scale;
	}
	
	/*
	 * get scaleX times normal scale
	 */
	public double getScaleX() {
		return scale * scaleX;
	}
	
	/*
	 * only get scaleX
	 */
	public double getAbsoluteScaleX() {
		return scaleX;
	}
	
	/*
	 * get scaleY times normal scale
	 */
	public double getScaleY() {
		return scale * scaleY;
	}
	
	/*
	 * only get scaleY
	 */
	public double getAbsoluteScaleY() {
		return scaleY;
	}
	
	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}
	
	public void setScaleY(double scaleY) {
		this.scaleX = scaleY;
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	
	public void println(Object o) {
		System.out.println(o);
	}

	public void setMaxZoom(int d) {
		this.maxZoom = d;
	}

	public void setMinZoom(double d) {
		this.minZoom = d;
	}

	public int toRealWorldPosX(int screenX) {
		double x = screenX;
		if(mode == 1)
			x += -frameWidth / 2.0;
		x /= scaleX * scale;
		
		x -= positionX;
		return (int) Math.floor(x);
	}
	
	public int toRealWorldPosY(int screenY) {
		double y = screenY;
		if(mode == 1)
			y += -frameHeight / 2.0;
		y /= scaleY * scale;
		
		y -= positionY;
		return (int) Math.floor(y);
	}

	
}
