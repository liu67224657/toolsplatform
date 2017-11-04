package com.enjoyf.search.controller;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.search.bean.SolrSortBean;
import com.enjoyf.search.exception.JoymeSearchException;
import com.enjoyf.search.service.SolrService;
import com.enjoyf.search.util.JsontoMapUtil;
import com.enjoyf.search.util.ResultMsg;
import com.enjoyf.util.Pagination;
import com.enjoyf.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-10-9
 * Time: 下午3:08
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/search/")
public class SearchController extends AbstractBaseController {

    private SolrService service = new SolrService();

    /**
     * core
     * (field field field:a_b)(field field field:b)
     * age:desc(asc)
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("query.do")
    public String search(HttpServletRequest request, HttpServletResponse response) {
        String core = request.getParameter("c");
        String queryString = request.getParameter("q");
        String sortString = request.getParameter("sort");
        String p = request.getParameter("p");
        String pSize = request.getParameter("ps");

        if (StringUtil.isEmpty(queryString) || StringUtil.isEmpty(core)) {
            writeJson(response, ResultMsg.PARAM_NOT_EXISTS.returnJsonStr());
            return null;
        }

        JSONObject result = new JSONObject();
        try {
            SolrSortBean solrSortBean = null;
            if (!StringUtil.isEmpty(sortString)) {
                solrSortBean = SolrSortBean.praseParam(sortString);
            }

            int pageNum = -1;
            int pageSize = 20;

            if (!StringUtil.isEmpty(p)) {
                try {
                    pageNum = Integer.parseInt(p);
                } catch (NumberFormatException e) {
                }
            }

            if (pSize != null) {
                try {
                    pageSize = Integer.parseInt(pSize);
                } catch (NumberFormatException e) {
                }
            }

            Pagination pagination = null;
            int startIdx = -1;
            if (pageNum > 0) {
                pagination = new Pagination(pageNum * pageSize, pageNum, pageSize);
                startIdx = pagination.getStartRowIdx();
                pageSize = pagination.getPageSize();
            }

            SolrDocumentList documentList = service.search(core, queryString, solrSortBean, startIdx, pageSize);

            result.put("rs", 1);
            List list = new ArrayList();
            for (SolrDocument solrDocument : documentList) {
                JSONObject object = new JSONObject();
                for (Map.Entry<String, Object> entry : solrDocument.entrySet()) {
                    if (entry.getKey().startsWith("_")) {
                        continue;
                    }

                    object.put(entry.getKey(), entry.getValue());
                }
                list.add(object);
            }
            result.put("result", list);
            if (pagination != null) {
                pagination.setTotalRows((int) documentList.getNumFound());

                Map<String, Long> pageMap = new HashMap<String, Long>();
                pageMap.put("curpage", Long.valueOf(pageNum));
                pageMap.put("pagesize", Long.valueOf(pageSize));
                pageMap.put("total", documentList.getNumFound());
                pageMap.put("maxpage", Long.valueOf(pagination.getMaxPage()));

                result.put("page", pageMap);
            }

            writeJson(response, result.toString());
            return null;
        } catch (SolrServerException e) {
            LogService.errorSystemLog(" occured SolrServerException.", e);
            writeJson(response, ResultMsg.SERVER_ERROR.returnJsonStr());
            return null;
        } catch (IOException e) {
            LogService.errorSystemLog(" occured IOException.", e);
            writeJson(response, ResultMsg.SERVER_ERROR.returnJsonStr());
            return null;
        } catch (JoymeSearchException e) {
            LogService.errorSystemLog(" occured JoymeSearchException.", e);

            if (e.equals(JoymeSearchException.CORE_NOT_EXISTS_EXCEPTION)) {
                writeJson(response, ResultMsg.CORE_NOT_EXISTS.returnJsonStr());
            } else if (e.equals(JoymeSearchException.FIELD_NOT_EXISTS_EXCEPTION)) {
                writeJson(response, ResultMsg.FIELD_NOT_EXISTS.returnJsonStr());
            }
            return null;
        } catch (Exception e) {
            LogService.errorSystemLog(" occured Exception.", e);
            writeJson(response, ResultMsg.SERVER_ERROR.returnJsonStr());
        }

        writeJson(response, result.toString());
        return null;
    }

    /**
     * core
     * field:k=v,k1=v1,k2=v2
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("save.do")
    public String save(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        String core = request.getParameter("c");
        String queryString = request.getParameter("field");
        if (StringUtil.isEmpty(queryString) || StringUtil.isEmpty(core)) {
            writeJson(response, ResultMsg.PARAM_NOT_EXISTS.returnJsonStr());
            return null;
        }

        Map<String, String> fieldMap = new HashMap<String, String>();
        String[] fields = queryString.split(",");
        for (String field : fields) {
            String[] keyValues = field.split("=");
            fieldMap.put(keyValues[0], keyValues[1]);
        }


        try {
            service.save(core, fieldMap);
            writeJson(response, ResultMsg.SUCCESS.returnJsonStr());
            return null;
        } catch (SolrServerException e) {
            LogService.errorSystemLog(" occured SolrServerException.", e);

            writeJson(response, ResultMsg.SERVER_ERROR.returnJsonStr());
            return null;
        } catch (IOException e) {
            LogService.errorSystemLog(" occured IOException.", e);

            writeJson(response, ResultMsg.SERVER_ERROR.returnJsonStr());
            return null;
        } catch (JoymeSearchException e) {
            LogService.errorSystemLog(" occured JoymeSearchException.", e);

            if (e.equals(JoymeSearchException.CORE_NOT_EXISTS_EXCEPTION)) {
                writeJson(response, ResultMsg.CORE_NOT_EXISTS.returnJsonStr());

            } else if (e.equals(JoymeSearchException.FIELD_NOT_EXISTS_EXCEPTION)) {
                writeJson(response, ResultMsg.FIELD_NOT_EXISTS.returnJsonStr());
            } else if (e.equals(JoymeSearchException.FIELD_TYPE_ILLEGEL_EXCEPTION)) {
                writeJson(response, ResultMsg.FIELD_ERROR.returnJsonStr());
            }
            return null;
        } catch (Exception e) {
            LogService.errorSystemLog(" occured Exception.", e);

            writeJson(response, ResultMsg.SYSTEM_ERROR.returnJsonStr());
            return null;
        }
    }


    /**
     * core
     * field:{"entryid":"1b0251ccb8bd5f9ccf444e4bda7713e3","id":"1224","type":1,"title":"是先升级阴阳师御灵还,是先升级御魂呢"}
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("jsonsave.do")
    public String jsonsave(HttpServletRequest request, HttpServletResponse response) {
        String core = request.getParameter("c");
        String queryString = request.getParameter("field");
        if (StringUtil.isEmpty(queryString) || StringUtil.isEmpty(core)) {
            writeJson(response, ResultMsg.PARAM_NOT_EXISTS.returnJsonStr());
            return null;
        }
        Map<String, String> fieldMap = JsontoMapUtil.jsontoMap(queryString);

        if (fieldMap == null) {
            writeJson(response, ResultMsg.FIELDMAP_IS_NULL.returnJsonStr());
            LogService.errorSystemLog("fieldmap.is.null." + ",core=" + core + ",queryString=" + queryString);
            return null;
        }

        try {
            service.save(core, fieldMap);
            writeJson(response, ResultMsg.SUCCESS.returnJsonStr());
            return null;
        } catch (SolrServerException e) {
            LogService.errorSystemLog(" occured SolrServerException.", e);

            writeJson(response, ResultMsg.SERVER_ERROR.returnJsonStr());
            return null;
        } catch (IOException e) {
            LogService.errorSystemLog(" occured IOException.", e);

            writeJson(response, ResultMsg.SERVER_ERROR.returnJsonStr());
            return null;
        } catch (JoymeSearchException e) {
            LogService.errorSystemLog(" occured JoymeSearchException.", e);

            if (e.equals(JoymeSearchException.CORE_NOT_EXISTS_EXCEPTION)) {
                writeJson(response, ResultMsg.CORE_NOT_EXISTS.returnJsonStr());

            } else if (e.equals(JoymeSearchException.FIELD_NOT_EXISTS_EXCEPTION)) {
                writeJson(response, ResultMsg.FIELD_NOT_EXISTS.returnJsonStr());
            } else if (e.equals(JoymeSearchException.FIELD_TYPE_ILLEGEL_EXCEPTION)) {
                writeJson(response, ResultMsg.FIELD_ERROR.returnJsonStr());
            }
            return null;
        } catch (Exception e) {
            LogService.errorSystemLog(" occured Exception.", e);

            writeJson(response, ResultMsg.SYSTEM_ERROR.returnJsonStr());
            return null;
        }
    }

    /**
     * core
     * (field field field:a_b)(field field field:b)
     * age:desc(asc)
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();

        String core = request.getParameter("c");
        String queryString = request.getParameter("q");
        if (StringUtil.isEmpty(queryString) || StringUtil.isEmpty(core)) {
            writeJson(response, ResultMsg.PARAM_NOT_EXISTS.returnJsonStr());
            return null;
        }

        try {
            service.delete(core, queryString);
            writeJson(response, ResultMsg.SUCCESS.returnJsonStr());
            return null;
        } catch (SolrServerException e) {
            LogService.errorSystemLog(" occured SolrServerException.", e);

            writeJson(response, ResultMsg.SERVER_ERROR.returnJsonStr());
            return null;
        } catch (IOException e) {
            LogService.errorSystemLog(" occured IOException.", e);

            writeJson(response, ResultMsg.SERVER_ERROR.returnJsonStr());
            return null;
        } catch (JoymeSearchException e) {
            LogService.errorSystemLog(" occured JoymeSearchException.", e);

            if (e.equals(JoymeSearchException.CORE_NOT_EXISTS_EXCEPTION)) {
                writeJson(response, ResultMsg.CORE_NOT_EXISTS.returnJsonStr());

            } else if (e.equals(JoymeSearchException.FIELD_NOT_EXISTS_EXCEPTION)) {
                writeJson(response, ResultMsg.FIELD_NOT_EXISTS.returnJsonStr());
            } else if (e.equals(JoymeSearchException.FIELD_TYPE_ILLEGEL_EXCEPTION)) {
                writeJson(response, ResultMsg.FIELD_ERROR.returnJsonStr());
            }
            return null;
        } catch (Exception e) {
            LogService.errorSystemLog(" occured Exception.", e);

            writeJson(response, ResultMsg.SYSTEM_ERROR.returnJsonStr());
            return null;
        }
    }


    /**
     * c=article&qexps=id:liuhao&likefield=title&mltf=1&mldf=2&sort=id:desc&p=1&ps=4
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("mlt.do")
    public String mlt(HttpServletRequest request, HttpServletResponse response) {
        String core = request.getParameter("c");
        String querExps = request.getParameter("qexps");
        String likefield = request.getParameter("likefield");
        String mltfParam = request.getParameter("mltf");
        String mldfParam = request.getParameter("mldf");
        String sortString = request.getParameter("sort");
        String p = request.getParameter("p");
        String pSize = request.getParameter("ps");
        if (StringUtil.isEmpty(likefield) || StringUtil.isEmpty(core) || StringUtil.isEmpty(querExps)) {
            writeJson(response, ResultMsg.PARAM_NOT_EXISTS.returnJsonStr());
            return null;
        }

        String[] likeArray = likefield.split(",");
        List<String> likeFieldList = new ArrayList<String>();
        for (String like : likeArray) {
            likeFieldList.add(like);
        }
        int mltf = 1;
        try {
            if (!StringUtil.isEmpty(mltfParam)) {
                mltf = Integer.parseInt(mltfParam);
            }
        } catch (NumberFormatException e) {
        }
        int mldf = 1;
        try {
            if (!StringUtil.isEmpty(mldfParam)) {
                mldf = Integer.parseInt(mldfParam);
            }
        } catch (NumberFormatException e) {
        }


        Map<String, Object> queryMap = new HashMap<String, Object>();
        String[] queryExpArray = querExps.split(",");
        for (String queryExp : queryExpArray) {
            String[] queryFiled = queryExp.split(":");
            queryMap.put(queryFiled[0], queryFiled[1]);
        }

        JSONObject result = new JSONObject();
        SolrSortBean solrSortBean = null;
        if (!StringUtil.isEmpty(sortString)) {
            solrSortBean = SolrSortBean.praseParam(sortString);
        }

        int pageNum = -1;
        int pageSize = 20;

        if (!StringUtil.isEmpty(p)) {
            try {
                pageNum = Integer.parseInt(p);
            } catch (NumberFormatException e) {
            }
        }

        if (pSize != null) {
            try {
                pageSize = Integer.parseInt(pSize);
            } catch (NumberFormatException e) {
            }
        }

        Pagination pagination = null;
        int startIdx = -1;
        if (pageNum > 0) {
            pagination = new Pagination(pageNum * pageSize, pageNum, pageSize);
            startIdx = pagination.getStartRowIdx();
            pageSize = pagination.getPageSize();
        }

        try {
            SolrDocumentList documentList = service.mlt(core, queryMap, likeFieldList, mltf, mldf, solrSortBean, startIdx, pageSize);

            result.put("rs", 1);
            List list = new ArrayList();
            for (SolrDocument solrDocument : documentList) {
                JSONObject object = new JSONObject();
                for (Map.Entry<String, Object> entry : solrDocument.entrySet()) {
                    if (entry.getKey().startsWith("_")) {
                        continue;
                    }

                    object.put(entry.getKey(), entry.getValue());
                }
                list.add(object);
            }
            result.put("result", list);
            if (pagination != null) {
                pagination.setTotalRows((int) documentList.getNumFound());
                Map<String, Long> pageMap = new HashMap<String, Long>();
                pageMap.put("curpage", Long.valueOf(pageNum));
                pageMap.put("pagesize", Long.valueOf(pageSize));
                pageMap.put("total", documentList.getNumFound());
                pageMap.put("maxpage", Long.valueOf(pagination.getMaxPage()));
                result.put("page", pageMap);
            }

            writeJson(response, result.toString());
            return null;
        } catch (JoymeSearchException e) {
            LogService.errorSystemLog(" occured JoymeSearchException.", e);

            if (e.equals(JoymeSearchException.CORE_NOT_EXISTS_EXCEPTION)) {
                writeJson(response, ResultMsg.CORE_NOT_EXISTS.returnJsonStr());
            } else if (e.equals(JoymeSearchException.FIELD_NOT_EXISTS_EXCEPTION)) {
                writeJson(response, ResultMsg.FIELD_NOT_EXISTS.returnJsonStr());
            } else if (e.equals(JoymeSearchException.FIELD_TYPE_ILLEGEL_EXCEPTION)) {
                writeJson(response, ResultMsg.FIELD_ERROR.returnJsonStr());
            }
            return null;
        } catch (Exception e) {
            LogService.errorSystemLog(" occured Exception.", e);

            writeJson(response, ResultMsg.SYSTEM_ERROR.returnJsonStr());
            return null;
        }
    }

}
