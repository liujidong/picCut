package person.ljd.tools;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.Graphics;
import javax.imageio.ImageIO;

public class ImageCut {

	  /** */
    /**
     * 图像切割（改）
     * 
     * @param srcImageFile
     *            源图像地址
     * @param x
     *            目标切片起点x坐标
     * @param y
     *            目标切片起点y坐标
     * @param destWidth
     *            目标切片宽度
     * @param destHeight
     *            目标切片高度
     */
    public static void abscut(String srcImageFile, int y,int destHeight,int indexY) {
    	int x	=	0;
        try {
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getWidth(); // 源图宽度
            int destWidth = srcWidth;	//------add-----------
            int srcHeight = bi.getHeight(); // 源图高度
            if(destHeight<0){
            	destHeight	=	srcHeight-y;
            }
            System.out.println("srcWidth= " + srcWidth + "\tsrcHeight= "
                    + srcHeight);
            if (srcWidth >= destWidth && srcHeight >= destHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight,
                        Image.SCALE_DEFAULT);
                // 改进的想法:是否可用多线程加快切割速度
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
                img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(destWidth, destHeight,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                // 输出为文件
                int dotPos = srcImageFile.lastIndexOf(".");
                File newDir = new File(srcImageFile.substring(0,dotPos));
                if(!newDir.exists()){
                	newDir.mkdir();
                }
                //String tarImageFile = srcImageFile.substring(0,dotPos)+File.separator+indexY+srcImageFile.substring(dotPos);
                String tarImageFile = srcImageFile.substring(0,dotPos)+File.separator+indexY+".jpg";
                ImageIO.write(tag, "JPEG", new File(tarImageFile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
