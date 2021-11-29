import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * from https://blog.csdn.net/mayifan_blog/article/details/84038120
 * @author USER
 * 2021年11月29日
 */
public class ImageCutSub {

	public static void main(String[] args) {
		for (int i = 2795; i <= 2825; i++) {
			File f = new File("C:\\Users\\USER\\Pictures\\Screenshots\\屏幕截图("+i+").png");
			try {
				String fileName = f.getName();
				String extension=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
				String name = fileName.substring(0,fileName.lastIndexOf("."));		
				System.out.println(fileName);
//			System.out.println(name);
//			System.out.println(extension);
				//if(true){ return;}
				BufferedImage bufImage = ImageIO.read(f);
				BufferedImage subImage = bufImage.getSubimage (393,163, 1467, 829);

				File fo = new File(f.getParent(),name+".sub."+extension);
				ImageIO.write(subImage, extension, fo);
			} catch (IOException e) {
				System.err.println("error at:"+f.getAbsolutePath());
				//e.printStackTrace();
			}				
		}
	}

}
