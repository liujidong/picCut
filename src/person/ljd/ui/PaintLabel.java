package person.ljd.ui;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;

public class PaintLabel extends JLabel {
	/**
	 *   背景是图片的可以画线的Label
	 */
	private static final long serialVersionUID = 1L;
	int ypos;
	int xpos;
	//int heightImg;
	List<Integer> lsY	= new ArrayList<Integer>();
	List<Integer> lsX = new ArrayList<Integer>();
	public PaintLabel(Icon image) {
		super(image);
	}
	public void setYpos(int y)
	{
		ypos = y;
		repaint();
	}
	public void setXpos(int xpos) {
		this.xpos = xpos;
		repaint();
	}
	
	public List<Integer> getLsY() {
		return lsY;
	}
	public void addYpos() {
		if(ypos > 0){
			this.lsY.add(ypos);
		}
	}
	
	public List<Integer> getLsX() {
		return lsX;
	}
	public void addXpos(){
		if(xpos > 0){
			this.lsX.add(xpos);
		}
	}
	
	public void clearLs(){
		lsY	= new ArrayList<Integer>();
		lsX = new ArrayList<Integer>();	
		repaint();
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.drawString("画一幅图片", 20, 20);
		//当前的标记线
		g.setColor(Color.orange);		
		if(ypos>0){
			g.drawLine(0,ypos,this.getWidth(),ypos);
		}
		if(xpos > 0){
			g.drawLine(xpos, 0, xpos, this.getHeight());
		}
		
		//已确定的标记线
		g.setColor(Color.cyan);
		if(lsY.size()>0){
			for (Integer y : lsY) {
				g.drawLine(0,y,this.getWidth(),y);
			}
		}
		if(lsX.size() > 0){
			for (Integer x : lsX) {
				g.drawLine(x, 0, x, this.getHeight());
			}
		}
	}
	
}