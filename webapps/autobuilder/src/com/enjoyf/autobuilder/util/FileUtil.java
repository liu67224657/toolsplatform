package com.enjoyf.autobuilder.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUtil {
    public static String SYSTEM_FILE_SEPARATOR = System.getProperty("file.separator");

    public static void writeFile(String path, String html) throws IOException {
        File file = new File(path);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
        OutputStreamWriter write = new OutputStreamWriter(fileOutputStream, "UTF-8");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new BufferedWriter(write));
            writer.write(html);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    public static boolean isFileOrDirExist(String fileOrDirName) {
        boolean returnValue = false;

        File fileOrDir = new File(fileOrDirName);
        if (fileOrDir.exists()) {
            returnValue = true;
        }

        fileOrDir = null;

        return returnValue;
    }

    public static boolean createDirectory(String directoryName) throws FileNotFoundException {
        return createDirectory(directoryName, SYSTEM_FILE_SEPARATOR, false);
    }

    public static boolean createDirectory(String directoryName, String dirSeparator, boolean recursion) throws FileNotFoundException {
        File direct = null;

        if (recursion) {
            if (dirSeparator == null || "".equals(dirSeparator)) {
                dirSeparator = SYSTEM_FILE_SEPARATOR;
            }

            String[] tempDirs = StringUtil.replace(directoryName, dirSeparator, "/", 1).split("/");
            String curDirName = "";

            if (tempDirs == null || tempDirs.length < 1) {
                throw new FileNotFoundException("File or direction name is incorrect.");
            }

            for (int i = 0; i < tempDirs.length; i++) {
                curDirName += tempDirs[i] + dirSeparator;
                direct = new File(curDirName);

                if (direct.exists()) {
                    continue;
                }

                if (!direct.mkdir()) {
                    return false;
                }
            }

            return true;
        } else {
            direct = new File(directoryName);

            if (direct.exists()) {
                return true;
            }

            return direct.mkdirs();
        }
    }

    public static String readFile(String filePath, String encoding) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStream in = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        try {
            in = new FileInputStream(new File(filePath));
            isr = new InputStreamReader(in, encoding);
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            return sb.toString();
        } finally {
            if (in != null) {
                in.close();
                in = null;
            }
            if (isr != null) {
                isr.close();
                isr = null;
            }
            if (br != null) {
                br.close();
                br = null;
            }
        }
    }

    public static HttpServletResponse download(String path, HttpServletResponse response) {
        OutputStream toClient = null;
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                toClient.flush();
                toClient.close();
            } catch (IOException e) {
            }
        }
        return response;
    }
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            System.out.print("tempList=" + tempList[i]);
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            temp.delete();
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);
                delFolder(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }
}
