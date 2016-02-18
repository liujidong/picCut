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
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
	private JButton btn_line_ok = new JButton("标记切割线");
	private JButton btn_cut	=	new JButton("切割");
	private String imgPath;
	
	public PicCutFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		//---------------ui-------------------------------
		this.setTitle("图片切割");
		Container c = this.getContentPane();
		JToolBar toolbar = new JToolBar();
		toolbar.add(btnOpen);
		c.add(toolbar,BorderLayout.NORTH);
		
		chooser.setCurrentDirectory(new File("。"));
		chooser.setFileFilter(new FileNameExtensionFilter(
				"image files", "jpg","jpeg","gif","png"));
		
		JPanel jp_east = new JPanel();
		jp_east.setLayout(new GridLayout(4, 1));
		jp_east.add(btn_line_ok);
		jp_east.add(btn_cut);
		//jp_east.add(new JLabel(softInfo()));
		c.add(BorderLayout.EAST,jp_east);
		//--------------event-------------------------------------
		btnOpen.addActionListener(this);
		btn_line_ok.addActionListener(this);
		btn_cut.addActionListener(this);
		
		new DropTarget(c,
		         new DropImgListener(this));
		//pack();
		setSize(1000,720);
	}
	public static String softInfo(){
		StringBuffer infoHtml = new StringBuffer();
		infoHtml.append("<html>\n");
		//infoHtml.append(oldFile).append(":\n");
		infoHtml.append("<ul>\n");
		infoHtml.append("<li>liujidong2010@gmail.com</li>\n");
		infoHtml.append("<li>制作：刘继东</li>\n");
		infoHtml.append("<li>日期：2014-11-27</li>\n");
		infoHtml.append("</ul>\n");
		infoHtml.append("</html>\n");
		return infoHtml.toString();
	}
	public static void main (String []args)
	{
		new PicCutFrame().show();
	}
	public void showImage(String imgPath){
		this.imgPath = imgPath;
		try {
			//-------------------ui-----------------------
			BufferedImage image = ImageIO.read(new File(imgPath));
			//int theWidth=image.getWidth();
			int theHeigh=image.getHeight();
			lblImage = new PaintLabel(new ImageIcon(imgPath)); 
			lblImage.setOpaque(false);
			JScrollPane scroll_img = new JScrollPane(lblImage);
			//加载图像
			getContentPane().add(BorderLayout.CENTER,scroll_img);
			//构建拖动条
			jy = new  JSlider(JSlider.VERTICAL,0,theHeigh,0);
			jy.setInverted(true);
			//jy.setPreferredSize(new Dimension(20, theHeigh));
			JScrollPane scroll_slider = new JScrollPane(jy);
			getContentPane().add(BorderLayout.WEST,scroll_slider);
			//jx	=	new JSlider(JSlider.HORIZONTAL, 10, 1000,10);
			//f.getContentPane().add(BorderLayout.NORTH,jx);
			//-------------------event---------------------------
			jy.addChangeListener(new ChangeY());
			
			this.validate();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
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
			lblImage.addLs_y();
			//System.out.println("ypos:"+lblImage.getYpos());
		}else if(source==btn_cut){
			List<Integer> ls_y = lblImage.getLs_y();
			if(ls_y.size()>0){
				ls_y.add(0);
				Collections.sort(ls_y);
				for(int i=0;i<ls_y.size()-1;i++){
					int destHeight	=	ls_y.get(i+1)-ls_y.get(i);
					System.out.println("y:"+ls_y.get(i)+" height:"+destHeight);
					ImageCut.abscut(imgPath, ls_y.get(i), destHeight, i);
				}
				//last one
				int index_last = ls_y.size()-1;
				System.out.println("lastone:y:"+ls_y.get(index_last)+" height:-1");
				ImageCut.abscut(imgPath, ls_y.get(index_last), -1, index_last);
				JOptionPane.showMessageDialog(null, "操作成功！");
			}
		}
	}

	class ChangeY implements ChangeListener{
		public void stateChanged(ChangeEvent e){
			lblImage.setYpos(((JSlider)e.getSource()).getValue());
		}
	}	

}
