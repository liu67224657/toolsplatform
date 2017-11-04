package com.enjoyf.mcms.controller;

import com.enjoyf.mcms.bean.JoymeImage;
import com.enjoyf.mcms.bean.JoymeSpec;
import com.enjoyf.mcms.bean.temp.AppRedirectType;
import com.enjoyf.mcms.bean.temp.JoymeImageType;
import com.enjoyf.mcms.bean.temp.ReplaceImageBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.facade.AdminConsoleFacade;
import com.enjoyf.mcms.service.JoymeImageService;
import com.enjoyf.mcms.service.JoymeSpecService;
import com.enjoyf.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-12-12 上午11:31
 * Description:
 */
@Controller
@RequestMapping("/marticle/ac/rollimage")
public class RollImageController {
    private JoymeImageService service = new JoymeImageService();
    private AdminConsoleFacade facade = new AdminConsoleFacade();
    private static JoymeSpecService joymeSpecService = new JoymeSpecService();

    @RequestMapping("/bigpage.do")
    public ModelAndView bigPage(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "specId", required = true) Integer specId) throws Exception {
        String filePath = request.getParameter("filePath");
        JoymeSpec joymeSpec = joymeSpecService.queryJoymeSpecbyId(null, specId);


        List imageList = service.queryJoymeImage(null, joymeSpec.getSpecId(), JoymeImageType.TYPE_BIG_CLIENT);
        request.setAttribute("joymeSpec", joymeSpec);
        request.setAttribute("imageList", imageList);
        request.setAttribute("filePath", filePath);
        request.setAttribute("redirectTypes", AppRedirectType.getAll());

        return new ModelAndView("/marticle/ac/rollimage/create_bigrollimage");
    }

    @RequestMapping("/bigcreateaction.do")
    public ModelAndView bigCreateAction(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestParam(value = "title", required = false) String title,
                                        @RequestParam(value = "link", required = true) String link,
                                        @RequestParam(value = "pic", required = true) String pic,
                                        @RequestParam(value = "specId", required = true) Integer specId,
                                        @RequestParam(value = "redirectType", required = true) Integer redirectType) throws Exception {
        String filePath = request.getParameter("filePath");

        int displayOrder = 0;
        try {
            displayOrder = request.getParameter("displayorder") == null ? 0 : Integer.parseInt(request.getParameter("displayorder"));
        } catch (NumberFormatException e) {
        }

        String localPath = ConfigContainer.getLocalPath(request);

        ReplaceImageBean bean = new ReplaceImageBean();
        bean.setFilePath(filePath);
        bean.setLocalPath(localPath);
        bean.setSpecId(specId);
        JoymeImage imageBean = new JoymeImage();
        imageBean.setImageType(JoymeImageType.TYPE_BIG_CLIENT);
        imageBean.setTitle(title);
        imageBean.setPic(pic);
        imageBean.setSpecid(specId);
        imageBean.setLink(link);
        imageBean.setRedirectType(redirectType);
        imageBean.setDisplayorder(displayOrder);
        bean.setJoymeImage(imageBean);

        facade.insertCreateJoymeImage(bean);

        Map map = new HashMap();
        map.put("filePath", filePath);
        return new ModelAndView("redirect:/marticle/ac/rollimage/bigpage.do?filePath=" + filePath+"&specId="+specId);
    }

    @RequestMapping("/deleteaction.do")
    public ModelAndView deleteAction(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value = "specId", required = true) Integer specId,
                                     @RequestParam(value = "imgId", required = true) Integer imageId) throws Exception {
        String filePath = request.getParameter("filePath");


        String localPath = ConfigContainer.getLocalPath(request);

        ReplaceImageBean bean = new ReplaceImageBean();
        bean.setFilePath(filePath);
        bean.setLocalPath(localPath);
        bean.setImageId(imageId);
        bean.setSpecId(specId);
        facade.insertDeleteJoymeImage(bean);

        Map map = new HashMap();
        map.put("filePath", filePath);
        return new ModelAndView("redirect:/marticle/ac/rollimage/bigpage.do?filePath=" + filePath+"&specId="+specId);
    }

    @RequestMapping("/bigmodifyaction.do")
    public ModelAndView bigModifyAction(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestParam(value = "imgId", required = true) Integer imageId,
                                        @RequestParam(value = "title", required = false) String title,
                                        @RequestParam(value = "link", required = true) String link,
                                        @RequestParam(value = "pic", required = true) String pic,
                                        @RequestParam(value = "specId", required = true) Integer specId,
                                        @RequestParam(value = "redirectType", required = true) Integer redirectType) throws Exception {
        String filePath = request.getParameter("filePath");

        int displayOrder = 0;
        try {
            displayOrder = request.getParameter("displayorder") == null ? 0 : Integer.parseInt(request.getParameter("displayorder"));
        } catch (NumberFormatException e) {
        }

        String localPath = ConfigContainer.getLocalPath(request);

        ReplaceImageBean bean = new ReplaceImageBean();
        bean.setFilePath(filePath);
        bean.setLocalPath(localPath);
        bean.setSpecId(specId);

        JoymeImage imageBean = new JoymeImage();
        imageBean.setImageType(JoymeImageType.TYPE_BIG_CLIENT);
        imageBean.setTitle(title);
        imageBean.setPic(pic);
        imageBean.setSpecid(specId);
        imageBean.setLink(link);
        imageBean.setRedirectType(redirectType);
        imageBean.setImageId(imageId);
        imageBean.setDisplayorder(displayOrder);
        bean.setJoymeImage(imageBean);

        facade.insertUpdateJoymeImage(bean);

        Map map = new HashMap();
        map.put("filePath", filePath);
        return new ModelAndView("redirect:/marticle/ac/rollimage/bigpage.do?filePath=" + filePath+"&specId="+specId);
    }

    @RequestMapping("/smallpage.do")
    public ModelAndView smallPage(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam(value = "specId", required = false) String specId) throws Exception {
        String filePath = request.getParameter("filePath");

        specId = StringUtil.isEmpty(specId) ? "0" : specId;

        List imageList = service.queryJoymeImage(null, Integer.parseInt(specId), JoymeImageType.TYPE_SMALL_CLIENT);
        request.setAttribute("specId", specId);
        request.setAttribute("imageList", imageList);
        request.setAttribute("filePath", filePath);
        request.setAttribute("redirectTypes", AppRedirectType.getAll());

        return new ModelAndView("/marticle/ac/rollimage/create_smallrollimage");
    }

    @RequestMapping("/smallcreateaction.do")
    public ModelAndView createAction(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "link", required = true) String link,
                                     @RequestParam(value = "pic", required = true) String pic,
                                     @RequestParam(value = "specId", required = true) Integer specId) throws Exception {
        String filePath = request.getParameter("filePath");

        int displayOrder = 0;
        try {
            displayOrder = request.getParameter("displayorder") == null ? 0 : Integer.parseInt(request.getParameter("displayorder"));
        } catch (NumberFormatException e) {
        }

        String localPath = ConfigContainer.getLocalPath(request);

        ReplaceImageBean bean = new ReplaceImageBean();
        bean.setFilePath(filePath);
        bean.setLocalPath(localPath);
        bean.setSpecId(specId);
        JoymeImage imageBean = new JoymeImage();
        imageBean.setImageType(JoymeImageType.TYPE_SMALL_CLIENT);
        imageBean.setTitle(title);
        imageBean.setPic(pic);
        imageBean.setSpecid(specId);
        imageBean.setLink(link);
        imageBean.setDisplayorder(displayOrder);
        bean.setJoymeImage(imageBean);

        facade.insertCreateJoymeImage(bean);

        Map map = new HashMap();
        map.put("filePath", filePath);
        return new ModelAndView("redirect:/marticle/ac/rollimage/smallpage.do?filePath=" + filePath + "&specId=" + specId);
    }

    @RequestMapping("/smallmodifyaction.do")
    public ModelAndView createAction(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value = "imgId", required = true) Integer imageId,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "link", required = true) String link,
                                     @RequestParam(value = "pic", required = true) String pic,
                                     @RequestParam(value = "specId", required = true) Integer specId
    ) throws Exception {
        String filePath = request.getParameter("filePath");

        int displayOrder = 0;
        try {
            displayOrder = request.getParameter("displayorder") == null ? 0 : Integer.parseInt(request.getParameter("displayorder"));
        } catch (NumberFormatException e) {
        }

        String localPath = ConfigContainer.getLocalPath(request);

        ReplaceImageBean bean = new ReplaceImageBean();
        bean.setFilePath(filePath);
        bean.setLocalPath(localPath);
        bean.setSpecId(specId);

        JoymeImage imageBean = new JoymeImage();
        imageBean.setImageType(JoymeImageType.TYPE_SMALL_CLIENT);
        imageBean.setTitle(title);
        imageBean.setPic(pic);
        imageBean.setSpecid(specId);
        imageBean.setLink(link);
        imageBean.setImageId(imageId);
        imageBean.setDisplayorder(displayOrder);
        bean.setJoymeImage(imageBean);

        facade.insertUpdateJoymeImage(bean);

        Map map = new HashMap();
        map.put("filePath", filePath);
        return new ModelAndView("redirect:/marticle/ac/rollimage/smallpage.do?filePath=" + filePath + "&specId=" + specId);
    }

    @RequestMapping("/smalldeleteaction.do")
    public ModelAndView smallDeleteAction(HttpServletRequest request,
                                          HttpServletResponse response,
                                          @RequestParam(value = "specId", required = true) Integer specId,
                                          @RequestParam(value = "imgId", required = true) Integer imageId) throws Exception {
        String filePath = request.getParameter("filePath");


        String localPath = ConfigContainer.getLocalPath(request);

        ReplaceImageBean bean = new ReplaceImageBean();
        bean.setFilePath(filePath);
        bean.setLocalPath(localPath);
        bean.setImageId(imageId);
        bean.setSpecId(specId);
        facade.insertDeleteJoymeImage(bean);

        Map map = new HashMap();
        map.put("filePath", filePath);
        return new ModelAndView("redirect:/marticle/ac/rollimage/smallpage.do?filePath=" + filePath + "&specId=" + specId);
    }

}
