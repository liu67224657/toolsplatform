package com.enjoyf.wiki.facade;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.CookieUtil;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;
import com.enjoyf.wiki.bean.CardCompare;
import com.enjoyf.wiki.bean.CardInfo;
import com.enjoyf.wiki.service.JoymeTemplateService;
import com.enjoyf.wiki.util.CollectionUtil;
import com.enjoyf.wiki.util.FreemarkerTemplateGenerator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.*;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  2014/5/20 16:58
 * Description:
 */
public class CardSelectFacade {

    public static String DATA_COMPARE_TEXT = "data-compare-text";//对比工具
    public static String DATA_COMPARE_TEXT_OPINION = "data-compare-text-opinion";//意见反馈

    private static URLUtil urlUtil = new URLUtil();
    private JoymeTemplateService templateService = new JoymeTemplateService();

    public Map<String, Object> getCardCompareModel(HttpServletRequest request, HttpServletResponse response, String wikiCode, String wikiType, String channel) {
        Map<String, Object> map = new HashMap<String, Object>();

//        channel = "wx";
        try {
            String cookieValue = null;
            try {
                cookieValue = CookieUtil.getCookieValue(request, "compare");
                System.out.println(this.getClass().getName() + ("cookieValue:" + cookieValue));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //  cookieValue = "%5B%7B%22url%22%3A%22http%3A%2F%2Fcf.joyme.com%2Fwxwiki%2F415661.shtml%22%7D%2C%7B%22url%22%3A%22http%3A%2F%2Fcf.joyme.com%2Fwxwiki%2F415721.shtml%22%7D%5D";
            //  cookieValue = "%5B%7B%22urlid%22%3A%22%E6%8F%90%E5%B0%94%22%2C%22boxid%22%3A%22box_%E6%8F%90%E5%B0%94%22%2C%22url%22%3A%22http%3A%2F%2Fwww.joyme.com%2Fwiki%2Ftos%2F58869.shtml%22%2C%22info%22%3A%22%26lt%3Ba%20href%3D%26quot%3B58869.shtml%26quot%3B%20title%3D%26quot%3B%E6%8F%90%E5%B0%94%26quot%3B%26gt%3B%26lt%3Bimg%20alt%3D%26quot%3B203s.png%26quot%3B%20src%3D%26quot%3Bhttp%3A%2F%2Ftos.joyme.com%2Fimages%2Ftos%2Fd%2Fd4%2F203s.png%26quot%3B%20style%3D%26quot%3Bwidth%3A%20100%25%3Bmax-width%3A%2063px%3Bheight%3Aauto%26quot%3B%26gt%3B%26lt%3B%2Fa%26gt%3B%26lt%3Bstrong%26gt%3B%26lt%3Ba%20href%3D%26quot%3B58869.shtml%26quot%3B%20title%3D%26quot%3B%E6%8F%90%E5%B0%94%26quot%3B%26gt%3B%E6%8F%90%E5%B0%94%26lt%3B%2Fa%26gt%3B%26lt%3B%2Fstrong%26gt%3B%26lt%3Bp%26gt%3B%26lt%3B%2Fp%26gt%3B%26lt%3Bp%20class%3D%26quot%3Bp_operation%26quot%3B%26gt%3B%26lt%3Bspan%26gt%3B%E7%A8%80%E6%9C%89%E5%BA%A6%3A%26lt%3B%2Fspan%26gt%3B5%E2%98%85%26lt%3B%2Fp%26gt%3B%22%7D%2C%7B%22urlid%22%3A%22%E8%B4%B9%E8%95%BE%E9%9B%85%22%2C%22boxid%22%3A%22box_%E8%B4%B9%E8%95%BE%E9%9B%85%22%2C%22url%22%3A%22http%3A%2F%2Fwww.joyme.com%2Fwiki%2Ftos%2F58871.shtml%22%2C%22info%22%3A%22%26lt%3Ba%20href%3D%26quot%3B58871.shtml%26quot%3B%20title%3D%26quot%3B%E8%B4%B9%E8%95%BE%E9%9B%85%26quot%3B%26gt%3B%26lt%3Bimg%20alt%3D%26quot%3B205s.png%26quot%3B%20src%3D%26quot%3Bhttp%3A%2F%2Ftos.joyme.com%2Fimages%2Ftos%2F9%2F95%2F205s.png%26quot%3B%20style%3D%26quot%3Bwidth%3A%20100%25%3Bmax-width%3A%2063px%3Bheight%3Aauto%26quot%3B%26gt%3B%26lt%3B%2Fa%26gt%3B%26lt%3Bstrong%26gt%3B%26lt%3Ba%20href%3D%26quot%3B58871.shtml%26quot%3B%20title%3D%26quot%3B%E8%B4%B9%E8%95%BE%E9%9B%85%26quot%3B%26gt%3B%E8%B4%B9%E8%95%BE%E9%9B%85%26lt%3B%2Fa%26gt%3B%26lt%3B%2Fstrong%26gt%3B%26lt%3Bp%26gt%3B%26lt%3B%2Fp%26gt%3B%26lt%3Bp%20class%3D%26quot%3Bp_operation%26quot%3B%26gt%3B%26lt%3Bspan%26gt%3B%E7%A8%80%E6%9C%89%E5%BA%A6%3A%26lt%3B%2Fspan%26gt%3B5%E2%98%85%26lt%3B%2Fp%26gt%3B%22%7D%2C%7B%22urlid%22%3A%22%E8%B4%B9%E9%9B%B7%22%2C%22boxid%22%3A%22box_%E8%B4%B9%E9%9B%B7%22%2C%22url%22%3A%22http%3A%2F%2Fwww.joyme.com%2Fwiki%2Ftos%2F58867.shtml%22%2C%22info%22%3A%22%26lt%3Ba%20href%3D%26quot%3B58867.shtml%26quot%3B%20title%3D%26quot%3B%E8%B4%B9%E9%9B%B7%26quot%3B%26gt%3B%26lt%3Bimg%20alt%3D%26quot%3B201s.png%26quot%3B%20src%3D%26quot%3Bhttp%3A%2F%2Ftos.joyme.com%2Fimages%2Ftos%2Fd%2Fd1%2F201s.png%26quot%3B%20style%3D%26quot%3Bwidth%3A%20100%25%3Bmax-width%3A%2063px%3Bheight%3Aauto%26quot%3B%26gt%3B%26lt%3B%2Fa%26gt%3B%26lt%3Bstrong%26gt%3B%26lt%3Ba%20href%3D%26quot%3B58867.shtml%26quot%3B%20title%3D%26quot%3B%E8%B4%B9%E9%9B%B7%26quot%3B%26gt%3B%E8%B4%B9%E9%9B%B7%26lt%3B%2Fa%26gt%3B%26lt%3B%2Fstrong%26gt%3B%26lt%3Bp%26gt%3B%26lt%3B%2Fp%26gt%3B%26lt%3Bp%20class%3D%26quot%3Bp_operation%26quot%3B%26gt%3B%26lt%3Bspan%26gt%3B%E7%A8%80%E6%9C%89%E5%BA%A6%3A%26lt%3B%2Fspan%26gt%3B5%E2%98%85%26lt%3B%2Fp%26gt%3B%22%7D%5D";
            if (StringUtil.isEmpty(cookieValue)) {
                map.put("error", "card.error.isnull");
                return map;
            }

            cookieValue = URLDecoder.decode(cookieValue, "UTF-8");
            LogService.infoSystemLog(this.getClass().getName() + "############### : " + cookieValue + "#####################", true);
            List<String> urlList = getUrlList(cookieValue);

            int i = 0;
            List<String> keyList = new ArrayList<String>();
            List<CardInfo> cardInfoList = new ArrayList<CardInfo>();

            List<String> cardNameList = new ArrayList<String>();
            for (String url : urlList) {
                if (i > 2) {
                    break;
                }

                Document cardDoc = Jsoup.parse(urlUtil.openURLWithTimeOut(url));
                Elements elements = cardDoc.getElementsByAttribute("data-compare-text");
                String cardImage = getImage(cardDoc);
                String cardName = getName(cardDoc);
                cardNameList.add(cardName);
                Map<String, String> cardMap = getCardCompare(elements, CardSelectFacade.DATA_COMPARE_TEXT);


                cardInfoList.add(new CardInfo(cardName, cardImage, cardMap));
                if (i <= 0) {
                    keyList = getKeyList(elements);
                }
                i++;
            }
            String s = "";
            String templateType = request.getParameter("template");
            if ("wx".equals(channel)) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("cardInfoList", cardInfoList);
                param.put("keyList", keyList);
                //模版2
                if (!StringUtil.isEmpty(templateType) && "2".equals(templateType)) {
                    /**
                     *        抓取对比点评的URL  抓取规则： xx.joyme.com/wiki/对比武器名称1对比武器名称2
                     *        例如 cf.joyme.com/wiki/AK47AK47_A   如果获得页面不为空 获取id为  wuyuanjun的div
                     *        如果  xx.joyme.com/wiki/武器1武器2 抓取为空 则需要抓取一下xx.joyme.com/wiki/武器2武器1 避免WD那边生成多个冗余页面
                     */
                    if (!CollectionUtil.isEmpty(cardNameList) && cardNameList.size() >= 2) {
                        String cardName = cardNameList.get(0) + cardNameList.get(1);
                        String dpURL = "http://" + wikiCode + ".joyme.com/wiki/" + cardName;
                        //  dpURL = "http://cf.joyme.com/wiki/" + cardName;
                        String url = urlUtil.openURLWithTimeOut(dpURL);
                        boolean bool = false;
                        if (StringUtil.isEmpty(url)) {
                            param.put("dianping", "敬请期待");
                            dpURL = "http://" + wikiCode + ".joyme.com/wiki/" + cardNameList.get(1) + cardNameList.get(0);
                            //   dpURL = "http://cf.joyme.com/wiki/" + cardNameList.get(1) + cardNameList.get(0);
                            url = urlUtil.openURLWithTimeOut(dpURL);
                            if (!StringUtil.isEmpty(url)) {
                                bool = true;
                            }
                        } else {
                            bool = true;
                        }
                        if (bool) {
                            Document cardDoc = Jsoup.parse(url);
                            Element element = cardDoc.getElementById("wuyuanjun");
                            if (element != null) {
                                param.put("dianping", element.html());
                            } else {
                                param.put("dianping", "敬请期待");
                            }
                        }
                    }
                    s = FreemarkerTemplateGenerator.get().generateTemplate("/ftl/wxcardcompare2.ftl", param);
                } else {
                    s = FreemarkerTemplateGenerator.get().generateTemplate("/ftl/wxcardcompare.ftl", param);
                }
            } else {
                //数值对比 ，是整数且最大的标红
                Map<String, List<String>> listMaps = new HashMap<String, List<String>>();
                //返回的map
                Map<String, String> returnMap = null;
                for (String key : keyList) {
                    listMaps.put(key, new ArrayList<String>());
                }
                //把map里key相同的value转换成map<String,List>
                for (CardInfo cardInfo : cardInfoList) {
                    Set<String> keySet = cardInfo.getCardMap().keySet();
                    for (String key : keySet) {
                        if (listMaps.containsKey(key)) {
                            listMaps.get(key).add(cardInfo.getCardMap().get(key));
                        }
                    }
                }

                if (listMaps != null) {
                    List<String> removeKeyList = new ArrayList<String>();
                    for (String key : listMaps.keySet()) {
                        List<String> listString = listMaps.get(key);
                        try {
                            for (String cardValue : listString) {
                                Integer.parseInt(cardValue);
                            }
                        } catch (NumberFormatException e) {
                            //把不是整数的元素key储存下来
                            removeKeyList.add(key);
                            continue;
                        }
                    }
                    //排除不是整数的key
                    for (String removeKey : removeKeyList) {
                        listMaps.remove(removeKey);
                    }
                    returnMap = new HashMap<String, String>();

                    for (String key : listMaps.keySet()) {
                        List<String> listString = listMaps.get(key);
                        for (int n = 0; n < listString.size() - 1; n++) {
                            for (int j = 1; j < listString.size() - n; j++) {
                                Integer a = Integer.parseInt(listString.get(j - 1));
                                Integer b = Integer.parseInt(listString.get(j));

                                if (a.compareTo(b) <= 0) { // 比较两个整数的大小
                                    String temp = listString.get(j);
                                    listString.set((j), listString.get(j - 1));
                                    listString.set(j - 1, temp);
                                }
                            }
                        }
                        //查询List里的重复数 如果全部一样则不标红
                        int num = Collections.frequency(listString, listString.get(0));
                        if (num != listString.size()) {
                            returnMap.put(key, listString.get(0));
                        }
                    }
                }

                CardCompare cardCompare = new CardCompare();
                cardCompare.setKeyList(keyList);
                cardCompare.setCardInfoList(cardInfoList);


                Map<String, Object> param = new HashMap<String, Object>();
                param.put("cardObj", cardCompare);
                param.put("returnMap", returnMap);
                s = FreemarkerTemplateGenerator.get().generateTemplate("/ftl/cardcompare.ftl", param);
            }

            if (StringUtil.isEmpty(channel)) {
                channel = "default";
            }
            String template = templateService.getTemplate(wikiCode, channel, 3, wikiType);
            if (template != null) {
                template = template.replace("{joyme:wiki_body}", s);
            } else {
                template = wikiType.equals("wiki") ? ToolsTemplate.CARD_COMPARE_WIKI_DEFAULT.replace("{joyme:wiki_body}", s) :
                        ToolsTemplate.CARD_COMPARE_MWIKI_DEFAULT.replace("{joyme:wiki_body}", s);
            }
            map.put("result", template);


            //CookieUtil.setCookie(request, response, "compare", "", "enjoyf.com", 0);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "system.error");
        }

        return map;
    }

