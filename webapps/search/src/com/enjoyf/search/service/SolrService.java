package com.enjoyf.search.service;

import com.enjoyf.config.HotdeployConfigFactory;
import com.enjoyf.search.bean.SolrCore;
import com.enjoyf.search.bean.SolrSortBean;
import com.enjoyf.search.exception.JoymeSearchException;
import com.enjoyf.search.util.ConfigContainer;
import com.enjoyf.search.util.CoreHotdeployPropsConfig;
import com.enjoyf.util.HttpClientManager;
import com.enjoyf.util.HttpParameter;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.FieldAnalysisRequest;
import org.apache.solr.client.solrj.response.AnalysisResponseBase;
import org.apache.solr.client.solrj.response.FieldAnalysisResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-10-9
 * Time: 下午4:08
 * To change this template use File | Settings | File Templates.
 */
public class SolrService {

    private static final Pattern CONDITION_PATTERN = Pattern.compile("\\(([^)]+)\\)");

    public SolrDocumentList search(String core, String queryParam, SolrSortBean sortBean, int startIndex, int pageSize) throws SolrServerException, IOException, JoymeSearchException {
        SolrCore solrCore = HotdeployConfigFactory.get().getConfig(CoreHotdeployPropsConfig.class).getSolrCoreMap().get(core);

        if (solrCore == null) {
            throw JoymeSearchException.CORE_NOT_EXISTS_EXCEPTION;
        }

        SolrDocumentList docs;
        HttpSolrServer server = null;
        try {
            String url = ConfigContainer.getSearchApiUrl() + "/" + solrCore.getCoreName() + "/";
            server = new HttpSolrServer(url);
            server.setSoTimeout(3000); // socket read timeout
            server.setConnectionTimeout(1000);
            server.setDefaultMaxConnectionsPerHost(1000);
            server.setMaxTotalConnections(10);
            server.setFollowRedirects(false); // defaults to false
            server.setAllowCompression(true);
            server.setMaxRetries(1);

            String queryStr = queryString(queryParam, server, solrCore);

            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setQuery(queryStr);
            //设置起始位置与返回结果数
            if (startIndex >= 0 && pageSize > 0) {
                solrQuery.setStart(startIndex);
                solrQuery.setRows(pageSize);
            }


            if (sortBean != null) {
                solrQuery.addSort(sortBean.getSortField(), SolrQuery.ORDER.desc);
            }


            QueryResponse qrsp = server.query(solrQuery);
            docs = qrsp.getResults();

//            if (pagination != null) {
//                pagination.setTotalRows((int) docs.getNumFound());
//            }


        } finally {
            if (server != null) {
                server.shutdown();
            }
        }
        return docs;
    }

    private String queryString(String s, HttpSolrServer server, SolrCore solrCore) throws IOException, SolrServerException, JoymeSearchException {
        Matcher matcher = CONDITION_PATTERN.matcher(s);

        String result = "";

        while (matcher.find()) {
            String select = matcher.group(1);

            String[] field2Value = select.split(":");
            if (field2Value.length == 2) {
                String[] field = field2Value[0].split(" ");

                String queryParam = "";
                String queryValue = "";
                String[] valueDomain = field2Value[1].split("_");
                int index = 0;
                if (valueDomain.length == 2) {
                    queryValue = "[" + valueDomain[0] + " TO " + valueDomain[1] + "]";
                    for (String f : field) {
                        String type = solrCore.getFieldNames().get(f);
                        if (type == null) {
                            throw JoymeSearchException.FIELD_NOT_EXISTS_EXCEPTION;
                        }

                        queryParam += f + ":" + queryValue;
                        index++;
                        if (index < field.length) {
                            queryParam += " OR ";
                        }
                    }
                } else {

                    for (String f : field) {
                        String type = solrCore.getFieldNames().get(f);
                        if (type == null) {
                            throw JoymeSearchException.FIELD_NOT_EXISTS_EXCEPTION;
                        }

                        String value = URLDecoder.decode(field2Value[1], "UTF-8");
                        if (type.equalsIgnoreCase("textIKAnalyze")) {
                            queryValue = analysisString(f, type, value, server);
                            queryParam += f + ":" + queryValue;
                        } else if (type.equalsIgnoreCase("text")) {
                            String[] queryValues = value.split(" ");

                            for (int i = 0; i < queryValues.length; i++) {
                                queryParam += f + ":" + queryValues[i];

                                if (i < queryValues.length - 1) {
                                    queryParam += " OR ";
                                }
                            }

                        } else {
                            queryValue = value;
                            queryParam += f + ":" + queryValue;
                        }


                        index++;
                        if (index < field.length) {
                            queryParam += " OR ";
                        }
                    }

                }
                result += "(" + queryParam + ") AND ";
            }
        }

        return result.substring(0, result.lastIndexOf(" AND "));
    }

