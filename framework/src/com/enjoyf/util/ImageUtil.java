package com.enjoyf.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.*;
import javax.imageio.stream.FileImageOutputStream;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

import util.imageio.UtilImage;

public class ImageUtil {
    protected ImageWriter imgWrier;
    protected ImageWriteParam imgWriteParams;

    /**
     * 输入宽度，自适应高度
     * 
     * @param src
     * @param des
     * @param width
     * @param quality
     * @throws IOException
     */
    public void compressJPG(File src, File des, int width, float quality) throws IOException {
        FileImageOutputStream fileImageOutputStream = null;
        try {
            // 压缩前后的JPEG文件
            if (!des.exists()) {
                des.createNewFile();
            }

            // 压缩百分比
            if (des.exists()) {
                des.delete();
            }

            Image image = javax.imageio.ImageIO.read(src);
            int srcWidth = image.getWidth(null);
            int srcHeight = image.getHeight(null);
            int height = width * srcHeight / srcWidth;

            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(image, 0, 0, width, height, null); // 绘制缩小后的图
            Iterator it = ImageIO.getImageWritersBySuffix("jpg");
            if (it.hasNext()) {
                fileImageOutputStream = new FileImageOutputStream(des);
                ImageWriter iw = (ImageWriter) it.next();
                ImageWriteParam iwp = iw.getDefaultWriteParam();
                iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                iwp.setCompressionQuality(quality);
                iw.setOutput(fileImageOutputStream);
                iw.write(null, new IIOImage(tag, null, null), iwp);
                iw.dispose();
            }
        } finally {
            if (fileImageOutputStream != null) {
                fileImageOutputStream.flush();
                fileImageOutputStream.close();
            }
        }
    }

    public void compressPNG(File sourceFile, File targetFile) throws IOException {
        BufferedImage image = ImageIO.read(sourceFile);
        try {
            UtilImage.write(image, "png", targetFile);
        } finally {
            if (image != null) {
                image.flush();
                image = null;
            }
        }
    }

    public int getWidth(File file) throws IOException {
        Image image = javax.imageio.ImageIO.read(file);
        try {
            return image.getWidth(null);
        } finally {
            image.flush();
            image = null;
        }
    }

    public int getHeight(File file) throws IOException {
        Image image = javax.imageio.ImageIO.read(file);
        try {
            return image.getHeight(null);
        } finally {
            image.flush();
            image = null;
        }
    }

    public void ChangeJPG(File srcFile, File targetFile, int targetWidth, float quality) throws Exception {
        FileImageOutputStream out = null;
        Image src = javax.imageio.ImageIO.read(srcFile); // 构造Image对象
        BufferedImage tag = null;
        try {
            int srcWidth = src.getWidth(null);// 得到源图宽
            int srcHeight = src.getHeight(null); // 得到源图长
            int targetHeight = targetWidth * srcHeight / srcWidth;
            tag = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(src, 0, 0, targetWidth, targetHeight, null); // 绘制缩小后的图

            out = new FileImageOutputStream(targetFile); // 输出到文件流
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            JPEGEncodeParam encoder_param = encoder.getDefaultJPEGEncodeParam(tag);

//            String formatName = targetFile.getName().substring(targetFile.getName().lastIndexOf(".") + 1);

            Iterator it = ImageIO.getImageWritersBySuffix("jpg");
            ImageWriter iw = (ImageWriter) it.next();
            ImageWriteParam iwp = iw.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(quality);
            iw.setOutput(out);
            iw.write(null, new IIOImage(tag, null, null), iwp);
            iw.dispose();

//            ImageIO.write(tag, /*"GIF"*/ formatName /* format desired */ , targetFile /* target */ );

//            encoder_param.setQuality(quality, true); // Image compress rate
//            encoder.setJPEGEncodeParam(encoder_param);
//            encoder.encode(tag); // 近JPEG编码
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
            if (src != null) {
                src.flush();
                src = null;
            }
            if (tag != null) {
                tag.flush();
                tag = null;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new ImageUtil().ChangeJPG(new File("/Users/ericliu/Desktop/b.jpg"),new File("/Users/ericliu/Desktop/a.jpg"),4800,1.0f);
    }
}
