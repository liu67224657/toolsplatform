package com.enjoyf.mcms.factory.impl;

import com.enjoyf.mcms.bean.JoymePointArchive;
import com.enjoyf.mcms.util.MarticleUtil;
import com.enjoyf.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 14/11/14
 * Description:
 */
public class WebViewParseFactoryImpl extends AbstractParseFactoryImpl {

    protected void updateImg(int aid, Document document, String channel) throws Exception {
        Elements elements = document.getElementsByTag(IMAGE_NODE);
        int i = 0;
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            i++;

            Element element = (Element) iterator.next();
            if (element.hasAttr("width")) {
                try {
                    int width = Integer.parseInt(element.attr("width"));
                    if (width > 240)
                        element.attr("width", "100%");
                } catch (Exception e) {
                    element.attr("width", "100%");
                }
            } else {
                element.attr("width", "100%");
            }

            if (element.hasAttr("height"))
                element.removeAttr("height");

            // 修改src的路径
            // 如果是http://www.joyme.com/article/uploads开头的，需要先去找找upapp里面有没有相同的图片
            Map<String, String> map = MarticleUtil.channelMap;
            String imagePath = getImgPath(element.attr("src"));

            //该方法下面是延迟加载和图片方法需要的class 暂时只有玩霸210有
            if (StringUtil.isEmpty(imagePath)) {
                continue;
            }
            if (map != null && !StringUtil.isEmpty(map.get(channel))) {
                element.attr("data-src", imagePath);
                element.attr("src", "http://lib.joyme.com/static/theme/default/images/data-bg.gif");
                element.attr("class", "lazy img-big");
                if (!StringUtil.isEmpty(imagePath)) {
                    BufferedImage bufferedImage = getBufferedImage(imagePath);
                    if (bufferedImage != null) {
                        int width = bufferedImage.getWidth();
                        int height = bufferedImage.getHeight();
                        element.attr("width", String.valueOf(width));
                        element.attr("height", String.valueOf(height));
                        if (width < 50) {
                            element.attr("class", "lazy");
                        }
                    }

                }
            } else {
                element.attr("src", imagePath);
            }

            if (i == 1) { // 放入第一张图片的地址
                JoymePointArchive bean = new JoymePointArchive();
                bean.setArchiveId((long) aid);
                bean.setImageUrl(imagePath);
                joymePointArchiveService.updateJoymePointArchiveImageUrlByAId(null, bean);
            }

            // 修改onclick
            if (element.hasAttr("onclick")) {
                element.removeAttr("onclick");
            }
//            element.parent().tagName().equalsIgnoreCase("a");
//            element.parent().replaceWith(element);
        }
    }

    public static BufferedImage getBufferedImage(String imgUrl) {
        URL url = null;
        InputStream is = null;
        BufferedImage img = null;
        try {
            url = new URL(imgUrl);
            is = url.openStream();
            img = ImageIO.read(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return img;
    }

}
