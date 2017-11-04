package com.enjoyf.autobuilder.controller;

import com.enjoyf.autobuilder.bean.*;
import com.enjoyf.autobuilder.config.AndroidConfigContainer;
import com.enjoyf.autobuilder.config.BatchClientConfigContainer;
import com.enjoyf.autobuilder.config.IOSConfigContainer;
import com.enjoyf.autobuilder.parse.IParseFactory;
import com.enjoyf.autobuilder.parse.impl.DefaultBatchAndroidFactoryImpl;
import com.enjoyf.autobuilder.parse.impl.DefualtBatchIOSFactory;
import com.enjoyf.autobuilder.service.AndroidTemplateService;
import com.enjoyf.autobuilder.service.BackgroudImgService;
import com.enjoyf.autobuilder.service.BuildLogService;
import com.enjoyf.autobuilder.service.BuildWebService;
import com.enjoyf.autobuilder.util.FileUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-12-16 下午12:18
 * Description:
 */
@Controller
@RequestMapping("/batch/ios/pkg")
public class BatchIOSPkgController extends BaseController {
    AndroidTemplateService templateService = new AndroidTemplateService();
    BackgroudImgService backgroudImgService = new BackgroudImgService();


    private BuildLogService buildLogService = new BuildLogService();
    private BuildWebService buildWebService = new BuildWebService();

    private static IParseFactory factory = new DefualtBatchIOSFactory();

    @RequestMapping("/buildpage.do")
    public ModelAndView buildPage() {
        Map map = new HashMap();
        try {
            List<AndroidTemplate> list = templateService.queryTemplate(null, AppPlatform.APP_PLATFORM_IOS);

            List<BatchClientImage> backgroudImgList = backgroudImgService.queryBackgroudImg(null, AppPlatform.APP_PLATFORM_IOS, BatchClientImage.IMAGE_TYPE_BACKGROUND);

            List<BatchClientImage> iconImgList = backgroudImgService.queryBackgroudImg(null, AppPlatform.APP_PLATFORM_IOS, BatchClientImage.IMAGE_TYPE_ICON);

            List<BatchClientImage> loadingImgList = backgroudImgService.queryBackgroudImg(null, AppPlatform.APP_PLATFORM_IOS, BatchClientImage.IMAGE_TYPE_LOADINGPAGE);


            map.put("list", list);
            map.put("backgroundList", backgroudImgList);
            map.put("iconList", iconImgList);
            map.put("loadingList", loadingImgList);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/batch/ios/buildpkg", map);
    }


    @RequestMapping("/build.do")
    public ModelAndView build(@RequestParam(value = "templateid", required = true) Integer templateId,
                              @RequestParam(value = "title", required = true) String title,
                              @RequestParam(value = "backgroundid", required = false) Integer backgroundId,
                              @RequestParam(value = "iconid", required = false) Integer iconId,
                              @RequestParam(value = "loadid", required = false) Integer loadid,
                              @RequestParam(value = "code", required = false) String code,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "version", required = false) String versionName,
                              @RequestParam(value = "share", required = false) String shareContent,
                              @RequestParam(value = "channel", required = false, defaultValue = "joyme") String channel
    ) {
        try {
            AndroidTemplate template = templateService.queryAndroidTemplatebyId(null, templateId);

            BatchClientImage backgroudImg = null;
            if (backgroundId != null) {
                backgroudImg = backgroudImgService.queryBackgroudImgbyId(null, backgroundId);
            }

            BatchClientImage iconImg = backgroudImgService.queryBackgroudImgbyId(null, iconId);

            BatchClientImage loadImg = null;
            if (loadid != null) {
                loadImg = backgroudImgService.queryBackgroudImgbyId(null, loadid);
            }

            //build pkg
            BatchIOSParamBean paramBean = new BatchIOSParamBean();

            paramBean.setTemplate(template);
            paramBean.setBackgroudImg(backgroudImg);
            paramBean.setIcon(iconImg);
            paramBean.setLoading(loadImg);
            paramBean.setCode(code);
            paramBean.setTitle(title);
            paramBean.setAppName(name);
            paramBean.setVersionName(versionName);
            paramBean.setShareContent(shareContent);
            paramBean.setIosChannel(channel);

            factory.setBatchIosParamBean(paramBean);
            factory.execute();

            runProcess(BatchClientConfigContainer.batch_ios_root_folder + "/pkg_batch.sh " + paramBean.getAppkey() + " " + "tgameguide" + " " + paramBean.getCode() + " " + URLEncoder.encode(paramBean.getAppName(), "UTF-8") + " " + versionName + "");
            AndroidConfigContainer.build_flag = true;

            //write log
            String path = buildWebService.copyBatchOutputIpas(code);
            JSONObject object = new JSONObject();
            object.put("appname", name);
            object.put("pkgname", code);
            object.put("backgroundid", backgroundId);
            object.put("templateid", templateId);
            object.put("version", versionName);
            object.put("shareContent", shareContent);
            object.put("path", path);
            BuildLog log = new BuildLog();
            log.setBuildLogCode(code);
            log.setBuildPlatform(AppPlatform.APP_PLATFORM_IOS);
            log.setJsonstring(object.toString());
            log.setBuildType(BuildLogService.BUILD_TYPE_BATCHBUILD);
            buildLogService.insertBuildLog(null, log);

        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/batch/ios/pkg/downloadpage.do?pkgname=" + code);
    }


    @RequestMapping("/downloadpage.do")
    public ModelAndView downloadPage(@RequestParam(value = "pkgname", required = true) String pkgName) throws Exception {
        Map<String, Object> mapMessage = new HashMap<String, Object>();
        BuildLog buildLog = buildLogService.getRecentBuildLog(null, pkgName, AppPlatform.APP_PLATFORM_IOS, BuildLogService.BUILD_TYPE_BATCHBUILD);
        if (buildLog == null) {
            //todo
        }
        JSONObject object = JSONObject.fromObject(buildLog.getJsonstring());
        String apkpath = object.getString("path");
        List<String> apkPathList = buildWebService.getFileNames(apkpath);
        mapMessage.put("apkPathList", apkPathList);
        mapMessage.put("lid", buildLog.getBuildLogId());
        return new ModelAndView("/batch/ios/download", mapMessage);
    }

    @RequestMapping("/download.do")
    public ModelAndView download(@RequestParam(value = "name", required = true) String fileName,
                                 @RequestParam(value = "logid", required = true) Long logid, HttpServletResponse response) throws Exception {
        BuildLog buildLog = buildLogService.queryBuildLogbyId(null, logid);
        if (buildLog == null) {
            return null;
        }
        JSONObject object = JSONObject.fromObject(buildLog.getJsonstring());
        String apkpath = object.getString("path");
        response = FileUtil.download(apkpath + "/" + fileName, response);
        return null;
    }

}
