package com.enjoyf.webapps.joyme.webpage.controller.kdxf;

import com.enjoyf.util.JsonBinder;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-10-23 上午10:13
 * Description:
 */
@Controller
@RequestMapping(value = "/word/analyze")
public class WordAnalyzeController {

    private JsonBinder jsonBinder = JsonBinder.buildNormalBinder();

    @ResponseBody
    @RequestMapping
    private String analyzeWord(@RequestParam(value = "word") String word,HttpServletRequest request) throws IOException {
        //创建分词对象

        IKAnalyzer anal = new IKAnalyzer(true);
        StringReader reader = new StringReader(word);
        //分词
        TokenStream ts = anal.tokenStream("", reader);
        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
        //遍历分词数据
        HashSet<String> set = new LinkedHashSet<String>();

        while (ts.incrementToken()) {
            set.add(term.toString());
        }
        reader.close();

        return jsonBinder.toJson(set);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s="%E9%87%91%E6%AF%9B%E7%8B%AE%E7%8E%8Bvs%E5%BC%A0%E6%97%A0%E5%BF%8C";

        System.out.println(URLDecoder.decode(s,"UTF-8"));
    }
}
