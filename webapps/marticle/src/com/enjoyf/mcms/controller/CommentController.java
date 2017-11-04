package com.enjoyf.mcms.controller;

import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.service.DedeArchivesService;
import com.enjoyf.mcms.util.HTTPUtil;
import com.enjoyf.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhimingli
 * Date: 2014/12/31
 * Time: 18:07
 */
@Controller
@RequestMapping("/marticle/comment")
public class CommentController {
    private static DedeArchivesService dedeArchivesService = new DedeArchivesService();

    //手游画报1.4.3
    @RequestMapping("/{domain}/{unikey}/list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable(value = "domain") String domain,
                             @PathVariable(value = "unikey") String unikey) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("unikey", unikey);
        map.put("domain", domain);
        if (!StringUtil.isEmpty(unikey)) {
            DedeArchives dedeArchives = dedeArchivesService.queryDedeArchivesbyId(null, Integer.valueOf(unikey));
            String clientPic = "";
            String articleTitle = "";
            if (dedeArchives == null) {
                clientPic = "http://marticle.joyme.com/img/default_client_pic.png";
                articleTitle = "正文";
            } else {
                if (dedeArchives.getClientpic().startsWith("http://")) {
                    clientPic = dedeArchives.getClientpic();
                } else {
                    clientPic = ConfigContainer.getCMSDomain() + dedeArchives.getClientpic();
                }

                articleTitle = dedeArchives.getTitle();
            }
            map.put("articleTitle", articleTitle);
            map.put("clientPic", clientPic);
        }
        return new ModelAndView("/opinion/list", map);
    }

    //手游画报2.0-着迷玩霸,评论详情是调用natvie
    @RequestMapping("/{domain}/{unikey}/client")
    public ModelAndView client(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable(value = "domain") String domain,
                               @PathVariable(value = "unikey") String unikey,
                               @RequestParam(value = "channel", required = true, defaultValue = "default") String channel) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("unikey", unikey);
        map.put("domain", domain);

        String articleTitle = "评论";
        if (!StringUtil.isEmpty(unikey)) {
            DedeArchives dedeArchives = dedeArchivesService.queryDedeArchivesbyId(null, Integer.valueOf(unikey));
            if (dedeArchives != null) {
                articleTitle = dedeArchives.getTitle();
            }
        }
        map.put("articleTitle", articleTitle);

//        String JParam = request.getHeader("JParam");
//        map.put("JParam", JParam);

        //取用户的登录信息
        String uid = HTTPUtil.getParam(request, "uid");
        String logindomain = HTTPUtil.getParam(request, "logindomain");
        String token = HTTPUtil.getParam(request, "token");
        String uno = HTTPUtil.getParam(request, "uno");
        map.put("token", token);
        map.put("uno", uno);
        map.put("uid", uid);
        map.put("logindomain", logindomain);
        map.put("marticle_host", ConfigContainer.getMarticleHost());
        // System.out.println("JParam==" + JParam + ",token=" + token + ",uno=" + uno + ",uid=" + uid + ",logindomain=" + logindomain);
        return new ModelAndView("/opinion/list-" + channel, map);
    }

}
