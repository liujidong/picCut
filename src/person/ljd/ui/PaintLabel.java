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
	int heightImg;
	List<Integer> ls_y	= new ArrayList<Integer>();
	public PaintLabel(Icon image) {
		super(image);
	}
	public void setYpos(int y)
	{
		ypos = y;
		repaint();
	}
	public int getYpos() {
		return ypos;
	}
	
	public List<Integer> getLs_y() {
		return ls_y;
	}
	public void addLs_y() {
		this.ls_y.add(ypos);
	}
	
	public int getHeightImg() {
		return heightImg;
	}
	public void setHeightImg(int heightImg) {
		this.heightImg = heightImg;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.drawString("画一幅图片", 20, 20);
		g.setColor(Color.cyan);
		if(ypos>0){
			g.drawLine(0,ypos,this.getWidth(),ypos);
		}
		if(ls_y.size()>0){
			for (Integer y : ls_y) {
				g.drawLine(0,y,this.getWidth(),y);
			}
		}
	}
	
}