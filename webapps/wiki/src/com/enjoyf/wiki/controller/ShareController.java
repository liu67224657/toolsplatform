package com.enjoyf.wiki.controller;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.bean.JoymeWikiShare;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.service.JoymeWikiShareService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-12-21 上午11:22
 * Description:
 */
@Controller
@RequestMapping("/wiki/ac/share")
public class ShareController {

    private JoymeWikiShareService shareService = new JoymeWikiShareService();

    @RequestMapping("/page.do")
    public ModelAndView page(@RequestParam(value = "pageNo", defaultValue = "1", required = false) Integer pageNo,
                             @RequestParam(value = "wiki", required = false) String wikiKey) {
        Map map = new HashMap();
        try {
            List<JoymeWikiShare> shareList = new ArrayList<JoymeWikiShare>();
            if (StringUtil.isEmpty(wikiKey)) {
                shareList = shareService.queryJoymeWikiShare(null);
            } else {
                JoymeWikiShare share = shareService.queryJoymeWikiShare(null, wikiKey);
                if (share != null) {
                    shareList.add(share);
                }
            }
            map.put("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
            map.put("wikiKey", wikiKey);
            map.put("list", shareList);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/wiki/ac/share/list", map);
    }

    @RequestMapping("/createPage.do")
    public ModelAndView createPage() {
        Map map = new HashMap();
        map.put("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        return new ModelAndView("/wiki/ac/share/createPage", map);
    }

    @RequestMapping("/create.do")
    public ModelAndView create(@RequestParam(value = "wiki", required = false) String wikiKey,
                               @RequestParam(value = "content", required = false) String content,
                               @RequestParam(value = "picurl", required = false) String picurl) {

        JoymeWikiShare wikiShare = new JoymeWikiShare();
        wikiShare.setJoymeShareContent(content);
        wikiShare.setJoymeSharePic(picurl);
        wikiShare.setJoymeWikiKey(wikiKey);

        try {
            shareService.insertJoymeWikiShare(null, wikiShare);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/wiki/ac/share/page.do?wikiKey=" + wikiKey);
    }

    @RequestMapping("/modifyPage.do")
    public ModelAndView modifyPage(@RequestParam(value = "id", required = true) Integer id) {
        Map map = new HashMap();

        try {
            JoymeWikiShare wikiShare = shareService.queryJoymeWikiSharebyId(null, id);
            map.put("wikishare", wikiShare);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/wiki/ac/share/modifyPage", map);
    }

    @RequestMapping("/modify.do")
    public ModelAndView modfiy(@RequestParam(value = "id", required = true) Integer id,
                               @RequestParam(value = "wiki", required = false) String wiki,
                               @RequestParam(value = "content", required = true) String content,
                               @RequestParam(value = "picurl", required = true) String picurl) {

        JoymeWikiShare wikiShare = new JoymeWikiShare();
        wikiShare.setJoymeShareId(id);
        wikiShare.setJoymeShareContent(content);
        wikiShare.setJoymeSharePic(picurl);
        try {
            shareService.updateJoymeWikiShare(null, wikiShare);
        } catch (JoymeDBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JoymeServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return new ModelAndView("redirect:/wiki/ac/share/page.do?wikiKey=" + wiki);
    }

    @RequestMapping("/delete.do")
    public ModelAndView delete(@RequestParam(value = "id", required = true) Integer id,
                               @RequestParam(value = "wiki", required = true) String wiki) {

        try {
            shareService.deleteJoymeWikiShare(null,id);
        } catch (JoymeDBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JoymeServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return new ModelAndView("redirect:/wiki/ac/share/page.do?wikiKey=" + wiki);
    }
}
