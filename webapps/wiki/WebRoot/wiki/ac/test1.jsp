<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title></head>
<body>
<%
    File source = new File("/opt/zxl.amr");

    long now = System.currentTimeMillis();
    for (int i = 0; i < 100; i++) {
        Runtime runtime = Runtime.getRuntime();
        final Process transferProcess = runtime.exec("ffmpeg -i " + " /opt/zxl.amr" + " /opt/test/" + i + ".mp3");

//        Thread ffmpegKiller = new Thread() {
//            @Override
//            public void run() {
//                transferProcess.destroy();
//            }
//        };
//
//        runtime.addShutdownHook(ffmpegKiller);
//
//        if (ffmpegKiller != null) {
//            Runtime killerRunTime = Runtime.getRuntime();
//            killerRunTime.removeShutdownHook(ffmpegKiller);
//            ffmpegKiller = null;
//        }
    }

    System.out.println("spent:" + (System.currentTimeMillis() - now));
%>

</body>
</html>