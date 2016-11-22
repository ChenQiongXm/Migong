package cq.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import cq.floyd.Point;

public class MyFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {		
		new MyFrame();
	}
	private MyPanel myPanel;
	private JPanel dPanel, uPanel;
	private JButton bt;
	private JLabel label1;
	public MyFrame() {
		// TODO Auto-generated constructor stub
		myPanel=new MyPanel(10,11);
		bt=new JButton("start");
		label1=new JLabel("迷宫找最短路径");
		dPanel=new JPanel();
		uPanel=new JPanel();
		uPanel.add(label1);
		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Thread(myPanel).start();
			}
		});
		dPanel.add(bt);
		add(myPanel,BorderLayout.CENTER);
		add(dPanel, BorderLayout.SOUTH);
		add(uPanel, BorderLayout.NORTH);
		this.addMouseListener(myPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(700, 500);

	}
}
class MyPanel extends JPanel implements Runnable,MouseInputListener{
	private static final long serialVersionUID = 1L;
	private Cell[][]cells;
	private Cell start=null;
	private Cell end=null;
	private Cell now=null;
	Color start_before=Color.gray;
	Color end_before=Color.gray;
	public MyPanel(int m,int n) {
		cells=new Cell[m][n];
		// TODO Auto-generated constructor stub
		for (int i = 0; i <n; i++) {
			for (int j = 0; j <m; j++) {
				cells[j][i]=new Cell(new Point(i, j));
			}
		}
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				cells[i][j].draw(g);;
			}
		}
	}
	@Override
	public void run() {
		solution1();
	}
	/**
	 * 问题一：怎么保存路径
	 * 问题二：用什么数据结构存储
	 * 问题三：怎么结束循环
	 * @return
	 */
	private void solution1()
	{
		now=start;
		now.setName("start");
		ArrayList<Cell>cellArray=new ArrayList<>();
		cellArray.add(now);	
		int index=0;
		int count=0;
		int temp=0;
		boolean flag=false;
		while (now!=end) {
			try {
				Thread.sleep(1200);
				this.repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count=0;
			for (int j2 = index; j2 <=temp; j2++)
			{
				now=cellArray.get(j2);
				int i=now.getY();
				int j=now.getX();
				if (checkPoint(i, j + 1)||checkPoint(i, j - 1)||checkPoint(i - 1, j)||checkPoint(i + 1, j))
				{
					if (checkPoint(i, j + 1))
					{
						cells[i][j+1].setName(now.getName()+">r");
						if(cells[i][j+1]==end){
							flag=true;
							now=cells[i][j+1];
							break;
						}
						cellArray.add(cells[i][j+1]);
						cells[i][j+1].setColor(Color.LIGHT_GRAY);
						count++;
					}
					if (checkPoint(i, j - 1))
					{
						cells[i][j-1].setName(now.getName()+">l");
						if(cells[i][j-1]==end){
							flag=true;
							now=cells[i][j-1];
							break;
						}
						cellArray.add(cells[i][j-1]);
						count++;					
						cells[i][j-1].setColor(Color.LIGHT_GRAY);
						}
					if (checkPoint(i + 1, j))
					{
						cells[i+1][j].setName(now.getName()+">d");
						if(cells[i+1][j]==end){
							flag=true;
							now=cells[i+1][j];
							break;
						}
						cellArray.add(cells[i+1][j]);
						count++;					
						cells[i+1][j].setColor(Color.LIGHT_GRAY);
					}
					if (checkPoint(i - 1, j))
					{
						cells[i-1][j].setName(now.getName()+">u");
						if(cells[i-1][j]==end){
							flag=true;
							now=cells[i-1][j];
							break;
						}
						cells[i-1][j].setColor(Color.LIGHT_GRAY);
						cellArray.add(cells[i-1][j]);
						count++;					
					} 
				}
				if(now!=start)now.setColor(Color.cyan);
			}
			if(flag){
				System.out.println("找到出口了，路线为："+now.getName());
				break;
			}
			if(count==0){
				flag=false;
				System.out.println("没有找到出口，此迷宫无解");
				break;
			}
			index=temp;
			temp+=count;
			// TODO Auto-generated method stub
		}
	}
	private void solution2(){
		now=start;
		while (now!=end) {
			try {
				Thread.sleep(500);
				this.repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int i=now.getY();
			int j=now.getX();
			if(checkPoint(i,j+1)){
				now.next=cells[i][++j];
				cells[i][j].pre=now;
				now=cells[i][j];
				now.setColor(Color.red);
			}else if(checkPoint(i,j-1)){
				now.next=cells[i][--j];
				cells[i][j].pre=now;
				now=cells[i][j];
				now.setColor(Color.red);
			}else if(checkPoint(i+1,j)){
				now.next=cells[++i][j];
				cells[i][j].pre=now;
				now=cells[i][j];
				now.setColor(Color.red);
			}else if(checkPoint(i-1,j)){
				now.next=cells[--i][j];
				cells[i][j].pre=now;
				now=cells[i][j];
				now.setColor(Color.red);
			}else{
				now.setColor(Color.BLACK);
				now=now.pre;
				//now.setColor(Color.BLACK);
			}
			if(now==end){
				now.setColor(Color.cyan);
				System.out.println("找到了");
			}
			if(now==start){
				System.out.println("没有路");
				break;
			}
			// TODO Auto-generated method stub

		}
	}
	public boolean checkPoint(int i,int j){		
		if(i<0||i>=cells.length||j<0||j>=cells[0].length)return false;
		if(cells[i][j].getColor()==Color.gray||cells[i][j].getColor()==Color.green){
			return true;
		}
		else return false;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		java.awt.Point point = e.getPoint();
		int j=(point.x-200)/30;
		int i=(point.y-50)/30-2;
		if(i<cells.length&&j>=0&&cells[0].length>j&&i>=0){
			if(start==null)
			{
				if(end==cells[i][j]){
					cells[i][j].setColor(end_before);
					end=null;
				}else{
					start_before=cells[i][j].getColor();
					cells[i][j].setColor(Color.RED);
					start=cells[i][j];
				}
			}else {
				if(start==cells[i][j]){
					cells[i][j].setColor(start_before);
					start=null;
				}else if(end==cells[i][j]){
					cells[i][j].setColor(end_before);
					end=null;
				}else if(end==null){
					end_before=cells[i][j].getColor();
					cells[i][j].setColor(Color.green);
					end=cells[i][j];
				}
			}
			repaint();
		}
		
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}