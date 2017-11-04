package com.mobcent.codetool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.mobcent.codetool.Constants;

public class OpenConnection {
    private static Properties prop = new Properties();

    public static String getDBName(){
        return prop.getProperty("DB");
    }

    public static void loadProperties() throws FileNotFoundException, IOException {
        File file = new File("./src/db.properties");
        if(file.exists()){
            prop.load(new FileInputStream(file));
        }else{
            prop.load(Constants.class.getResourceAsStream("../../../db.properties"));
        }
    }

    public static Connection openMySqlConnection() throws FileNotFoundException, IOException {
        if(prop.isEmpty())
            loadProperties();
        
        String CLASSFORNAME = prop.getProperty("CLASSFORNAME");
        String DB = prop.getProperty("DB");
        String HOST = prop.getProperty("HOST");
        String USER = prop.getProperty("USER");
        String PWD = prop.getProperty("PWD");
        String LINKURL = "jdbc:mysql://" + HOST + ":3306/" + DB;
        try {
            Class.forName(CLASSFORNAME);
            Connection conn = DriverManager.getConnection(LINKURL, USER, PWD);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) throws FileNotFoundException, IOException {
//        OpenConnection conn = new OpenConnection();
//    }
}
