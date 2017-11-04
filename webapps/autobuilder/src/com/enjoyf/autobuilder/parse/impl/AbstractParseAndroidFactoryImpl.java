package com.enjoyf.autobuilder.parse.impl;

import com.enjoyf.autobuilder.bean.AndroidParamBean;
import com.enjoyf.autobuilder.bean.BatchAndroidParamBean;
import com.enjoyf.autobuilder.bean.BatchIOSParamBean;
import com.enjoyf.autobuilder.bean.IOSParamBean;
import com.enjoyf.autobuilder.config.AndroidConfigContainer;
import com.enjoyf.autobuilder.parse.IParseFactory;
import com.enjoyf.util.FileUtil;
import com.enjoyf.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractParseAndroidFactoryImpl implements IParseFactory {
    private final static String CHARSET = "utf-8";
    private static String PACKAGE_NAME_SUFFIX = "com.joyme.app.android.";
    private Map homeVPMap = new HashMap();
    protected AndroidParamBean bean = null;

    @Override
    public void setAndroidParamBean(AndroidParamBean bean) {
        this.bean = bean;
    }

    @Override
    public void setIOSParamBean(IOSParamBean bean) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setBatchAndroidParamBean(BatchAndroidParamBean bean) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setBatchIosParamBean(BatchIOSParamBean bean) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean execute() throws Exception {
        // 清空gen
        clearGen();
        // copy源文件
        copySourceFolder();
        // 替换包名
        replaceSourcePackage();
        // copy res
        copyRes();
        // 替换AndroidManifestFile
        replaceAndroidManifestFile();
        // 修改layout
        replaceLayout();
        // 修改Strings
        replaceStrings();
        // 替换asset
        replaceAsset_();
        // replaceAsset();
        // 替换WelcomeActivity.java
        // replaceWelcomeActivity();
        // replace color
        replaceColor();
        replaceLocalProps();

        replaceAppNameFlag();
        setBuildHost();
        return true;
    }

    protected void setBuildHost() throws IOException {
        String host = bean.getHost();
        if (("api").equals(host))
            return;
        String constantsFilePath = AndroidConfigContainer.getBuildFolder() + "/" + bean.getPackageReplaceStr() + "/utils/UrlHelp.java";
        File constantsFile = new File(constantsFilePath);
        FileInputStream fileInputStream = new FileInputStream(constantsFile);
        String data = FileUtil.readFile(fileInputStream, "UTF-8");
        if ("alpha".equals(host))
            data = data.replace("api.joyme.com", "com.joyme.alpha");
        //data =  data.replace();
        FileUtil.writeFile(constantsFilePath, data);

    }


    protected void replaceAppNameFlag() throws IOException {
        String constantsFilePath = AndroidConfigContainer.getBuildFolder() + "/" + bean.getPackageReplaceStr() + "/utils/Constants.java";
        File constantsFile = new File(constantsFilePath);
        FileInputStream fileInputStream = new FileInputStream(constantsFile);
        String data = FileUtil.readFile(fileInputStream, "UTF-8");
        data = data.replace("\"_APP_NAME_FLAG\"", "\"" + bean.getAppNameFlag() + "\"").
                replace("shareappname_", bean.getAppName()).
                replace("appshortname_", bean.getAppShortName()).
                replace("IMAGE_URL_HOST_", bean.getImage_url_host());
        //data =  data.replace();
        FileUtil.writeFile(constantsFilePath, data);
    }

    protected void replaceUrl() throws IOException {
        String constantsFilePath = AndroidConfigContainer.getBuildFolder() + "/" + bean.getPackageReplaceStr() + "/util/UrlHelp.java";
        File constantsFile = new File(constantsFilePath);
        List list = FileUtils.readLines(constantsFile, CHARSET);
    }

    protected void replaceLocalProps() throws IOException {
        String localPropsFilePath = AndroidConfigContainer.rootFolder + "/local.properties";
        File file = new File(localPropsFilePath);
        List list = FileUtils.readLines(file, CHARSET);

        List tempList = new ArrayList();
        for (Object object : list) {
            String valueStr = object.toString();
            if (valueStr.trim().startsWith("app.name")) {
                valueStr = "app.name=" + bean.getPackageReplaceStr().toUpperCase() + "_gameguide";

            }
            tempList.add(valueStr);
        }
        FileUtils.writeLines(file, CHARSET, tempList, "\r\n");
    }

    protected void clearGen() throws IOException {
        String colorFilePath = AndroidConfigContainer.rootFolder + "/gen";
        File file = new File(colorFilePath);
        FileUtils.deleteDirectory(new File(colorFilePath));
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    protected void replaceColor() throws Exception {
        String colorFilePath = AndroidConfigContainer.rootFolder + "/res/values/colors.xml";
        SAXReader saxReader = new SAXReader();
        File file = new File(colorFilePath);
        Document doc = saxReader.read(file);
        if (bean.isUpdateHomeMenuColorActive()) {
            List list = doc.selectNodes("//color[@name='home_menu_color_active']");
            if (list.size() > 0) {
                Element element = (Element) list.get(0);
                element.setText(bean.getHomeMenuColorActive());
            }
        }
        if (bean.isUpdateHomeMenuColorN()) {
            List list = doc.selectNodes("//color[@name='home_menu_color_n']");
            if (list.size() > 0) {
                Element element = (Element) list.get(0);
                element.setText(bean.getHomeMenuColorN());
            }
        }
        FileUtils.writeStringToFile(file, doc.asXML(), CHARSET);
    }

    protected void replaceWelcomeActivity() throws IOException {
        String constantsFilePath = AndroidConfigContainer.getBuildFolder() + "/" + bean.getPackageReplaceStr() + "/activity/WelcomeActivity.java";
        File file = new File(constantsFilePath);
        String context = FileUtils.readFileToString(file, CHARSET);
        String flag = "String[] jsonName = new String[]{";
        int position = context.indexOf(flag);
        String retString = "";
        if (position > 0) {
            String begin = context.substring(0, position + flag.length());
            int endposition = context.indexOf("}", position + flag.length());
            String end = context.substring(endposition, context.length());

            int i = 1;
            String str = "";
            while (true) {
                if (homeVPMap.get(i) == null)
                    break;

                str += "Constant.HOME_VP_" + i + ",";
                i++;
            }
            str = StringUtil.removeLastCharacter(str, ",");
            retString = begin + str + end;
            FileUtils.writeStringToFile(file, retString, CHARSET);
        }

    }


    protected void replaceAsset_() throws IOException {
        List tempContextList = new ArrayList();

        String targetAssertFolder = AndroidConfigContainer.getTargetAssetFolder();
        String sourceAssertFolder = bean.getSourceAssertFolder();
        File targetAssertFile = new File(targetAssertFolder);
        File sourceAssertFile = new File(sourceAssertFolder);
        if (targetAssertFile.exists()) {
            FileUtils.deleteDirectory(targetAssertFile);
        }
        FileUtils.copyDirectory(sourceAssertFile, targetAssertFile);

    }

    protected void replaceAsset() throws IOException {
        List tempContextList = new ArrayList();

        String targetAssertFolder = AndroidConfigContainer.getTargetAssetFolder();
        String sourceAssertFolder = bean.getSourceAssertFolder();
        File targetAssertFile = new File(targetAssertFolder);
        File sourceAssertFile = new File(sourceAssertFolder);
        if (targetAssertFile.exists()) {
            FileUtils.deleteDirectory(targetAssertFile);
        }

        homeVPMap = getHomeVPMap(sourceAssertFile);

        FileUtils.copyDirectory(new File(sourceAssertFolder), new File(targetAssertFolder));
        // 把img前面的l_去掉
        replaceImgName(targetAssertFile);

        String constantsFilePath = AndroidConfigContainer.getBuildFolder() + "/" + bean.getPackageReplaceStr() + "/util/Constant.java";

        File constantsFile = new File(constantsFilePath);
        List list = FileUtils.readLines(constantsFile, CHARSET);

        // 去掉Home vp
        List removeList = getRemoveList();
        removeHomeVpLine(list, tempContextList, removeList);

        // 去掉最后一个} ,加入HOME_VP ,然后再加入}
        updateConstantFile(homeVPMap, constantsFile, tempContextList);

        tempContextList.add("}");

        //修改调用域名
        this.changeBetaDomain(tempContextList);
        //修改BASE_DIR
        this.changeBaseDir(tempContextList);
        //修改开发 平台发送信息
        this.changeOpenPlat(tempContextList);

        this.changeCacheDir(tempContextList);

//        StringBuffer sb=new StringBuffer();
//        for(Object string:tempContextList){
//            sb.append(string).append("\r\n");
//        }
//
//        FileUtil.writeFile(constantsFile.getCanonicalPath(),sb.toString());

//        constantsFile.delete();
//        constantsFile=new File(constantsFilePath);
        FileUtils.writeLines(constantsFile, CHARSET, tempContextList, "\r\n");


    }

    private void changeCacheDir(List tempContextList) {
        changeItem("framework_cache", bean.getPackageReplaceStr() + "_cache", tempContextList);
    }

    private void changeOpenPlat(List tempContextList) {
        String appName = bean.getAppName();
        if (appName.endsWith("攻略")) {
            int position = appName.lastIndexOf("攻略");
            appName = appName.substring(0, position);
        }
        String url = "http://r001.joyme.com/r001/app/" + bean.getPackageReplaceStr().toUpperCase() + "_gameguide_joyme.apk";
        String info = "我在使用“" + appName + "宇宙最全攻略”（" + url + "）查看“（";

        changeItem("replace_here", info, tempContextList);
    }


    private void changeBaseDir(List tempContextList) {
        changeItem("JoymeGuideCache/framework", "JoymeGuideCache/" + bean.getPackageReplaceStr(), tempContextList);
    }

    private void changeBetaDomain(List tempContextList) {
        if (bean.getBetaDomain() != null && bean.getBetaDomain().trim().length() > 0) {
            changeItem("http://api.joyme.com", bean.getBetaDomain(), tempContextList);
        }
    }

    protected void changeItem(String suffix, String replace, List tempContextList) {
        List tempList = new ArrayList();
        tempList.addAll(tempContextList);
        tempContextList.clear();

        for (int i = 0; i < tempList.size(); i++) {
            String line = (String) tempList.get(i);
            line = line.replaceAll(suffix, replace);
            tempContextList.add(line);
        }

    }

    protected List getRemoveList() {
        List removeList = new ArrayList();
        removeList.add("HOME_VP");
        removeList.add("NOTIFY_N_URL");
        removeList.add("URL_HOST");
        return removeList;
    }

    private void removeHomeVpLine(List list, List tempList, List removeList) {
        for (int i = 0; i < list.size(); i++) {
            String str = (String) list.get(i);
            if (!isContainRemoveFlag(str, removeList)) {
                tempList.add(str);
            }
        }
    }

    private boolean isContainRemoveFlag(String str, List removeFlag) {
        for (int i = 0; i < removeFlag.size(); i++) {
            if (str.contains(removeFlag.get(i).toString())) {
                return true;
            }
        }
        return false;
    }

    private void updateConstantFile(Map map, File constantsFile, List tempList) throws IOException {
        if (tempList.size() > 1)
            tempList.remove(tempList.size() - 1);

        int i = 1;
        while (true) {
            if (map.get(i) == null)
                break;

            String value = map.get(i).toString();
            String line = "public static final String HOME_VP_" + i + " = \"" + value + "\";";
            i++;
            tempList.add(line);
        }

        tempList.add("public static final String URL_HOST = \"" + bean.getWikiHost() + "\";");
        tempList.add("public static final String NOTIFY_N_URL = \"" + bean.getNotifyUrl() + "\";");
    }

    private void replaceImgName(File targetAssertFile) throws IOException {
        File[] files = targetAssertFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName();
            if (name.startsWith("l_")) {
                String temp = name.replaceAll("l_", "");
                int position = temp.indexOf("_");
                if (position > 0) {
                    temp = temp.substring(position + 1, temp.length());
                }

                String tempFilePath = files[i].getParentFile().getCanonicalPath() + "/" + temp;
                files[i].renameTo(new File(tempFilePath));
            }
        }
    }

    private Map getHomeVPMap(File sourceAssertFile) {
        File[] files = sourceAssertFile.listFiles();
        Map map = new HashMap();
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            if (fileName.startsWith("l_")) {
                String temp = StringUtil.removeStartCharacter(fileName, "l_");
                int position = temp.indexOf("_");
                int key = Integer.parseInt(temp.substring(0, position));
                String value = temp.substring(position + 1, temp.length());
                map.put(key, value);
            }
        }
        return map;
    }

    protected void replaceStrings() throws Exception {
        String stringsFile = AndroidConfigContainer.getStringsFile();
        SAXReader saxReader = new SAXReader();
        File file = new File(stringsFile);
        if (file == null || !file.exists()) return;
        Document doc = saxReader.read(file);
        replaceStringsXmlValue(doc, "app_name", bean.getAppName());
        replaceStringsXmlValue(doc, "top_title", bean.getTopTitle());
        replaceStringsXmlValue(doc, "about_title_text", bean.getAboutTitleTxt());
//        int position = bean.getVersionName().lastIndexOf(".");
//        String version = bean.getVersionName().substring(0, position);
        replaceStringsXmlValue(doc, "about_c_text", bean.getAboutCText() + bean.getVersionName());
        FileUtils.writeStringToFile(file, doc.asXML(), CHARSET);
    }

    protected void replaceStringsXmlValue(Document doc, String name, String replaceName) {
        List list = doc.selectNodes("//string[@name='" + name + "']");
        if (list.size() == 1) {
            Element element = (Element) list.get(0);
            element.setText(replaceName);
        }
    }

    protected void replaceLayout() throws IOException {
        String layoutFolder = AndroidConfigContainer.getLayoutFolder();
        File file = new File(layoutFolder);
        if (file == null || !file.exists())
            return;
        File[] files = file.listFiles();

        String flag = PACKAGE_NAME_SUFFIX + bean.getPackageReplaceFlag();
        String replaceStr = PACKAGE_NAME_SUFFIX + bean.getPackageReplaceStr();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().endsWith(".xml")) {
                String context = FileUtils.readFileToString(files[i], CHARSET);
                context = context.replaceAll(flag, replaceStr);
                FileUtils.writeStringToFile(files[i], context, CHARSET);
            }
        }
    }

    /**
     * 修改androidManifest
     *
     * @throws Exception
     */
    protected void replaceAndroidManifestFile() throws Exception {
        String androidManifest = AndroidConfigContainer.getAndroidManifestFile();
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new File(androidManifest));
        List nodeList = doc.selectNodes("//manifest[@android:versionCode]");
        if (nodeList.size() == 1) {
            Element element = (Element) nodeList.get(0);
            // 替换version
            element.attribute("versionCode").setValue(bean.getVersionCode());
            element.attribute("versionName").setValue(bean.getVersionName());
            // 替换包名
            element.attribute("package").setValue(PACKAGE_NAME_SUFFIX + bean.getPackageReplaceStr());
        }
        // 设置appkey
        nodeList = doc.selectNodes("//meta-data[@android:name='JOYME_APPKEY']");
        if (nodeList.size() == 1) {
            Element element = (Element) nodeList.get(0);
            // 设置element
            element.attribute("value").setValue(bean.getAppKey());
        }
        nodeList = doc.selectNodes("//meta-data[@android:name='UMENG_APPKEY']");
        if (nodeList.size() == 1) {
            Element element = (Element) nodeList.get(0);
            // 设置element
            element.attribute("value").setValue(bean.getUmengKey());
        }
        String html = doc.asXML();
        html = html.replace("." + bean.getPackageReplaceFlag() + ".", "." + bean.getPackageReplaceStr() + ".");
        FileUtils.writeStringToFile(new File(androidManifest.replace(".temp", "")), html, CHARSET);
    }

    /**
     * copy res
     *
     * @throws Exception
     */
    protected void copyRes() throws Exception {
        try {
            String sourceFolder = bean.getSourceResFolder();
            String targetFolder = bean.getTargetResFolder();
            FileUtils.deleteDirectory(new File(targetFolder));
            FileUtils.copyDirectory(new File(sourceFolder), new File(targetFolder));
        } catch (Exception e) {
            throw new Exception("Can't copy res folder");
        }

    }

    /**
     * 替换src
     *
     * @throws IOException
     */
    protected void copySourceFolder() throws Exception {
        try {
            String sourceFolder = bean.getSourceFileFolder();
            String targetFolder = bean.getTargetFileFolder();
            File targetFolderFile = new File(targetFolder);
            if (targetFolderFile.exists()) {
                try {
                    FileUtils.deleteDirectory(new File(targetFolder));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            FileUtils.copyDirectory(new File(sourceFolder), new File(targetFolder));
        } catch (Exception e) {
            throw new Exception("Can't copy source folder");
        }
    }

    protected void replaceSourcePackage() throws Exception {
        try {
            String targetFolder = bean.getTargetFileFolder();
            File targetFolderFile = new File(targetFolder);
            replaceFile(targetFolderFile);

            String packageName = AndroidConfigContainer.getBuildFolder() + "/" + bean.getPackageReplaceFlag();
            String targetPackageName = AndroidConfigContainer.getBuildFolder() + "/" + bean.getPackageReplaceStr();
            File file = new File(packageName);
            if (file.exists())
                file.renameTo(new File(targetPackageName));

        } catch (Exception e) {
            throw new Exception("Can't replace source package");
        }
    }

    protected void replaceFile(File targetFolderFile) throws IOException {
        File[] files = targetFolderFile.listFiles();
        String flag = PACKAGE_NAME_SUFFIX + bean.getPackageReplaceFlag();
        String replaceStr = PACKAGE_NAME_SUFFIX + bean.getPackageReplaceStr();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory())
                replaceFile(files[i]);
            else {
                if (files[i].getName().endsWith(".java")) {
                    String context = FileUtils.readFileToString(files[i], CHARSET);
                    context = context.replaceAll(flag, replaceStr);
                    FileUtils.writeStringToFile(files[i], context, CHARSET);
                }
            }
        }
    }

}
