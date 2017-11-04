package com.mobcent.codetool.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.util.NameUtil;

public abstract class GenerateCreateFile {
    /**
     * extend��implements�ı�־
     */
    protected String extendsFlag = "";
    protected String className = "";
    protected String classNameflag = "";
    protected boolean isNeedAddClassName = true;
    protected boolean isClass = true;

    protected static String BASEPATH = "";
    private static final String SUFFIX = ".java";

    public GenerateCreateFile() {
        String classPath = this.getClass().getClassLoader().getResource("/").toString();
        if (classPath.startsWith("file:/")) {
            int index = classPath.indexOf("/");
            classPath = classPath.substring(index);
        }
        for (int i = 0; i < 3; i++) {
            int index = classPath.lastIndexOf("/");
            if (index != -1) {
                classPath = classPath.substring(0, index);
            }
        }
        BASEPATH = classPath;
    }

    protected String addClassName(String methodContent) {
        String classContent = "public "+ (isClass ? "class" : "interface")+" " + className + classNameflag + " " + extendsFlag + "{" + Constants.NEXT_LINE + Constants.NEXT_LINE;
        String propertiesString = this.getPropertiesString(className);
        classContent += propertiesString;
        classContent += methodContent + "}";
        return classContent;
    }


    /**
     * ����properties����
     * @return
     */
    protected String getPropertiesString(String className) {
        return "";
    }

    public String makeJavaFile(String table, String whereClause) throws Exception {
        className = NameUtil.changeToJavaName(table, true);
        this.setClassNameflag();
        this.setExtendsFlag();
        this.isClass();
        String fileName = createFileName(className + classNameflag);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/HH/mm/ss/");
        String path = sdf.format(date);
        BASEPATH=URLDecoder.decode(BASEPATH, "utf-8");
        File f = new File(BASEPATH + path);
        boolean flag = true;
        if (!f.exists()) {
            if (!f.mkdirs()) {
                flag = false;
            }
        }
        
        
        String file = path + fileName + SUFFIX;
        System.out.println("=========file==========");
        System.out.println(file);
        f = new File(BASEPATH + file);
        System.out.println("=========path==========");
        System.out.println(BASEPATH + file);
        if (!f.exists()) {
            if (!f.createNewFile()) {
                flag = false;
            }
        }
        if (!flag) {
            throw new Exception();
        }
        
        
        OutputStream writer = null;
        try {
            writer = new FileOutputStream(f);

            String methodContent = doAction(table, whereClause);

            if (isNeedAddClassName)
                methodContent = this.addClassName(methodContent);

            writer.write(methodContent.getBytes());
        } finally {
            writer.flush();
            writer.close();
        }
       
        if (file.length() > 1) {
            file = file.substring(1);
        }
        return file;

    }

    public String createFileName(String table) throws Exception {
        return NameUtil.changeToJavaName(table, true);
    }

    public abstract String doAction(String table, String whereClause) throws Exception;

    public abstract void setExtendsFlag();

    public abstract void setClassNameflag();

    public void setClass(boolean isClass) {
        this.isClass = isClass;
    }

    public boolean isClass() {
        return isClass;
    }

}