    /**
     * 判断字符串是否为整数
     *
     * @param value
     * @return
     */
    private boolean isValidInt(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public Map<String, String> getCardCompare(Elements elements, String attr) {
        Map<String, String> cardMap = new HashMap<String, String>();
        for (Element element : elements) {
            String key = element.attr(attr);
            cardMap.put(key, element.text());
        }
        return cardMap;
    }


    private List<String> getKeyList(Elements elements) {
        List<String> keyList = new ArrayList<String>();
        for (Element element : elements) {
            keyList.add(element.attr("data-compare-text"));
        }
        return keyList;
    }

    public String getImage(Document doc) {
        Elements elements = doc.getElementsByAttribute("data-compare-img");
        if (elements != null && elements.size() > 0) {
            Elements link = elements.get(0).getElementsByTag("a");
            if (link != null && link.size() > 0) {
                Elements img = link.get(0).getElementsByTag("img");
                if (img != null && img.size() > 0) {
                    return img.get(0).attr("src");
                }
            }
        }
        return null;
    }

    public String getName(Document doc) {
        Elements elements = doc.getElementsByAttribute("data-compare-name");
        if (elements != null && elements.size() > 0) {
            return elements.text();
        }
        return null;
    }

    private List<String> getUrlList(String cookieValue) {
        List<String> list = new ArrayList<String>();

        JSONArray array = JSONArray.fromObject(cookieValue);
        for (Object obj : array) {
            JSONObject jsonObject = (JSONObject) obj;

            String url = (String) jsonObject.get("url");
            if (!StringUtil.isEmpty(url)) {
                list.add(url);
            }
        }

        return list;
    }
}
