package test.enjoyf.mcms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.service.TemplateService;

import junit.framework.TestCase;

public class Base extends TestCase{
    protected Connection conn = null;
    private static TemplateService templateService = new TemplateService();
    public Connection getConnection() throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        // URL指向要访问的数据库名scutcs
        String url = "jdbc:mysql://127.0.0.1:3306/article_cms_1";
        String user = "root";
        String password = "820222";
        // 加载驱动程序
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false);
        return conn;
    }
    
    public void closeCollection(Connection conn) throws SQLException{
        conn.close();
    }
    
    public void init() throws Exception{
        conn = this.getConnection();
        ConfigContainer.loadChannelConfig(conn);
        templateService.reloadTemplate("./WebRoot");
        ConfigContainer.loadProperties("./WebRoot/META-INF");
    }
    
    
}
