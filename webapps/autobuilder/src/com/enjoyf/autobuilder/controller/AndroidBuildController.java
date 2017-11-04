package com.enjoyf.autobuilder.controller;

import com.enjoyf.autobuilder.bean.AndroidParamBean;
import com.enjoyf.autobuilder.bean.BuildLog;
import com.enjoyf.autobuilder.bean.Resource;
import com.enjoyf.autobuilder.bean.Src;
import com.enjoyf.autobuilder.config.AndroidConfigContainer;
import com.enjoyf.autobuilder.parse.IParseFactory;
import com.enjoyf.autobuilder.parse.impl.DefaultParseAndroidFactoryImpl;
import com.enjoyf.autobuilder.service.BuildLogService;
import com.enjoyf.autobuilder.service.BuildWebService;
import com.enjoyf.autobuilder.service.ResourceService;
import com.enjoyf.autobuilder.service.SrcService;
import com.enjoyf.autobuilder.util.FileUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Controller
@RequestMapping("/build/android/")
public class AndroidBuildController {
    private static IParseFactory factory = new DefaultParseAndroidFactoryImpl();
    private SrcService srcService = new SrcService();
    private ResourceService resourceService = new ResourceService();
    private BuildLogService buildLogService = new BuildLogService();
    private BuildWebService buildWebService = new BuildWebService();

