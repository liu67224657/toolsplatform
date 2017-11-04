package com.enjoyf.wiki.controller;

import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.bean.JoymeWiki;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.service.JoymeWikiService;
import com.enjoyf.wiki.service.SystemService;
import com.enjoyf.wiki.util.CollectionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by zhimingli on 2015/4/23.
 */

@Controller
public class JoymeWikiController {

    private JoymeWikiService joymeWikiService = new JoymeWikiService();

    @RequestMapping("/wiki/ac/joymewikilist.do")
    public ModelAndView joymewikilist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String wikikey = request.getParameter("wikikey");

        HashMap map = new HashMap();

        //hard code
        List<JoymeWiki> list = (List<JoymeWiki>) joymeWikiService.queryJoymeWiki(null);


        if (!StringUtil.isEmpty(wikikey)) {
            List<JoymeWiki> newlist = new ArrayList<JoymeWiki>();
            for (JoymeWiki joymeWiki : list) {
                if (joymeWiki.getJoymeWikiKey().equals(wikikey)) {
                    newlist.add(joymeWiki);
                }
            }
            if (!CollectionUtil.isEmpty(newlist)) {
                list = newlist;
            }
        }


        //
        Collections.sort(list, new Comparator<JoymeWiki>() {
            @Override
            public int compare(JoymeWiki o1, JoymeWiki o2) {
                int jt1 = o1.getJoymeWikiId();
                int jt2 = o2.getJoymeWikiId();
                return jt1 < jt2 ? 1 : (o1 == o2 ? 0 : -1);
            }
        });

        map.put("wikilist", list);
        map.put("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        map.put("wikikey", wikikey);
        return new ModelAndView("/wiki/ac/joymewiki/wikilist", map);
    }

    @RequestMapping("/wiki/ac/editjoymewiki.do")
    public ModelAndView editjoymewiki(HttpServletRequest request) throws Exception {
        HashMap map = new HashMap();

        String joymewikiid = request.getParameter("joymewikiid");

        JoymeWiki joymeWiki = null;
        if (!StringUtil.isEmpty(joymewikiid)) {
            joymeWiki = joymeWikiService.queryJoymeWikibyId(null, Integer.valueOf(joymewikiid));
        }
        map.put("joymewiki", joymeWiki);

        return new ModelAndView("/wiki/ac/joymewiki/editwiki", map);
    }


    @RequestMapping("/wiki/ac/updatejoymewiki.do")
    public ModelAndView updatejoymewiki(HttpServletRequest request) throws Exception {
        HashMap map = new HashMap();
        Integer joymeWikiId = Integer.valueOf(request.getParameter("joymeWikiId"));
        String joymeWikiKey = request.getParameter("joymeWikiKey");
        String contextPath = request.getParameter("contextPath");
        String joymeWikiDomain = request.getParameter("joymeWikiDomain");
        String joymeWikiName = request.getParameter("joymeWikiName");
        String supportSubDomain = request.getParameter("supportSubDomain");
        Integer pcKeepJscss = Integer.valueOf(request.getParameter("pcKeepJscss"));
        Integer mKeepJscss = Integer.valueOf(request.getParameter("mKeepJscss"));

        JoymeWiki joymeWiki = joymeWikiService.queryJoymeWikibyId(null, joymeWikiId);
        joymeWiki.setJoymeWikiKey(joymeWikiKey);
        joymeWiki.setContextPath(contextPath);
        joymeWiki.setJoymeWikiDomain(joymeWikiDomain);
        joymeWiki.setJoymeWikiName(joymeWikiName);
        joymeWiki.setSupportSubDomain(Boolean.valueOf(supportSubDomain));
        joymeWiki.setPcKeepJscss(pcKeepJscss);
        joymeWiki.setmKeepJscss(mKeepJscss);
        int result = joymeWikiService.updateJoymeWiki(null, joymeWiki);

        if (result > 0) {
            SystemService systemService = new SystemService();
            systemService.loadJoymeWIki();
        }

        return new ModelAndView("redirect:/wiki/ac/joymewikilist.do?wikikey=" + joymeWikiKey);
    }


}
