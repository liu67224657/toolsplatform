import com.enjoyf.search.exception.JoymeSearchException;
import com.enjoyf.util.DateUtil;
import com.enjoyf.util.StringUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ericliu on 16/5/19.
 */
public class Main {

    private static Map<Integer, String> ruleMap = new HashMap<Integer, String>();


    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, SolrServerException, JoymeSearchException, IOException {

        long now = System.currentTimeMillis();

        loadUrlRule();

        int aId = 0;
        List<Article> articleList = null;
        do {
            articleList = queryData(aId);
            if (articleList != null) {
                aId = articleList.get(articleList.size() - 1).getId();
            }
            for (Article article : articleList) {
//                System.out.println(article);
                save(article);
//                break;
            }
//            break;
        } while (articleList != null && articleList.size() == 300);
        System.out.println("===index finish spent:==" + ((System.currentTimeMillis() - now) / 1000l));
    }


    private static List<Article> queryData(int lastId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://172.16.75.68:3306/test1?characterEncoding=UTF-8&useUnicode=true";
        List<Article> articles;
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection(url, "root", "654321");
            pstmt = conn.prepareStatement("SELECT id,title,description,typeid,senddate from dede_archives where id>? limit 0,300");
            pstmt.setInt(1, lastId);
            rs = pstmt.executeQuery();

            articles = new ArrayList<Article>();
            while (rs.next()) {
                Article article = new Article(rs.getInt("id"), rs.getString("title"), rs.getString("description"), new Date(rs.getLong("sendDate") * 1000l), rs.getInt("typeid"));

                articles.add(article);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return articles;
    }


    private static void loadUrlRule() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://172.16.75.68:3306/test1?characterEncoding=UTF-8&useUnicode=true";
        String returnUrl = "";
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection(url, "root", "654321");
            pstmt = conn.prepareStatement("SELECT id,typedir,namerule from dede_arctype");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ruleMap.put(rs.getInt("id"), rs.getString("namerule") + " " + rs.getString("typedir"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

    }

    private static String getUrl(int aid, Date sendDate, int arctypeId) {
        String returnUrl = "";
        String ruleString = ruleMap.get(arctypeId);
        if(ruleString==null){
            System.out.println("error reuleString is null aid="+aid+" arctypeId="+arctypeId);
            return "";
        }
        String[] ruleStringArray = ruleString.split("\\s");
        returnUrl = replaceUrl(ruleStringArray[0], ruleStringArray[1], aid, sendDate);
        return returnUrl;
    }

    private static String replaceUrl(String namerule, String typeDir, int aid, Date senDate) {
        //{typedir}/{Y}{M}/{D}{aid}.html
        String url = namerule.replace("{typedir}", typeDir).replace("{cmspath}", "")
                .replace("{Y}", DateUtil.formatDateToString(senDate, "yyyy"))
                .replace("{M}", DateUtil.formatDateToString(senDate, "MM"))
                .replace("{D}", DateUtil.formatDateToString(senDate, "dd"))
                .replace("{aid}", String.valueOf(aid));

        return url;
    }


    public static void save(Article article) throws SolrServerException, IOException, JoymeSearchException {
        String articleUrl=getUrl(article.getId(), article.getSendDate(), article.getTypeid());
        if(StringUtil.isEmpty(articleUrl)){
            return;
        }

        HttpSolrServer server = null;
        try {
            String url = "http://127.0.0.1:38000" + "/article/";
            server = new HttpSolrServer(url);
            server.setSoTimeout(3000); // socket read timeout
            server.setConnectionTimeout(1000);
            server.setDefaultMaxConnectionsPerHost(1000);
            server.setMaxTotalConnections(10);
            server.setFollowRedirects(false); // defaults to false
            server.setAllowCompression(true);
            server.setMaxRetries(1);

            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", article.getId());
            doc.addField("title", article.getTitle());
            doc.addField("content", article.getDescription());
            doc.addField("type", 1);
            doc.addField("url", articleUrl);
            doc.addField("key", "");
            server.add(doc);
            server.commit();
        } finally {
            if (server != null) {
                server.shutdown();
            }
        }
    }
}

class Article {
    private int id;
    private String title;
    private String description;
    private Date sendDate;
    private int typeid;

    public Article(int id, String title, String description, Date sendDate, int typeid) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.sendDate = sendDate;
        this.typeid = typeid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}