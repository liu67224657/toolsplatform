package com.enjoyf.autobuilder.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
   
/**   
 * <p>Title: 处理文件上传下载的类</p>   
 * <p>Description:    
 *    通过设置long MAX_SIZE可以设置上传文件的大小限制   
 *    通过设置String[] allowedExt设置允许上传的文件类型   
 *    通过Map parameters获得表单域的信息   
 *    通过List fileInfoList获取上传的每个文件的详细信息   
 * </p>   
 * <p>Copyright: Copyright (c) 2006, 2008 Royzhou Corporation.All rights reserved. </p>   
 * @author royzhou   
 * 2009-02-20   
 */    
public class FileWebService {     
    /**   
     * 表单域的信息   
     */    
    private Map parameters = null;     
         
    /**   
     * 文件域的详细信息   
     */    
    private List fileInfoList = null;     
         
    /**   
     * 允许上传的文件大小   
     */    
    private long MAX_SIZE = 100*1024*1024;     
         
    /**   
     * 允许上传的文件类型   
     */    
    private String[] allowedExt = new String[] { "zip" };     
         
    public FileWebService() {     
        parameters = new HashMap();     
        fileInfoList = new ArrayList();     
    }     
         
    /**   
     * @param request   
     * @param response   
     * @param path 用户设置的保存路径   
     * 上传文件并获取表单域及文件域的详细信息   
     * @throws Exception   
     */    
    public void upload(HttpServletRequest request, HttpServletResponse response, String path) throws Exception {     
        /**   
         * 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload   
         */    
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();     
        diskFileItemFactory.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘     
        /**   
         * 采用系统临时文件目录作为上传的临时目录   
         */    
        File tempfile = new File(System.getProperty("java.io.tmpdir"));       
        diskFileItemFactory.setRepository(tempfile);     
             
        /**   
         * 用以上工厂实例化上传组件   
         * 设置最大上传尺寸   
         */    
        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);     
        fileUpload.setSizeMax(MAX_SIZE);     
             
        /**   
         * 调用FileUpload.settingHeaderEncoding(”UTF-8″)，这项设置可以解决路径或者文件名为乱码的问题。   
         * 设置输出字符集   
         */    
        fileUpload.setHeaderEncoding("UTF-8");     
        response.setContentType("text/html;charset=utf-8");     
             
        PrintWriter out = response.getWriter();     
        /**   
         * 从request得到 所有 上传域的列表   
         */    
        List fileList = null;     
        try {     
            fileList = fileUpload.parseRequest(request);     
        } catch (FileUploadException e) {     
            if (e instanceof SizeLimitExceededException) {     
                /**   
                 * 文件大小超出限制   
                 */    
                out.println("文件尺寸超过规定大小:" + MAX_SIZE + "字节<p />");     
                out.println("<a href=\"upload.html\" target=\"_top\">返回</a>");     
                return;     
            }     
            e.printStackTrace();     
        }     
        /**   
         * 没有上传文件   
         */    
        if (fileList == null || fileList.size() == 0) {     
            out.println("请选择上传文件<p />");     
            out.println("<a href=\"upload.html\" target=\"_top\">返回</a>");     
            return;     
        }     
        /**   
         * 得到所有上传的文件   
         * 对文件域操作   
         * 并保存每个文件的详细信息   
         */    
        Iterator fileItr = fileList.iterator();     
        Map fileInfo = null;     
        while (fileItr.hasNext()) {     
            FileItem fileItem = null;     
            long size = 0;     
            String userPath = null;     
            String serverPath = null;     
            String fileName = null;     
            String fileExt = null;     
            fileItem = (FileItem) fileItr.next();     
            /**   
             * 忽略简单form字段而不是上传域的文件域(<input type="text" />等)   
             */    
            if (!fileItem.isFormField()) {     
                 
                /**   
                 * 得到文件的详细信息   
                 * 客户端完整路径：userPath   
                 * 服务器端完整路径：serverPath   
                 * 大小：size   
                 * 文件名：fileName   
                 * 扩展名：fileExt   
                 *    
                 */    
                userPath = fileItem.getName();     
                size = fileItem.getSize();     
                if ("".equals(userPath) || size == 0) {     
                    out.println("请选择上传文件<p />");     
                    out.println("<a href=\"upload.html\" target=\"_top\">返回</a>");     
                    return;     
                }     
                fileName = userPath.substring(userPath.lastIndexOf("\\") + 1);     
                fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);     
    
                /**   
                 * 文件类型是否合法   
                 */    
                int allowFlag = 0;     
                int allowedExtCount = allowedExt.length;     
                for (; allowFlag < allowedExtCount; allowFlag++) {     
                    if (allowedExt[allowFlag].toLowerCase().equals(fileExt.toLowerCase()))     
                        break;     
                }     
                if (allowFlag == allowedExtCount) {     
                    out.println("请上传以下类型的文件<p />");     
                    for (allowFlag = 0; allowFlag < allowedExtCount; allowFlag++)     
                        out.println("*." + allowedExt[allowFlag].toLowerCase()     
                                + "   ");     
                    out     
                            .println("<p /><a href=\"upload.html\" target=\"_top\">返回</a>");     
                    return;     
                }     
                /**   
                 * 根据系统时间生成上传后保存的文件名   
                 */    
                serverPath = path + System.currentTimeMillis() + "." + fileExt;     
                     
                try {     
                    /**   
                     * 保存文件   
                     */    
                    File diskPath = new File(path);     
                    if(!diskPath.exists()) {     
                        diskPath.mkdirs();     
                    }     
                    File diskFile = new File(serverPath);     
                    if(!diskFile.exists()) {     
                        diskFile.createNewFile();     
                    }     
                    fileItem.write(diskFile);     
                    out.println("文件上传成功. 已保存为: " + serverPath + "   文件大小: " + size + "字节<p />");     
                    out.println("<form action=\"FileDownloadServlet\" method=\"post\">");     
                    out.println("  <input type=\"hidden\" name=\"fileName\" value=\"" + fileName + "\" />");     
                    out.println("  <input type=\"hidden\" size=\"200\" name=\"filePath\" value=\"" + serverPath + "\" />");     
                    out.println("  <input type=\"submit\" name=\"submit\" value=\"下载上传的文件\" />");     
                    out.println("</form>");     
                } catch (Exception e) {     
                    e.printStackTrace();     
                }     
                     
                fileInfo = new HashMap();     
                fileInfo.put("size", String.valueOf(size));     
                fileInfo.put("userpath", userPath);     
                fileInfo.put("name",fileName);     
                fileInfo.put("ext", fileExt);     
                fileInfo.put("serverpath", serverPath);     
                fileInfoList.add(fileInfo);     
            } else {     
                String fieldName = fileItem.getFieldName();     
                /**   
                 * 在取字段值的时候，用FileItem.getString(”UTF-8″)，这项设置可以解决获取的表单字段为乱码的问题。   
                 */      
                String value = fileItem.getString("UTF-8");     
                parameters.put(fieldName, value);     
            }     
        }     
    } 
}
