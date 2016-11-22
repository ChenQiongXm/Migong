package cq.view;

import java.awt.Color;
import java.awt.Graphics;

import cq.floyd.Point;

public class Cell {
	private Color color;
	private int x,y;
	private String name;
	Cell pre,next;
	public Cell(Point point) {
		super();
		this.x = point.getX();
		this.y=point.getY();
		if(point.getValue()>0){
			this.color=Color.gray;
		}else this.color=Color.yellow;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
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

	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(color);
		g.fill3DRect(200+x*30,y*30+50,30,30,true);
		g.setColor(c);
	}
}
