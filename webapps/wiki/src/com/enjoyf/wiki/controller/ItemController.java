package com.enjoyf.wiki.controller;

import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.bean.JoymeItem;
import com.enjoyf.wiki.bean.JoymeSubItemBean;
import com.enjoyf.wiki.bean.PageBean;
import com.enjoyf.wiki.container.ChannelContainer;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.service.JoymeItemService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;


@Controller
public class ItemController {
    private static JoymeItemService service = new JoymeItemService();

    @RequestMapping("/wiki/ac/itempage.do")
    public ModelAndView templatePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/wiki/ac/item/itempage");
    }

    @RequestMapping("/wiki/ac/createItem.do")
    public ModelAndView createItem(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "wikitype") String wikiType) throws Exception {
        //wiki
        request.setAttribute("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        //渠道
        request.setAttribute("channelMap", ChannelContainer.channelHtmlFacotryMap);
        request.setAttribute("wikitype", wikiType);
        return new ModelAndView("/wiki/ac/item/createitem");

    }

    @RequestMapping("/wiki/ac/createItemAction.do")
    public ModelAndView createItemAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int joymeItemId = Integer.valueOf(request.getParameter("joymeItemId"));
        String wiki = request.getParameter("wiki");
        String channel = request.getParameter("channel");
        int isIndex = Integer.parseInt(request.getParameter("isIndex"));
        String isDefaultKey = request.getParameter("isDefaultKey");
        String itemDescription = request.getParameter("itemDescription");
        String itemKey = "";

        String wikiType = request.getParameter("wikitype");

        if (isDefaultKey.equals("1")) { //基本key值
            itemKey = request.getParameter("defaultIndexKey");
        } else {
            itemKey = request.getParameter("customIndexKey");
        }

        String itemType = request.getParameter("itemType");
        String itemProperites = "";
        String itemContext = "";
        if (itemType.equals("image")) { //基本图片类型
            itemProperites = service.getImageItemProperties(request);
            itemContext = service.getImageContext(request);
        } else if (itemType.equals("textlink")) {
            itemProperites = service.getTextlinkProperties(request);
            itemContext = service.getTextlinkContext(request);
        } else if (itemType.equals("flash")) {
            itemProperites = service.getFlashProperties(request);
            itemContext = service.getFlashContext(request);
        } else if (itemType.equals("iframe")) {
            itemProperites = service.getIframeProperties(request);
            itemContext = service.getIframeContext(request);
        } else if (itemType.equals("HTML")) {
            itemProperites = service.getHTMLProperties(request);
            itemContext = service.getHTMLContext(request);
        }

        JoymeItem item = new JoymeItem();
        item.setChannel(channel);
        item.setCreateDate(new Timestamp(System.currentTimeMillis()));
        item.setIsIndex(isIndex);
        item.setItemContext(itemContext);
        item.setItemDescription(itemDescription);
        item.setItemKey(itemKey);
        item.setItemProperties(itemProperites);
        item.setItemType(itemType);
        item.setWiki(wiki);
        item.setContextPath(wikiType);
        item.setJoymeItemId(joymeItemId);
        service.insertOrUpdate(isIndex, item, wikiType);

        return new ModelAndView("/wiki/ac/item/createitemAction");
    }

    @RequestMapping("/wiki/ac/itemList.do")
    public ModelAndView itemList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        request.setAttribute("channelMap", ChannelContainer.channelHtmlFacotryMap);

        String wiki = request.getParameter("wiki");
        String channel = request.getParameter("channel");
        String wikiType = request.getParameter("wikitype");
        int isIndex = 0;
        if (!StringUtil.isEmpty(request.getParameter("isIndex"))) {
            isIndex = Integer.parseInt(request.getParameter("isIndex"));
        }
        int pageNum = 1;
        if (!StringUtil.isEmpty(request.getParameter("pageNum"))) {
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        }

        String itemKey = request.getParameter("itemkey");
        PageBean bean = service.queryJoymeItemByCondition(null, wiki, channel, isIndex, itemKey, wikiType, pageNum);
        request.setAttribute("result", bean);
        return new ModelAndView("/wiki/ac/item/listitem");
    }

    @RequestMapping("/wiki/ac/editItem.do")
    public ModelAndView editItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        request.setAttribute("channelMap", ChannelContainer.channelHtmlFacotryMap);

        String joymeItemId = request.getParameter("joymeItemId");
        JoymeItem item = service.queryJoymeItembyId(null, Integer.parseInt(joymeItemId));
        String properties = item.getItemProperties();
        JSONObject object = JSONObject.fromObject(properties);
        JoymeSubItemBean subItemBean = new JoymeSubItemBean();
        if (item.getItemType().equals("image")) {
            service.setImageLinkSubItemBean(object, subItemBean);
        } else if (item.getItemType().equals("textlink")) {
            service.setTextLinkSubItemBean(object, subItemBean);
        } else if (item.getItemType().equals("iframe")) {
            service.setIframeSubItemBean(object, subItemBean);
        } else if (item.getItemType().equals("flash")) {
            service.setFlashSubItemBean(object, subItemBean);
        } else if (item.getItemType().equals("HTML")) {
            service.setHTMLSubItemBean(object, subItemBean);
        }
//        object.getString("")

        request.setAttribute("item", item);
        request.setAttribute("subItem", subItemBean);
        String wikitype = request.getParameter("wikitype");
        request.setAttribute("wikitype", wikitype);
        request.setAttribute("joymeItemId", joymeItemId);
        return new ModelAndView("/wiki/ac/item/edititem");
    }
}
