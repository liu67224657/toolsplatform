package com.enjoyf.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static boolean createDirectory(String directoryName)
            throws FileNotFoundException {
        return createDirectory(directoryName, SYSTEM_FILE_SEPARATOR, false);
    }

    public static boolean createDirectory(String directoryName,
                                          String dirSeparator, boolean recursion)
            throws FileNotFoundException {
        File direct = null;

        if (recursion) {
            if (dirSeparator == null || "".equals(dirSeparator)) {
                dirSeparator = SYSTEM_FILE_SEPARATOR;
            }

            String[] tempDirs = StringUtil.replace(directoryName, dirSeparator, "/", 1).split("/");
            String curDirName = "";

            if (tempDirs == null || tempDirs.length < 1) {
                throw new FileNotFoundException(
                        "File or direction name is incorrect.");
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

    public static boolean deleteFileOrDir(String fileOrDirName)
            throws FileNotFoundException {
        File fileOrDir = new File(fileOrDirName);

        if (fileOrDir.exists()) {
            return fileOrDir.delete();
        } else {
            throw new FileNotFoundException("The file or direction:"
                    + fileOrDirName + " is not exist.");
        }
    }

    public static String readFile(InputStream in, String encoding) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        InputStreamReader isr = null;
        try {
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


    public static void displayFile(HttpServletResponse response, File file, String contentType) throws IOException, FileNotFoundException {
        response.setCharacterEncoding("utf-8");
        response.setContentType(contentType == null ? "text/html" : contentType);
        FileInputStream is = new FileInputStream(file);

        OutputStream os = null;
        try {
            os = response.getOutputStream();
            byte[] buff = new byte[8192];
            int len = -1;
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }

        } finally {
            if (is != null) {
                is.close();
                is = null;
            }

            if (os != null) {
                os.flush();
                os.close();
                os = null;
            }
        }
    }

    public static void displayHtml(HttpServletResponse response, String html, String contentType) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType(contentType == null ? "text/html" : contentType);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(html);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }


}
