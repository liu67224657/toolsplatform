package com.enjoyf.mcms.controller;

import com.enjoyf.mcms.container.MemCachedContainer;
import com.enjoyf.mcms.util.VideoTools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class MovieController {
    private static VideoTools tools = new VideoTools();
    private final static String MOVIE_FLAG = "MV_";
    private final static String NO_MP4_FLAG = "noMp4";

    @RequestMapping("/marticle/movie/getmovie.do")
    public ModelAndView addTopic(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String key = MOVIE_FLAG + id;
        String url = "";
        if (MemCachedContainer.mcc.get(key) != null) {
            url = MemCachedContainer.mcc.get(key) + "";
        } else {
            url = tools.getMP4ViedoUrl(id);

            if (url == null)
                url = NO_MP4_FLAG;
            // 15分钟后失效
            MemCachedContainer.mcc.set(key, url, new Date(15 * 60 * 1000));
        }

        if (url != null && !url.equals(NO_MP4_FLAG)) {
            response.sendRedirect(url);
            return null;
        } else {
            return null;
        }
    }
}
