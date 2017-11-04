package com.enjoyf.autobuilder.service;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p/>
 * Description:
 * </p>
 *
 * @author: <a href=mailto:ericliu@enjoyfound.com>liuhao</a>
 */
public class ImageGenerator {
    public static String copyImage(InputStream inputStream, double quality, String outputPath) throws IOException, IM4JavaException, InterruptedException {
        Image img = ImageIO.read(inputStream);
        ConvertCmd cmd = new ConvertCmd(true);
        IMOperation op = new IMOperation();
//        op.flatten();
//        op.quality(quality);// 压缩率
        op.addImage();// input
        op.addImage(outputPath);// output

        cmd.run(op, img);
        return outputPath;
    }

    public static String cutImage(String srcPath, int cutWidth, int cutHeight, int xAxis, int yAxis, double quality, String outPutPath) throws IOException, IM4JavaException, InterruptedException {
        ConvertCmd cutCmd = new ConvertCmd(true);

        IMOperation cutOp = new IMOperation();
        cutOp.quality(quality);// 压缩率
        cutOp.addImage();// input
        cutOp.crop(cutWidth, cutHeight, xAxis, yAxis);
        cutOp.addImage();// output
        cutCmd.run(cutOp, srcPath, outPutPath);

        return outPutPath;
    }

    public static String resizeImage(String srcPath, int resizeWidth, int resizeHeight, double quality, String outPutPath) throws IOException, IM4JavaException, InterruptedException {
        ConvertCmd cutCmd = new ConvertCmd(true);

        IMOperation cutOp = new IMOperation();
        cutOp.quality(quality);// 压缩率
        cutOp.addImage();// input
       cutOp.resize(resizeWidth, resizeHeight, '^').gravity("center").extent(resizeWidth, resizeHeight);
        cutOp.addImage();// output
        cutCmd.run(cutOp, srcPath, outPutPath);
        return outPutPath;
    }
}