    @RequestMapping("/selectcode.do")
    public ModelAndView selectcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> mapMessage = new HashMap<String, Object>();
        List<Resource> resourceList = resourceService.queryResource(null);
        Set<String> pkgSet = new HashSet<String>();
        for (Resource resource : resourceList) {
            pkgSet.add(resource.getResourceCode());
        }
        mapMessage.put("pkgSet", pkgSet);
        return new ModelAndView("/build/androidsc", mapMessage);
    }

    @RequestMapping("/page.do")
    public ModelAndView page(@RequestParam(value = "code", required = true) String code) throws Exception {
        Map<String, Object> mapMessage = new HashMap<String, Object>();
        BuildLog buildLog = buildLogService.getRecentBuildLog(null, code, 0, BuildLogService.BUILD_TYPE_BUILD);
        if (buildLog != null) {
            JSONObject object = JSONObject.fromObject(buildLog.getJsonstring());
            String appkey = object.getString("appkey");
            String pkgname = object.getString("pkgname");
            int srcid = object.getInt("srcid");
            int resid = object.getInt("resid");
            String vcode = object.getString("vcode");
            String version = object.getString("version");
            String appname = object.getString("appname");
            String toptitle = object.getString("toptitle");
            String abouttitle = object.getString("abouttitle");
            String aboutcontent = object.getString("aboutcontent");
            String onlinedomain = object.getString("onlinedomain");
            String betadomain = object.getString("betadomain");
            String mcolora = object.optString("mcolora");
            String mcolorn = object.optString("mcolorn");
            String umengKey = "";
            if (object.has("umengkey")) {
                umengKey = object.getString("umengkey");
            }
            String notifyUrl = "";
            if (object.has("notifyurl")) {
                notifyUrl = object.getString("notifyurl");
            }
            String wikihost = "";
            if (object.has("wikihost")) {
                notifyUrl = object.getString("wikihost");
            }
            mapMessage.put("appkey", appkey);
            mapMessage.put("appname", appname);
            mapMessage.put("pkgname", pkgname);
            mapMessage.put("srcid", srcid);
            mapMessage.put("resid", resid);
            mapMessage.put("vcode", vcode);
            mapMessage.put("version", version);
            mapMessage.put("toptitle", toptitle);
            mapMessage.put("abouttitle", abouttitle);
            mapMessage.put("aboutcontent", aboutcontent);
            mapMessage.put("onlinedomain", onlinedomain);
            mapMessage.put("betadomain", betadomain);
            mapMessage.put("mcolora", mcolora);
            mapMessage.put("mcolorn", mcolorn);
            mapMessage.put("umengkey", umengKey);
            mapMessage.put("notifyurl", notifyUrl);
            mapMessage.put("wikihost", wikihost);
        } else {
            mapMessage.put("pkgname", code);
            mapMessage.put("aboutcontent", "版本 Android - ");
        }
        List<Src> srcList = srcService.querySrc(null);
        List<Resource> resourceList = resourceService.queryResource(null);
        mapMessage.put("srcList", srcList);
        mapMessage.put("resourceList", resourceList);
        return new ModelAndView("/build/androidpage", mapMessage);
    }

    @RequestMapping("/build.do")
    public ModelAndView page(@RequestParam(value = "pkgname", required = true) String pkgname,
                             @RequestParam(value = "appkey", required = true) String appkey,
                             @RequestParam(value = "umengkey", required = true) String umengKey,
                             @RequestParam(value = "srcid", required = true) Integer srcid,
                             @RequestParam(value = "resid", required = true) Integer resid,
                             @RequestParam(value = "vcode", required = true) String versionCode,
                             @RequestParam(value = "version", required = true) String version,
                             @RequestParam(value = "appname", required = true) String appName,
                             @RequestParam(value = "appshortname", required = true) String appShortName,
                             @RequestParam(value = "toptitle", required = true) String topTitle,
                             @RequestParam(value = "abouttitle", required = true) String aboutTitle,
                             @RequestParam(value = "aboutcontent", required = true) String aboutContent,
                             @RequestParam(value = "onlinedomain", required = true) String onlinedomain,
                             @RequestParam(value = "notifyurl", required = false) String notifyurl,
                             @RequestParam(value = "betadomain", required = false) String betadomain,
                             @RequestParam(value = "mcolora", required = false) String mcolora,
                             @RequestParam(value = "wikihost", required = false) String wikihost,
                             @RequestParam(value = "mcolorn", required = false) String mcolorn,
                             @RequestParam(value = "app_name_flag", required = false) String appNameFlag,
                             @RequestParam(value = "host", required = false) String host,
                             @RequestParam(value = "image_url_host", required = false) String image_url_host
    ) throws Exception {
        Map<String, Object> mapMessage = new HashMap<String, Object>();
        boolean success = false;
        AndroidParamBean bean = new AndroidParamBean();
        bean.setAppKey(appkey);
        bean.setUmengKey(umengKey);
        Src src = srcService.querySrcbyId(null, srcid);
        bean.setSourceFileFolder(src.getSrcUrl());
        bean.setTargetFileFolder(AndroidConfigContainer.getTargetSourceFolder());
        bean.setPackageReplaceStr(pkgname);
        //设置资源文件信息
        Resource res = resourceService.queryResourcebyId(null, resid);
        bean.setSourceResFolder(res.getResourceRes());
        bean.setSourceAssertFolder(res.getResourceAssets());
        bean.setTargetResFolder(AndroidConfigContainer.getTargetResFolder());
        //设置版本信息
        bean.setVersionCode(versionCode);
        bean.setVersionName(version);
        bean.setWikiHost(wikihost);
        //设置app信息
        bean.setAppName(appName);
        bean.setTopTitle(topTitle);
        bean.setAboutTitleTxt(aboutTitle);
        bean.setAboutCText(aboutContent);
        bean.setAppNameFlag(appNameFlag);
        bean.setAppShortName(appShortName);
        bean.setHost(host);
        bean.setImage_url_host(image_url_host);
        //设置网站的域名信息
        if (!onlinedomain.startsWith("http://")) {
            bean.setOnlineDomain("http://" + onlinedomain);
        } else {
            bean.setOnlineDomain(onlinedomain);
        }
        if (betadomain != null && betadomain.length() > 0 && !betadomain.startsWith("http://")) {
            bean.setBetaDomain("http://" + betadomain);
        } else {
            bean.setBetaDomain(betadomain);
        }
        if (notifyurl != null && notifyurl.length() > 0 && !notifyurl.startsWith("http://")) {
            bean.setNotifyUrl("http://" + notifyurl);
        } else {
            bean.setNotifyUrl(notifyurl);
        }
        bean.setUpdateHomeMenuColorActive(mcolora != null && mcolora.trim().length() > 0);
        if (bean.isUpdateHomeMenuColorActive()) {
            bean.setHomeMenuColorActive(mcolora.trim());
        }
        bean.setUpdateHomeMenuColorN(mcolorn != null && mcolorn.trim().length() > 0);
        if (bean.isUpdateHomeMenuColorN()) {
            bean.setHomeMenuColorN(mcolorn.trim());
        }
        factory.setAndroidParamBean(bean);
        factory.execute();
        JSONObject object = new JSONObject();
        object.put("appkey", appkey);
        object.put("pkgname", pkgname);
        object.put("srcid", srcid);
        object.put("resid", resid);
        object.put("vcode", versionCode);
        object.put("version", version);
        object.put("appname", appName);
        object.put("toptitle", topTitle);
        object.put("abouttitle", aboutTitle);
        object.put("aboutcontent", aboutContent);
        object.put("onlinedomain", onlinedomain);
        object.put("betadomain", betadomain);
        object.put("mcolora", mcolora);
        object.put("mcolorn", mcolorn);
        object.put("umengkey", umengKey);
        object.put("notifyurl", notifyurl);
        object.put("wikihost", wikihost);
        object.put("appnameflag", appNameFlag);
        object.put("appshortname", appShortName);
        BuildLog log = new BuildLog();
        log.setBuildLogCode(pkgname);
        log.setBuildPlatform(0);
        log.setJsonstring(object.toString());
        log.setBuildType(BuildLogService.BUILD_TYPE_BUILD);
        buildLogService.insertBuildLog(null, log);
        success = true;
        mapMessage.put("success", success);
        mapMessage.put("pkgname", pkgname);
        return new ModelAndView("/build/androidresult", mapMessage);
    }

    @RequestMapping("/package.do")
    public ModelAndView pkgAction(@RequestParam(value = "pkgname", required = false) String pkgName) throws Exception {
        Map<String, Object> mapMessage = new HashMap<String, Object>();
        if (!AndroidConfigContainer.build_flag) {
            mapMessage.put("error", "已经在打包中请勿重复打包");
            return new ModelAndView("/build/androidpkgresult", mapMessage);
        }
        runProcess(AndroidConfigContainer.build_all_cmd);
        AndroidConfigContainer.build_flag = true;
        writePkgLog(pkgName);
        return new ModelAndView("redirect:/build/android/downloadpage.do?pkgname=" + pkgName, mapMessage);
    }

    @RequestMapping("/packageone.do")
    public ModelAndView pkgOneAction(@RequestParam(value = "pkgname", required = true) String pkgName) throws Exception {
        Map<String, Object> mapMessage = new HashMap<String, Object>();
        if (!AndroidConfigContainer.build_flag) {
            mapMessage.put("error", "已经在打包中请勿重复打包");
            return new ModelAndView("/build/androidpkgresult", mapMessage);
        }
        runProcess(AndroidConfigContainer.build_one_cmd);
        AndroidConfigContainer.build_flag = true;
        writePkgLog(pkgName);
        return new ModelAndView("redirect:/build/android/downloadpage.do?pkgname=" + pkgName, mapMessage);
    }

    @RequestMapping("/downloadpage.do")
    public ModelAndView downloadPage(@RequestParam(value = "pkgname", required = true) String pkgName) throws Exception {
        Map<String, Object> mapMessage = new HashMap<String, Object>();
        BuildLog buildLog = buildLogService.getRecentBuildLog(null, pkgName, 0, BuildLogService.BUILD_TYPE_PAKCAGE);
        if (buildLog == null) {
            //todo
        }
        JSONObject object = JSONObject.fromObject(buildLog.getJsonstring());
        String apkpath = object.getString("apkpath");
        List<String> apkPathList = buildWebService.getFileNames(apkpath);
        mapMessage.put("apkPathList", apkPathList);
        mapMessage.put("lid", buildLog.getBuildLogId());
        return new ModelAndView("/build/android/download", mapMessage);
    }

    @RequestMapping("/download.do")
    public ModelAndView download(@RequestParam(value = "name", required = true) String fileName,
                                 @RequestParam(value = "logid", required = true) Long logid, HttpServletResponse response) throws Exception {
        BuildLog buildLog = buildLogService.queryBuildLogbyId(null, logid);
        if (buildLog == null) {
            return null;
        }
        JSONObject object = JSONObject.fromObject(buildLog.getJsonstring());
        String apkpath = object.getString("apkpath");
        response = FileUtil.download(apkpath + "/" + fileName, response);
        return null;
    }

    private void writePkgLog(String pkgName) throws JoymeServiceException, JoymeDBException {
        String path = buildWebService.copyOutputApk(pkgName);
        JSONObject object = new JSONObject();
        object.put("pkgname", pkgName);
        object.put("apkpath", path);
        BuildLog log = new BuildLog();
        log.setBuildLogCode(pkgName);
        log.setBuildPlatform(0);
        log.setJsonstring(object.toString());
        log.setBuildType(BuildLogService.BUILD_TYPE_PAKCAGE);
        buildLogService.insertBuildLog(null, log);
    }

    private static void runProcess(String cmd) throws IOException {
        Process p = null;
        InputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            p = Runtime.getRuntime().exec(cmd);
            fis = p.getInputStream();
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(cmd + ":->" + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
            if (fis != null) {
                fis.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }
}
