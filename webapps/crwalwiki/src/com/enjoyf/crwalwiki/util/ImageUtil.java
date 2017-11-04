package com.enjoyf.crwalwiki.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUtil {

    /**
     * @param
     * @throws Exception
     */
    public boolean saveImgFromUrl(String link, String savePath) throws Exception {
        // newһ��URL����
        URL url = new URL(link);
        // ������
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream is = null;
        FileOutputStream os = null;
        try {
            // ��������ʽΪ"GET"
            conn.setRequestMethod("GET");
            // ��ʱ��Ӧʱ��Ϊ5��
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ͨ����������ȡͼƬ���
            is = conn.getInputStream();
            // �õ�ͼƬ�Ķ�������ݣ��Զ����Ʒ�װ�õ���ݣ�����ͨ����
            byte[] data = readInputStream(is);
            // newһ���ļ�������������ͼƬ��Ĭ�ϱ��浱ǰ���̸�Ŀ¼
            File imageFile = new File(savePath);
            // ���������
            os = new FileOutputStream(imageFile);

            // д�����
            os.write(data);

            return true;
        } catch (Exception e) {
            System.out.println(this.getClass().getName() + "saveImgFromUrl error image link: " + link);
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
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

    public static byte[] readInputStream(InputStream is) throws Exception {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            // ����һ��Buffer�ַ�
            byte[] buffer = new byte[2048];
            // ÿ�ζ�ȡ���ַ��ȣ����Ϊ-1�����ȫ����ȡ���
            int len = 0;
            // ʹ��һ����������buffer�����ݶ�ȡ����
            while ((len = is.read(buffer)) != -1) {
                // ���������buffer��д����ݣ��м��������ĸ�λ�ÿ�ʼ����len����ȡ�ĳ���
                outStream.write(buffer, 0, len);
            }

            // ��outStream������д���ڴ�
            return outStream.toByteArray();
        } finally {
            // �ر�������
            if (is != null) {
                is.close();
                is = null;
            }
        }
    }

}
