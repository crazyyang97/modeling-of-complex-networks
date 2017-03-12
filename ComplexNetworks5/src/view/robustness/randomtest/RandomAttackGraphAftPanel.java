package view.robustness.randomtest;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import jxl.read.biff.BiffException;
import utils.CommonUtils;
import utils.GraphUtils;
import utils.JXLUtils;
import view.MainFrame;

public class RandomAttackGraphAftPanel extends JPanel{
	public static final int WIDTH = 665;
	public static final int HEIGHT = 450;
	public static final int DIAMETER = 18;
	
	public static boolean isPaint = false;
	
	private ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
	private ArrayList<Point> locations = null;
	
	public RandomAttackGraphAftPanel(ArrayList<Point> locations){
		this.locations = locations;
	}
	
	public void setDatas(ArrayList<ArrayList<String>> datas){
		this.datas = datas;
	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(isPaint){
			paintNodes(g);
			paintEdges(g, datas);
			paintNodesName(g);
		}
	}
	
	private void paintNodesName(Graphics g) {
		for(int i =0; i<locations.size()-1; i++){
			if(!GraphUtils.isRemoved(datas, i+1)){
				Point point = locations.get(i);
				String str = ""+(i+1);
				FontMetrics fontMetrics = g.getFontMetrics();
				Color oldColor = g.getColor();
				Font oldFont = g.getFont();
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("������κ", Font.BOLD, 10));
				int strWidth = fontMetrics.stringWidth(str);
				int strHeight = fontMetrics.getHeight();
				g.drawString(str, (int)point.getX()+this.DIAMETER/2-strWidth/2, (int)point.getY()+this.DIAMETER/2+3);
				
				g.setColor(oldColor);
				g.setFont(oldFont);
			}
		}
		
	}

	private void paintEdges(Graphics g, ArrayList<ArrayList<String>> datas) {
		for(int i = 0; i<datas.size(); i++){
			if(!GraphUtils.isRemoved(datas, i+1)){
				for(int j = i+1; j<datas.get(i).size(); j++){
					if(datas.get(i).get(j).equals("y")){
						Point point1 = locations.get(i);
						Point point2 = locations.get(j);
						g.drawLine((int)(point1.getX())+this.DIAMETER/2, (int)(point1.getY())+this.DIAMETER/2,
								(int)(point2.getX())+this.DIAMETER/2, (int)(point2.getY())+this.DIAMETER/2);
					}
				}
			}
		}
	}

	private void paintNodes(Graphics g) {
		for(int i =0; i<locations.size()-1; i++){
			if(!GraphUtils.isRemoved(datas, i+1)){
				Point point = locations.get(i);
				g.fillOval((int)point.getX(), (int)point.getY(), this.DIAMETER, this.DIAMETER);
			}
		}
	}
	
	public void clear(){
		this.isPaint = false;
		this.repaint();
	}

}
