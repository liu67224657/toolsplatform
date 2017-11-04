package com.enjoyf.wiki;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.util.HttpClientManager;
import com.enjoyf.util.HttpParameter;
import com.enjoyf.wiki.dao.WikiPageDao;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-3-3 上午11:20
 * Description:
 */
public class Main {
    private static WikiPageDao subDao = new WikiPageDao();

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Connection conn = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        conn = DriverManager.getConnection("jdbc:mysql://db001.dev:3306/wikiurl", "root", "654321");

        conn.setAutoCommit(false);

        DataBean dbean = null;
        HttpClientManager manager = new HttpClientManager();
        try {
            String sql = "SELECT * FROM wiki_page WHERE page_status=1 and page_id >80608";
            List objectList = new ArrayList();
            dbean = subDao.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                HttpParameter[] saveParams = new HttpParameter[]{
                        new HttpParameter("c", "wiki-page"),
                        new HttpParameter("field", "id=" + rs.getLong("page_id") + ",wikikey=" + rs.getString("wiki_key") + ",title=" + rs.getString("wiki_url")),
                };
                manager.post("http://web001.dev:38080/search/save.do", saveParams, null);
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            subDao.cleanup(dbean, false);
        }

//            System.out.println(new WikiPageService().getWikiPageIdByChineseURL(conn, "ma", "中文测试"));

    }


    public static String generatorXml() throws IOException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");


        //  root.addNamespace("", "http://www.sitemaps.org/schemas/sitemap/0.9");//加入一行注释
//        Namespace m_ns = new Namespace("s", "http://www.sitemaps.org/schemas/sitemap/0.9");
//        root.add(m_ns);
        // root.addAttribute("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");

        Element urlElement = root.addElement("url"); //添加root的子节点
        Element locElement = urlElement.addElement("loc");
        locElement.addText("http://yzqx.joyme.com");
        Element priorityElement = urlElement.addElement("priority");
        priorityElement.addText("1.0");

        //输出全部原始数据，在编译器中显示
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");//根据需要设置编码
        XMLWriter writer = new XMLWriter(System.out, format);
        document.normalize();
        writer.write(document);
        writer.close();

        // 输出全部原始数据，并用它生成新的我们需要的XML文件
        XMLWriter writer2 = new XMLWriter(new FileWriter(new File(
                "C:\\Users\\zhimingli\\Desktop\\test.xml")), format);
        writer2.write(document); //输出到文件
        writer2.close();

        // 输出全部原始数据，并用它生成新的我们需要的XML文件
        XMLWriter writer3 = new XMLWriter(new FileWriter(new File(
                "C:\\Users\\zhimingli\\Desktop\\test.txt")), format);
        writer3.write(document); //输出到文件
        writer3.close();

        return "";
    }
}
