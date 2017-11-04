package com.enjoyf.wiki.tools;

/**
 * 
 */

/**
 * Created on 2011-5-24 Discription:[convert GIF->JPG GIF->PNG PNG->GIF(X)
 * PNG->JPG ]
 * 
 * @param source
 * @param formatName
 * @param result
 * @author:dx[hzdengxu@163.com]
 */

public class PngUtil {
    // private String[] args;
//
//    public static void convert(InputStream is, File targetFile) {
//        convert("jpg", is, targetFile);
//    }
//
//    public static void convert(String formatName, InputStream is, File targetFile) {
//        BufferedImage src = null;
//        try {
//            src = ImageIO.read(is);
//            ImageIO.write(src, formatName, targetFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if(src != null)
//                src.flush();
//        }
//    }
//
//    public static void pngConvert2Jpg(InputStream is, File targetFile) throws IOException{
//        BufferedImage src = ImageIO.read(is);
//        int width = src.getWidth(null);
//        int height = src.getHeight(null);
//
//        BufferedImage outImg = new BufferedImage(width, height, src.getType());
//        outImg.getGraphics().drawImage(src, 0, 0, width, height, null);
//        FileOutputStream out = null;
//        try{
//            out = new FileOutputStream(targetFile);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            encoder.encode(outImg);
//        } finally{
//            if(outImg != null)
//                outImg.flush();
//
//            if(src != null)
//                src.flush();
//
//            if(out != null){
//                out.flush();
//                out.close();
//                out = null;
//            }
//        }
//
//    }
//
//    public static void main(String[] args) throws Exception {
////        String file = "D:/png/2/1.png";
////        InputStream is = new FileInputStream(new File(file));
////        convert(is, new File("D:/png/2/2.jpg"));
//
//        BufferedImage image = ImageIO.read(new File("D:/png/2/2.png"));
//        File file = new File("D:/png/2/1.jpg");
//        System.out.println(UtilImage.write(image, "jpg", file));
//
//
//    }

}
