package person.ljd.ui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import person.ljd.tools.ImageCut;

public class PicCutFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnOpen = new JButton("选择图片");
	JFileChooser chooser = new JFileChooser();
	private PaintLabel lblImage = null; 
	private JSlider jy = null;
	private JSlider jx	=	null;
	private JButton btn_line_ok = new JButton("标记线确定");
	private JButton btn_cut	=	new JButton("切割");
	private JButton btn_clear = new JButton("清空");
	//JButton btn_test = new JButton("画直线");
	private String imgPath;
	BufferedImage image;
	public PicCutFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		//---------------ui-------------------------------
		this.setTitle("图片切割");
		//setLocationRelativeTo(null);//窗体居中
		Container c = this.getContentPane();
		JToolBar toolbar = new JToolBar();
		toolbar.add(btnOpen);
		c.add(toolbar,BorderLayout.NORTH);
		
		chooser.setCurrentDirectory(new File("。"));
		chooser.setFileFilter(new FileNameExtensionFilter(
				"image files", "jpg","jpeg","gif","png"));
		
		JPanel jp_east = new JPanel();
		jp_east.setLayout(new GridLayout(5, 1));
		//jp_east.setPreferredSize(new Dimension(100, 250));
		jp_east.add(btn_line_ok);
		jp_east.add(btn_cut);
		jp_east.add(btn_clear);
		//jp_east.add(btn_test);
		jp_east.add(new JLabel(softInfo()));
		c.add(BorderLayout.EAST,jp_east);
		//--------------event-------------------------------------
		btnOpen.addActionListener(this);
		btn_line_ok.addActionListener(this);
		btn_cut.addActionListener(this);
		btn_clear.addActionListener(this);
		//btn_test.addActionListener(this);
		//图片拖动处理
		new DropTarget(c,new DropImgListener(this));
		//pack();
		setSize(1000,820);
		setVisible(true);
	}
	public static String softInfo(){
		StringBuffer infoHtml = new StringBuffer();
		infoHtml.append("<html>\n");
		//infoHtml.append(oldFile).append(":\n");
		infoHtml.append("<ul>\n");
		infoHtml.append("<li>593098937@qq.com</li>\n");
		infoHtml.append("<li>制作：刘继东</li>\n");
		infoHtml.append("<li>日期：2021-11</li>\n");
		infoHtml.append("</ul>\n");
		infoHtml.append("</html>\n");
		return infoHtml.toString();
	}
	public static void main (String []args)
	{
		new PicCutFrame();//.show();
	}
	public void showImage(String imgPath){
		this.imgPath = imgPath;
		try {
			//-------------------ui-----------------------
			image = ImageIO.read(new File(imgPath));
			int theWidth=image.getWidth();
			int theHeigh=image.getHeight();
			lblImage = new PaintLabel(new ImageIcon(imgPath)); 
			lblImage.setOpaque(false);
			JScrollPane scroll_img = new JScrollPane(lblImage);
			//加载图像
			getContentPane().add(BorderLayout.CENTER,scroll_img);
			//构建拖动条
			jy = new  JSlider(JSlider.VERTICAL,0,theHeigh,0);
			//则反转滑块显示的值范围
			jy.setInverted(true);
			//jy.setPreferredSize(new Dimension(20, theHeigh));
			JScrollPane scroll_slider = new JScrollPane(jy);
			getContentPane().add(BorderLayout.WEST,scroll_slider);
			jx	=	new JSlider(JSlider.HORIZONTAL, 0, theWidth,0);
			getContentPane().add(BorderLayout.SOUTH,jx);
			//-------------------event---------------------------
			jy.addChangeListener(new ChangeY());
			jx.addChangeListener(new ChangeX());
			
			this.validate();
		} catch (IOException e1) {
			System.out.println("图片加载出错！");
			e1.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e){
		Object source =	e.getSource();
		if (source == btnOpen) {
			int result = chooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				//txtLog.append("File:" + chooser.getSelectedFile() + " is opened!\n");
				showImage(chooser.getSelectedFile().getAbsolutePath());
			}
		}
		else if(source==btn_line_ok){
			lblImage.addYpos();
			lblImage.addXpos();
		}else if(source == btn_clear){
			lblImage.clearLs();
		}
		else if(source==btn_cut){
			List<Integer> lsY = lblImage.getLsY();
			List<Integer> lsX = lblImage.getLsX();
	    		lsX.add(0);
	    		lsX.add(image.getWidth());
	    		lsY.add(0);
	    		lsY.add(image.getHeight());
			ImageCut.abscutBatch(imgPath, lsX, lsY);
			JOptionPane.showMessageDialog(null, "操作成功！");
		}/*else if(source == btn_test) {
			lblImage.getGraphics().drawLine(10, 10, 200, 200);
		}*/
	}

	class ChangeY implements ChangeListener{
		public void stateChanged(ChangeEvent e){
			lblImage.setYpos(((JSlider)e.getSource()).getValue());
		}
	}	
	class ChangeX implements ChangeListener{
		public void stateChanged(ChangeEvent e){
			lblImage.setXpos(((JSlider)e.getSource()).getValue());
		}
	}
}
