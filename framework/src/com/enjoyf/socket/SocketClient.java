package com.enjoyf.socket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.SystemUtil;

public class SocketClient extends Thread {
    public static Properties sp = getSocketProperties();

    //	private static String socketServerIP = "50.18.159.95";
    private static String socketServerIP = sp.getProperty("SOCKET_SERVER");
    //private static String socketServerIP = "127.0.0.1";
    private static int socketServerPort = Integer.parseInt(sp.getProperty("SOCKET_PORT"));
    private static int socketTimeOut = Integer.parseInt(sp.getProperty("SOCKET_TIMEOUT"));
    private static boolean isDebug = Boolean.parseBoolean(sp.getProperty("IS_DEBUG"));

    private String line = null;

    private String str = null;

    public SocketClient(String str) {
        this.str = str;
    }

    Socket socket;
    OutputStream o = null;

    public void sendToServer() throws Exception {
        long start = 0L;
        if (isDebug)
            start = System.currentTimeMillis();

        socket = new Socket(socketServerIP, socketServerPort);
        socket.setSoTimeout(socketTimeOut);
        InputStream in = null;
        try {
            o = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(o, true);
            pw.println(str);

            //收到返回的信息
            in = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            line = br.readLine();
            doPost(line);

        } finally {
            if(in != null){
                in.close();
            }
            if (o != null) {
                o.close();
            }
            if (socket != null) {
                socket.close();
            }
        }

        if (isDebug) {
            long end = System.currentTimeMillis();
            System.out.println("cost time is :" + (end - start));
        }
    }

    public void run() {
        super.run();
        try {
            this.sendToServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(String line) {

    }

    public String getContent() {
        return line;
    }

    public static Properties getSocketProperties() {
        Properties sp = new Properties();
        SystemUtil su = new SystemUtil();
        String file = su.getMetaInfFolderPath() + "/socket.properties";
        try {
            sp.load(new FileInputStream(new File(file)));
            return sp;
        } catch (FileNotFoundException e) {
            LogService.errorSystemLog("Error when getSocketProperties", e);
        } catch (IOException e) {
            LogService.errorSystemLog("Error when getSocketProperties", e);
        }
        return null;
    }
}