    private String analysisString(String fieldName, String fieldType, String value, HttpSolrServer server) throws IOException, SolrServerException {
        FieldAnalysisRequest request = new FieldAnalysisRequest("/analysis/field");
        request.addFieldType(fieldType);
        request.addFieldName(fieldName);
        request.setFieldValue("text");

        request.setQuery(value);
        FieldAnalysisResponse response = request.process(server);
        Iterator iterator = response.getFieldNameAnalysis(fieldName).getQueryPhases().iterator();
        String queryStr = "";
        while (iterator.hasNext()) {
            AnalysisResponseBase.AnalysisPhase phase = (AnalysisResponseBase.AnalysisPhase) iterator.next();
            if (phase.getClassName().equals("org.wltea.analyzer.lucene.IKTokenizer")) {
                List<AnalysisResponseBase.TokenInfo> list = phase.getTokens();
                for (AnalysisResponseBase.TokenInfo info : list) {
                    queryStr += info.getText() + " ";
                }
            }
        }
        return queryStr;
    }

    public void save(String core, Map<String, String> queryMap) throws SolrServerException, IOException, JoymeSearchException {
        SolrCore solrCore = HotdeployConfigFactory.get().getConfig(CoreHotdeployPropsConfig.class).getSolrCoreMap().get(core);

        if (solrCore == null) {
            throw JoymeSearchException.CORE_NOT_EXISTS_EXCEPTION;
        }

        SolrDocumentList docs;
        HttpSolrServer server = null;
        try {
            String url = ConfigContainer.getSearchApiUrl() + "/" + solrCore.getCoreName() + "/";
            server = new HttpSolrServer(url);
            server.setSoTimeout(3000); // socket read timeout
            server.setConnectionTimeout(1000);
            server.setDefaultMaxConnectionsPerHost(1000);
            server.setMaxTotalConnections(10);
            server.setFollowRedirects(false); // defaults to false
            server.setAllowCompression(true);
            server.setMaxRetries(1);

            SolrInputDocument doc = new SolrInputDocument();

            Map<String, String> fieldNames = solrCore.getFieldNames();

            for (Map.Entry<String, String> entry : queryMap.entrySet()) {
                if (!fieldNames.containsKey(entry.getKey())) {
                    throw new JoymeSearchException("create error. field key:" + entry.getKey(), JoymeSearchException.FIELD_NOT_EXISTS_EXCEPTION.getErrorCode());
                }
                String type = fieldNames.get(entry.getKey());
                if (type == null || type.equalsIgnoreCase("string") || type.equalsIgnoreCase("textIKAnalyze") || type.equalsIgnoreCase("text")) {
                    doc.addField(entry.getKey(), entry.getValue());
                } else if (type.equalsIgnoreCase("int")) {
                    try {
                        doc.addField(entry.getKey(), Integer.parseInt(entry.getValue()));
                    } catch (NumberFormatException e) {
                        throw new JoymeSearchException("create error type illegl. field key:" + entry.getKey(), JoymeSearchException.FIELD_TYPE_ILLEGEL_EXCEPTION.getErrorCode());
                    }
                } else if (type.equalsIgnoreCase("long")) {
                    try {
                        doc.addField(entry.getKey(), Long.parseLong(entry.getValue()));
                    } catch (NumberFormatException e) {
                        throw new JoymeSearchException("create error type illegl. field key:" + entry.getKey(), JoymeSearchException.FIELD_TYPE_ILLEGEL_EXCEPTION.getErrorCode());
                    }
                }
            }
            server.add(doc);
            server.commit();
        } finally {
            if (server != null) {
                server.shutdown();
            }
        }
    }

