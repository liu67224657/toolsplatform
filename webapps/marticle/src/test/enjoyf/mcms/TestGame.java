package test.enjoyf.mcms;

import java.sql.Connection;

import org.junit.Test;

import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.facade.ArchiveFacade;

public class TestGame extends Base {
    private static ArchiveFacade facade = new ArchiveFacade();

    protected void setUp() throws Exception {
        this.init();
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        if (conn != null) {
            conn.close();
        }
        super.tearDown();
    }

    // @Test
    // public void testzhiwudazhangjiangshi2() throws Exception{
    // Connection conn = this.getConnection();
    // String htmlFile = "zhiwudazhanjiangshi2";
    // String localPath = "http://localhost:8080/marticle";
    // List joymePointList = joymePointService.queryJoymePointByHtmlFile(conn,
    // htmlFile);
    // JoymeSpec spec = joymeSpecService.queryJoymeSpecByFilePath(conn,
    // htmlFile);
    //
    // int seqId = dedeArchivesService.getMaxSeqId(conn, htmlFile);
    //
    // List list = new ArrayList();
    //
    // // 封装专区的文章列表，由多个节点组成
    // for (Iterator iterator = joymePointList.iterator(); iterator.hasNext();)
    // {
    // JoymePoint point = (JoymePoint) iterator.next();
    // long pointId = point.getSpecPointId();
    // int rownum = point.getDisplayRowNum();
    //
    // List archiveList =
    // joymePointArchiveService.queryJoymePointArchiveByPointId(conn, pointId,
    // 0, rownum, seqId);
    // if(pointId == 261)
    // Assert.assertEquals(archiveList.size(), 10);
    // if(pointId == 158)
    // Assert.assertEquals(archiveList.size(), 6);
    //
    //
    // PointArchiveListBean bean = new PointArchiveListBean();
    // bean.setArchiveList(archiveList);
    // bean.setPoint(point);
    // list.add(bean);
    // }
    //
    // // 获得专区的渠道下载地址和广告语
    // String html = "";
    // String[] channels = new String[]{"default"};
    // for (int i = 0; i < channels.length; i++) {
    // IHtmlParseFactory factory = new DefaultParseFactoryImpl();
    // JoymeChannelContent content = spec == null ? null :
    // joymeChannelContentService.queryJoymeChannelContentbySpecChannel(conn,
    // spec.getSpecId(), channels[i]);
    //
    // // 生成html
    // // html = factory.makeSpecHtml(spec, list, content, channels[i],
    // localPath);
    // }
    //
    // System.out.println(html);
    // }
    @Test
    public void testGenerateSpecHtml() throws Exception {
        String htmlFile = "zhiwudazhanjiangshi2";
        String[] channels = new String[] { "json" };
        String localPath = "http://localhost:8080/marticle";
        String html = facade.generateSpecHtml(conn, htmlFile, channels, localPath);
        System.out.println(html);
    }

    public void testCache() throws Exception {
        System.out.println(ConfigContainer.getCacheFolder());
    }
}
