package com.enjoyf.wiki.controller;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.wiki.bean.PageBean;
import com.enjoyf.wiki.facade.ToolsTemplate;
import com.enjoyf.wiki.search.Search;
import com.enjoyf.wiki.service.ChannelService;
import com.enjoyf.wiki.service.JoymeTemplateService;
import com.enjoyf.wiki.util.FreemarkerTemplateGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-8-22
 * Time: 上午11:57
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class SearchController {
    private ChannelService channelService = new ChannelService();
    private Search search = new Search();
    private JoymeTemplateService templateService = new JoymeTemplateService();


    @RequestMapping("/mwiki/{wikicode}/search.do")
    public ModelAndView search(HttpServletRequest request,@PathVariable(value ="wikicode") String wikicode){
        String channel = channelService.getChannelByRequest(request);
         Map map  =new HashMap();
        try {
            String template = templateService.getTemplate(wikicode, channel, 3, "mwiki");

            String word=request.getParameter("word");
            String pageNum=request.getParameter("pageNum");
            String pageCount=request.getParameter("pageCount");


            int pageNo=1;
            int pagecount=10;
            if(pageNum==null){
                try {
                    pageNo=Integer.parseInt(pageNum);
                } catch (NumberFormatException e) {
                }

            }

            if (pageCount!=null) {
                try {
                    pagecount=Integer.parseInt(pageCount);
                } catch (NumberFormatException e) {
                }
            }

            try {
                Map param=new HashMap();
                param.put("wikicode",wikicode);
                param.put("pageNum",pageNo);
                param.put("pageCount",pagecount);
                param.put("word",word);
                if(word!=null){
                    PageBean pageBean=search.search(request, wikicode, word, pageNo, pagecount);

                    param.put("pageBean",pageBean);
                }

                String searchHtml = FreemarkerTemplateGenerator.get().generateTemplate("/ftl/msearch.ftl", param);

                if (template != null) {
                    template = template.replace("{joyme:wiki_body}", searchHtml);
                } else {
                    template = wikicode.equals("wiki") ? ToolsTemplate.CARD_COMPARE_WIKI_DEFAULT.replace("{joyme:wiki_body}", searchHtml) :
                            ToolsTemplate.CARD_COMPARE_MWIKI_DEFAULT.replace("{joyme:wiki_body}", searchHtml);
                }
                map.put("result", template);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JoymeDBException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/wiki/tools/result", map);
    }
}
