package com.enjoyf.mcms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.facade.ArchiveFacade;
import com.enjoyf.mcms.service.ChannelService;

@Controller
public class SearchController {
    @RequestMapping("/archivelist/more.do")
    public ModelAndView addTopic(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArchiveFacade.getArchiveList(request);
        String channel = ChannelService.getChannel(request);
        String retPath = ConfigContainer.getMoreLink(channel);
        return new ModelAndView(retPath);
    }
}
