package com.enjoyf.mcms.controller;

import com.enjoyf.mcms.bean.temp.ReplaceChannelBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.facade.AdminConsoleFacade;
import com.enjoyf.mcms.service.JoymeChannelContentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-12-12 上午10:27
 * Description:
 */
@Controller
@RequestMapping("/marticle/ac/channel")
public class ChannelAdvertiseController {
    private JoymeChannelContentService service = new JoymeChannelContentService();
    private AdminConsoleFacade facade = new AdminConsoleFacade();

    @RequestMapping("/createpage.do")
    public ModelAndView createPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        facade = new AdminConsoleFacade();
        facade.getJoymeSpecByFilePath(request);
        request.setAttribute("filePath", request.getParameter("filePath"));

        return new ModelAndView("/marticle/ac/advertise/create_adv");
    }

    @RequestMapping("/createaction.do")
    public ModelAndView createAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String filePath = request.getParameter("filePath");
        String specId = request.getParameter("specId");
        String localPath = ConfigContainer.getLocalPath(request);

        Map channelMap = service.getChannelMap(request);

        ReplaceChannelBean bean = new ReplaceChannelBean();
        bean.setFilePath(filePath);
        bean.setSpecId(specId);
        bean.setChannelMap(channelMap);
        bean.setLocalPath(localPath);
        facade.createReplaceChannelMap(bean);
        
        Map map = new HashMap();
        map.put("filePath", filePath);
        return new ModelAndView("/marticle/ac/advertise/create_adv_result", map);
    }
}
