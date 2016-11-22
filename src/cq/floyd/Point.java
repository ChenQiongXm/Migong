package cq.floyd;

public class Point {
	private int x;
	private int y;
	private int value;
	public Point(){
		
	}
	public Point(int i, int j) {
		super();
		this.y = j;
		this.x = i;
		this.value = (int)(Math.random()*3);
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
