package com.enjoyf.wiki.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;


public class ImageUtil {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
//            CopyImageFromURL("E:/cat.png ", "E:/111/aaa.jpg ");
//            CopyImageFromURL("http://www.csdn.net/ui/styles/public_header_footer/logo_csdn.gif ", "E:/111/logo_csdn.jpg ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param imageURL
     *            输入图片的URL完整路径，可以是本地文件，也可以是网路上的文件
     * @param localPath
     *            保存在本地的路径
     * @throws Exception
     */
    public static void CopyImageFromURL(String imageURL, String localPath) throws Exception {
        // 取得文件后缀名
        String tmpImageURL = imageURL.toLowerCase();
        String regex = "(\\.)(gif|bmp|png|dib|jpg|jpeg|jfif) ";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tmpImageURL);
        if (!matcher.find()) {
            throw new Exception("不支持该格式文件. ");
        }
        URL url;
        if (imageURL.toLowerCase().startsWith("http ")) {// http url
            url = new URL(imageURL);
        } else {// 文件url
            String tmpUrl = imageURL.toLowerCase();
            String beginer = tmpUrl.split("/ ")[0];
            if (beginer.matches("[c-o]: ")) {// 路径支持到c:d:....o:
                url = new File(imageURL).toURL();
            } else {
                throw new Exception("无法解析文件URL. ");
            }
        }
        BufferedImage src = ImageIO.read(url);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 保存图像到本地
        int lastSep1 = localPath.lastIndexOf("/ ");
        int lastSep2 = localPath.lastIndexOf("\\ ");
        int lastSep = (lastSep1 >= lastSep2 ? lastSep1 : lastSep2);
        String path = localPath.substring(0, lastSep);
        File localDir = new File(path);
        if (!localDir.exists()) {
            localDir.mkdirs();
        }
        BufferedImage outImg = new BufferedImage(width, height, src.getType());
        outImg.getGraphics().drawImage(src, 0, 0, width, height, null);
        FileOutputStream out = new FileOutputStream(localPath);
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//        encoder.encode(outImg);

        out.close();
    }
}
