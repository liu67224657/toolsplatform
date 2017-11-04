package com.enjoyf.wiki.controller;

import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.bean.PageBean;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.service.WikiPageService;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-5-19
 * Time: 下午1:48
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/wiki/ac/page")
public class WikiAcPageController {

    private WikiPageService wikiPageService = new WikiPageService();


    @RequestMapping("/wikipagelist.do")
    public ModelAndView wikipagelist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<WikiPage> list = wikiPageService.getWikiPageCount(null);
        request.setAttribute("list", list);
        request.setAttribute("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        return new ModelAndView("/wiki/ac/page/wikipagelist");
    }

    @RequestMapping("/addwikipage.do")
    public ModelAndView addwikipage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/wiki/ac/page/addwikipage");
    }

    @RequestMapping("/doaddwiki.do")
    public ModelAndView doaddwiki(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<WikiPage> list = wikiPageService.getWikiPageCount(null);
        request.setAttribute("list", list);


        String wiki_key = request.getParameter("wiki_key");
        if (StringUtil.isEmpty(wiki_key) || !wiki_key.equals("wiki")) {
            return new ModelAndView("/wiki/ac/page/wikipagelist");
        }

        String wiki_url = request.getParameter("wiki_url");
        wikiPageService.getWikiPageIdByChineseURL(null, wiki_key, wiki_url);

        return new ModelAndView("/wiki/ac/page/wikipagelist");

    }


    @RequestMapping("/export.do")
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String wikikey = request.getParameter("wikikey");
        List<WikiPage> list = wikiPageService.getWikiPageListByWikiKey(null, wikikey);
        ex(response, list, wikikey);
    }


    @RequestMapping("/keywikilist.do")
    public ModelAndView keywikilist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String wikikey = request.getParameter("wikikey");
        List<WikiPage> list = wikiPageService.getWikiPageListByWikiKey(null, wikikey);
        request.setAttribute("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        request.setAttribute("list", list);
        return new ModelAndView("/wiki/ac/page/keywikilist");
    }

    @RequestMapping("/searchwikipage.do")
    public ModelAndView searchwikipage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String wikikey = request.getParameter("wikikey");
        String searchtitle = request.getParameter("searchtitle");
        String pagenum = request.getParameter("pagenum");
        if (StringUtil.isEmpty(searchtitle)) {
            return new ModelAndView("redirect:/wiki/ac/page/wikipagelist.do");
        }
        if (StringUtil.isEmpty(pagenum)) {
            pagenum = "1";
        }
        int page = Integer.valueOf(pagenum);
        PageBean pageBean = wikiPageService.searchWikiPageList(null, wikikey, searchtitle, 1, page, 200);
        List<WikiPage> list = pageBean.getRetList();
        request.setAttribute("list", list);
        request.setAttribute("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        return new ModelAndView("/wiki/ac/page/keywikilist");
    }

    public String getPath() {
        return PropertiesContainer.getInstance().getCacheFolder() + "/wikipageexcel/";
    }

    private void ex(HttpServletResponse response, List<WikiPage> list, String wikikey) {
        File file = null;
        PrintWriter out = null;
        try {
            out = response.getWriter();
            File filepath = new File(getPath());
            if (!filepath.exists()) {
                filepath.mkdirs();
            }
            file = new File(filepath + "/" + wikikey + ".xls");
            //单元格格式
            WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,
                    WritableFont.BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, Colour.GREY_50_PERCENT);
            WritableCellFormat wcf = new WritableCellFormat(wf);
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
            wcf.setAlignment(Alignment.CENTRE);

            WritableWorkbook wwb = Workbook.createWorkbook(file);
            WritableSheet ws = wwb.createSheet(wikikey, 0);
            String[] columns = {"wiki_url", "URL"};
            for (int i = 0; i < columns.length; i++) {
                ws.addCell(new Label(i, 0, columns[i], wcf));
                ws.setColumnView(i, 50);
            }
            boolean supportSubDomain = PropertiesContainer.getInstance().supportSubDomain(wikikey, "wiki");
            for (int i = 0; i < list.size(); i++) {
                WikiPage cf = list.get(i);
                ws.addCell(new Label(0, i + 1, String.valueOf(cf.getWikiUrl())));
                if (supportSubDomain) {
                    ws.addCell(new Label(1, i + 1, "http://" + wikikey + "." + PropertiesContainer.getInstance().getSubDomain() + "/" + cf.getPageId() + ".shtml"));
                } else {
                    ws.addCell(new Label(1, i + 1, "http://www." + PropertiesContainer.getInstance().getSubDomain() + "/wiki/" + wikikey + "/" + cf.getPageId() + ".shtml"));
                }
            }
            wwb.write();
            wwb.close();

            BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
            byte[] buf = new byte[1024 * 1024];
            int len = 0;
            response.reset(); // 非常重要
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + new String(file.getName().getBytes(), "iso-8859-1"));

            OutputStream outStream = response.getOutputStream();
            while ((len = br.read(buf)) > 0)
                outStream.write(buf, 0, len);
            br.close();
            outStream.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            // out.print("<script>alert('导出用户信息失败、请从试!');</script>");
        } finally {
            if (file.exists()) {// 下载完毕删除文件
                file.delete();
            }
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
