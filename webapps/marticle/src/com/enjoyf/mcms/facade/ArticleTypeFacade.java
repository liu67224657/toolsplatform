package com.enjoyf.mcms.facade;

import com.enjoyf.mcms.bean.DedeArctype;
import com.enjoyf.mcms.bean.temp.DedeArchivePageBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.factory.IArticleTypeParseFactory;
import com.enjoyf.mcms.service.DedeArchivesService;
import com.enjoyf.mcms.service.DedeArctypeService;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-11-18 下午1:44
 * Description:
 */
public class ArticleTypeFacade {
    private static DedeArctypeService dedeArctypeService = new DedeArctypeService();
    private static DedeArchivesService dedeArchivesService = new DedeArchivesService();


    public String generateArticleTypeHtml(String htmlFile, String channel, String url, String path)
            throws Exception {

        int pageNo = 1;
        DedeArctype arctype = null;
        if (htmlFile.lastIndexOf("index") >= 0) {
            String[] typeDirArray = url.split("/");
            String typeDir = "{cmspath}/" + typeDirArray[typeDirArray.length - 2];
            //call dao query by typeDir
            arctype = dedeArctypeService.queryDedeArctypeByTypeDir(null, typeDir);
        } else {
            //call getArticleTypePageId
            int[] pageId = getArticleTypePageId(url);
            //call dao query by pageId
            if (pageId.length == 0) {
                return null;
            }
            arctype = dedeArctypeService.queryDedeArctype(null, pageId[0]);

            pageNo = pageId.length > 1 ? pageId[1] : pageNo;
        }

        //get archives list and prase html
        DedeArchivePageBean dedeArchivePageBean = dedeArchivesService.queryDedeArchiveListByTypeId(null, arctype.getId(), pageNo);

        IArticleTypeParseFactory factory = ConfigContainer.getArticleTypeParseFactory(channel);

        return factory.makeArticleTypeHtml(dedeArchivePageBean, arctype, channel, htmlFile, path, pageNo);
    }

    private int[] getArticleTypePageId(String uri) {
        int articleId = 0;
        int pageId = 1;
        int position = uri.lastIndexOf("/");
        if (position > 0) {
            String tmp = uri.substring(position + 1, uri.length());
            int position1 = tmp.indexOf(".");
            if (position1 > 0) {
                String tagPageStr = tmp.substring(0, position1);
                tagPageStr = tagPageStr.replace("list_", "");
                int position2 = tagPageStr.indexOf("_");
                if (position2 > 0) {
                    articleId = Integer.parseInt(tagPageStr.substring(0, position2));
                    pageId = Integer.parseInt(tagPageStr.substring(position2 + 1, tagPageStr.length()));
                } else {
                    articleId = Integer.parseInt(tagPageStr);
                }
            }
        }
        return new int[]{articleId, pageId};
    }

}
