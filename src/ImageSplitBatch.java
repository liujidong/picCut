import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;

import javax.imageio.ImageIO;

public class ImageSplitBatch {

	public static void main(String[] args) throws Exception{
		if(args.length<3) {
			System.out.println("Usage:ImageSplitBatch fileDir wn hn isHeightFirst");
			System.exit(-1);
		}
		int wn = Integer.parseInt(args[1]);
		int hn = Integer.parseInt(args[2]);
		boolean isHeightFirst = args.length>=4?Boolean.parseBoolean(args[3]) : false;
		 /** 
         * 要处理的图片目录 
         */ 
        File dir = new File(args[0]);
        /** 
         * 列出目录中的图片，得到数组 
         */ 
        File[] files = dir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				//return Arrays.asList(".png",".jpg").contains(name);
				return name.endsWith(".jpg") || name.endsWith(".png");
			}
		});
        //创建输出目录
        File dirOut = new File(dir,"out");        
        if(dirOut.exists()==false&& files.length>0) {
        	dirOut.mkdir();
        }
        for (File file : files) {
        	try {
				BufferedImage bi = ImageIO.read(file); 
				 int width = bi.getWidth(); 
				 int height = bi.getHeight();
				 int wd = width/wn;
				 int hd = height/hn;
					String fileName = file.getName();
					String extension=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
					String name = fileName.substring(0,fileName.lastIndexOf("."));		
					System.out.println(fileName);	         
				 if(isHeightFirst) {
					 for (int w = 0; w < wn; w++) {
				    	 for (int h = 0; h < hn; h++) {
							BufferedImage subImage = bi.getSubimage (w*wd,h*hd, wd, hd);

							File fo = new File(dirOut,name+w+h+"."+extension);
							ImageIO.write(subImage, extension, fo);							
						}					
					}

				 }else {
			    	 for (int h = 0; h < hn; h++) {
			    		 for (int w = 0; w < wn; w++) {
							BufferedImage subImage = bi.getSubimage (w*wd,h*hd, wd, hd);

							File fo = new File(dirOut,name+w+h+"."+extension);
							ImageIO.write(subImage, extension, fo);							
						}					
					}					 
				 }
			} catch (Exception e) {
				System.err.println("error at:"+file.getAbsolutePath());
			}
		}
	}
	
}