    public void delete(String core, String query) throws SolrServerException, IOException, JoymeSearchException {
        SolrCore solrCore = HotdeployConfigFactory.get().getConfig(CoreHotdeployPropsConfig.class).getSolrCoreMap().get(core);

        if (solrCore == null) {
            throw JoymeSearchException.CORE_NOT_EXISTS_EXCEPTION;
        }

        SolrDocumentList docs;
        HttpSolrServer server = null;
        try {
            String url = ConfigContainer.getSearchApiUrl() + "/" + solrCore.getCoreName() + "/";
            server = new HttpSolrServer(url);
            server.setSoTimeout(3000); // socket read timeout
            server.setConnectionTimeout(1000);
            server.setDefaultMaxConnectionsPerHost(1000);
            server.setMaxTotalConnections(10);
            server.setFollowRedirects(false); // defaults to false
            server.setAllowCompression(true);
            server.setMaxRetries(1);

            String deleteQuery = queryString(query, server, solrCore);

            server.deleteByQuery(deleteQuery);
            server.commit();
        } finally {
            if (server != null) {
                server.shutdown();
            }
        }

    }

    //mlt=true&mlt.fl=title&mlt.mindf=1&mlt.mintf=1
    public SolrDocumentList mlt(String core, Map<String, Object> queryField, List<String> likeFields, int mltTf, int mltDf, SolrSortBean sortBean, int startIndex, int pageSize) throws SolrServerException, IOException, JoymeSearchException {
        SolrCore solrCore = HotdeployConfigFactory.get().getConfig(CoreHotdeployPropsConfig.class).getSolrCoreMap().get(core);

        if (solrCore == null) {
            throw JoymeSearchException.CORE_NOT_EXISTS_EXCEPTION;
        }

        SolrDocumentList docs;
        HttpSolrServer server = null;
        try {
            String url = ConfigContainer.getSearchApiUrl() + "/" + solrCore.getCoreName() ;
//            String url = "http://127.0.0.1:38000"+ "/" + "article" ;
            server = new HttpSolrServer(url);
            server.setSoTimeout(3000); // socket read timeout
            server.setConnectionTimeout(1000);
            server.setDefaultMaxConnectionsPerHost(1000);
            server.setMaxTotalConnections(10);
            server.setFollowRedirects(false); // defaults to false
            server.setAllowCompression(true);
            server.setMaxRetries(1);

            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setRequestHandler("/mlt");
            for (Map.Entry<String, Object> entry : queryField.entrySet()) {
                solrQuery.setQuery(entry.getKey() + ":" + entry.getValue());
            }

            String mltFl = "";
            for (int i = 0; i < likeFields.size(); i++) {
                mltFl += likeFields.get(i);
                if (i < likeFields.size() - 1) {
                    mltFl += ",";
                }
            }
            solrQuery.setParam("mlt", "true")
                    .setParam("mlt.fl", mltFl)
                    .setParam("mlt.mindf", String.valueOf(mltDf))
                    .setParam("mlt.mintf", String.valueOf(mltTf));

            //设置起始位置与返回结果数
            if (startIndex >= 0 && pageSize > 0) {
                solrQuery.setStart(startIndex);
                solrQuery.setRows(pageSize);
            }

            if (sortBean != null) {
                solrQuery.addSort(sortBean.getSortField(), SolrQuery.ORDER.desc);
            }
            QueryResponse qrsp = server.query(solrQuery);
            docs = qrsp.getResults();

            server.commit();
        } finally {
            if (server != null) {
                server.shutdown();
            }
        }

        return docs;

    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        Map<String,Object> qMap=new HashMap<String, Object>();
        qMap.put("id",1);
        List<String> mltFields=new ArrayList<String>();
        mltFields.add("title");
        try {
            System.out.println(new SolrService().mlt("article", qMap, mltFields, 1, 1, null, 0, 4));
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JoymeSearchException e) {
            e.printStackTrace();
        }

//        HttpClientManager manager = new HttpClientManager();
//
//        HttpParameter[] params = new HttpParameter[]{
//                new HttpParameter("c", "wiki-page"),
//                new HttpParameter("q", "(id:*)"),
//        };

//        System.out.println(URLEncoder.encode("熊", "UTF-8"));
//        System.out.println(manager.post("http://localhost:8080/search/query.do", params, null).getResult());

//        HttpParameter[] saveParams = new HttpParameter[]{
//                new HttpParameter("c", "wiki-page"),
//                new HttpParameter("field", "id=10001,wikikey=ma,title=COMBO一览"),
//        };

//        System.out.println(manager.post("http://localhost:8080/search/save.do", saveParams, null).getResult());
//        System.out.println(manager.post("http://web001.dev:38080/search/delete.do", params, null).getResult());
//        (tags :男 女)(tags:角色)

    }
}
