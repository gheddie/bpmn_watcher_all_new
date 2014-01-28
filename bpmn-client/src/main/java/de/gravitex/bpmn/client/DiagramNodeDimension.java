package de.gravitex.bpmn.client;

public class DiagramNodeDimension {

	private Double x;
	
	private Double y;
	
	private Double width;
	
	private Double height;

	private boolean selected;

	public DiagramNodeDimension(Double x, Double y, Double width, Double height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Double getX() {
		return x;
	}
	
	public Double getY() {
		return y;
	}
	
	public Double getWidth() {
		return width;
	}
	
	public Double getHeight() {
		return height;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;		
	}

	public boolean includes(int xCoord, int yCoord) {
		return ((xCoord >= x && xCoord <= (x+width)) && (yCoord >= y && yCoord <= (y+height)));
	}
}
