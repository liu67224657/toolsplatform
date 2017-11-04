package com.enjoyf.autobuilder.service;

import com.enjoyf.autobuilder.bean.AndroidParamBean;
import com.enjoyf.autobuilder.bean.IOSParamBean;
import com.enjoyf.autobuilder.config.AndroidConfigContainer;
import com.enjoyf.autobuilder.config.IOSConfigContainer;
import com.enjoyf.autobuilder.parse.IParseFactory;
import com.enjoyf.autobuilder.parse.impl.DefaultParseAndroidFactoryImpl;
import com.enjoyf.autobuilder.parse.impl.DefaultParseIOSFactoryImpl;

public class BuildService {
    private static IParseFactory androidfactory = new DefaultParseAndroidFactoryImpl();
    private static IParseFactory IOSfactory = new DefaultParseIOSFactoryImpl();
    private String replacePath = "";

    public void executeAndroidBuild() throws Exception {
        AndroidParamBean bean = new AndroidParamBean();
        bean.setSourceFileFolder(replacePath + "/src");
        bean.setTargetFileFolder(AndroidConfigContainer.getTargetSourceFolder());
        bean.setPackageReplaceStr("gsd");

        //设置资源文件信息
        bean.setSourceResFolder(replacePath + "/res");
        bean.setTargetResFolder(AndroidConfigContainer.getTargetResFolder());

        //设置版本信息
        bean.setVersionCode("15");
        bean.setVersionName("1.3.004.0001");

        //设置app信息
        bean.setAppName("怪兽岛攻略");
        bean.setTopTitle("怪兽岛攻略");
        bean.setAboutTitleTxt("着迷攻略 for 怪兽岛");
        bean.setAboutCText("版本 Android - ");
        bean.setAppKey("2j1iNVKnBdNb0w4MGM6OPKA");
        //设置asset信息
        bean.setSourceAssertFolder(replacePath + "/assets");

        //设置网站的域名信息
        bean.setOnlineDomain("http://marticle.joyme.com/marticle/game/guaishoudao.html");
//        bean.setBetaDomain("http://www.joyme.alpha/");

        //设置颜色
        bean.setUpdateHomeMenuColorActive(true);
        bean.setHomeMenuColorActive("#FF6000");
        bean.setUpdateHomeMenuColorN(true);
        bean.setHomeMenuColorN("#4A1215");

        androidfactory.setAndroidParamBean(bean);
        androidfactory.execute();
    }

    public void executeIOSBuild() throws Exception {
        IOSParamBean bean = new IOSParamBean();
        bean.setSourceFileFolder("E:\\opt\\ios_builder\\source\\1.3.004");
        bean.setTargetFileFolder(IOSConfigContainer.ios_root_folder);

        bean.setSourceResFolder("E:\\opt\\ios_builder\\source\\xjy");
        bean.setTargetResFolder(IOSConfigContainer.ios_root_folder);

        bean.setTargetIconsFolder(IOSConfigContainer.getTargetIconsFolder());
        bean.setTargetImagesFolder(IOSConfigContainer.getTargetImagesFolder());
        bean.setTargetProfilesFolder(IOSConfigContainer.getTargetProfilesFolder());

        bean.setAppId("1");
        bean.setUmenKey("umeng");
        bean.setAboutTitle("abtitle");
        bean.setAppKey("appkey");
        bean.setItunesUrl("iteuns");
        bean.setOnlineDomain("http://api.joyme.com");
        bean.setPackageReplaceStr("xjy");
        bean.setWebViewHost("http://marticle.joyme.com");
        bean.setAboutTitle("着迷攻略 for 梦想海贼王");

        IOSfactory.setIOSParamBean(bean);
        IOSfactory.execute();
    }

    public static void main(String[] args) throws Exception {
//>>>> ORIGINAL //online/toolsplatform/main/webapps/autobuilder/src/com/enjoyf/autobuilder/service/BuildService.java#11
//            BuildService service = new BuildService();
//==== THEIRS //online/toolsplatform/main/webapps/autobuilder/src/com/enjoyf/autobuilder/service/BuildService.java#14
//        IOSConfigContainer.ios_root_folder = "E:\\opt\\ios_builder\\template";
//
//        BuildService service = new BuildService();
////            service.replacePath = "E:/1";
////            AndroidConfigContainer.rootFolder = "E:/opt/android_builder/bobzhai-desktop-android-framwork";
//==== YOURS //liangtang-laptop-joyme-toolsplatform-main/webapps/autobuilder/src/com/enjoyf/autobuilder/service/BuildService.java
//            BuildService service = new BuildService();
//>>>> ORIGINAL //online/toolsplatform/main/webapps/autobuilder/src/com/enjoyf/autobuilder/service/BuildService.java#10
//            service.replacePath = "D:/replaceRes/android";
//            AndroidConfigContainer.rootFolder = "D:/Android_Tools/bobzhai-desktop-android-macn";
//<<<<
////            service.executeAndroidBuild();
//>>>> ORIGINAL //online/toolsplatform/main/webapps/autobuilder/src/com/enjoyf/autobuilder/service/BuildService.java#11
//            service.replacePath = "E:/1";
//            AndroidConfigContainer.rootFolder = "E:/opt/android_builder/bobzhai-desktop-android-framwork";
//            service.executeAndroidBuild();
//           // service.executeIOSBuild();
//        }
//==== THEIRS //online/toolsplatform/main/webapps/autobuilder/src/com/enjoyf/autobuilder/service/BuildService.java#14
//        service.executeIOSBuild();
//    }
//==== YOURS //liangtang-laptop-joyme-toolsplatform-main/webapps/autobuilder/src/com/enjoyf/autobuilder/service/BuildService.java
//            service.executeIOSBuild();
//==== THEIRS //online/toolsplatform/main/webapps/autobuilder/src/com/enjoyf/autobuilder/service/BuildService.java#11
//            service.replacePath = "E:/1";
//            AndroidConfigContainer.rootFolder = "E:/opt/android_builder/bobzhai-desktop-android-framwork";
//==== YOURS //liangtang-laptop-joyme-toolsplatform-main/webapps/autobuilder/src/com/enjoyf/autobuilder/service/BuildService.java
//            service.replacePath = "D:/replaceRes/android";
//            AndroidConfigContainer.rootFolder = "D:/Android_Tools/bobzhai-desktop-android-macn";
//<<<<
//            service.executeAndroidBuild();
//>>>> ORIGINAL //online/toolsplatform/main/webapps/autobuilder/src/com/enjoyf/autobuilder/service/BuildService.java#10
//==== THEIRS //online/toolsplatform/main/webapps/autobuilder/src/com/enjoyf/autobuilder/service/BuildService.java#11
//           // service.executeIOSBuild();
//==== YOURS //liangtang-laptop-joyme-toolsplatform-main/webapps/autobuilder/src/com/enjoyf/autobuilder/service/BuildService.java
////            service.executeIOSBuild();
//<<<<
        }
//<<<<
}
