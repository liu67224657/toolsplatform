package com.joyme.servlet;

import com.joyme.parameter.ParamJson;
import com.joyme.util.CollectionUtil;
import com.joyme.util.SystemUtil;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-1-23
 * Time: 上午10:10
 * 读取配置文件
 */
public class MessageService {
	private static SystemUtil util = new SystemUtil();

	protected void loadMessagePropertis() throws IOException {
		String jdbcPath = util.getMetaInfFolderPath();
		String file = jdbcPath + "/message.properties";
		InputStream io = null;
		try {
			io = new FileInputStream(new File(file));
			Properties prop = new Properties();
			prop.load(io);

			//所有的放进去
			Map<String, String> messageMap = new HashMap<String, String>();
			for (String s : prop.stringPropertyNames()) {
				messageMap.put(s, prop.getProperty(s));
			}
			MessagePropertie.setMessageMap(messageMap);


			MessagePropertie.test_name = prop.getProperty("test_name");

			MessagePropertie.product = prop.getProperty("product");
			List<ParamJson> listProuct = new ArrayList<ParamJson>();
			Map<String, List<ParamJson>> productMaps = new HashMap<String, List<ParamJson>>();
			String productStr[] = MessagePropertie.product.split(",");
			for (String str : productStr) {
				String s[] = str.split("\\|");
				ParamJson paramJson = new ParamJson();
				paramJson.setKey(s[0]);
				paramJson.setValue(s[1]);
				listProuct.add(paramJson);


				String productVal = prop.getProperty("product." + s[0]);
				if(!StringUtils.isEmpty(productVal)){
					String productVals[] = productVal.split(",");
					List<ParamJson> temp = new ArrayList<ParamJson>();
					for (String pp : productVals) {
						String p[] = pp.split("\\|");
						ParamJson param = new ParamJson();
						param.setKey(p[0]);
						param.setValue(p[1]);
						temp.add(param);
					}
					if (!CollectionUtil.isEmpty(temp)) {
						productMaps.put("product." + s[0], temp);
					}
				}

			}


			MessagePropertie.setProductMaps(productMaps);

			MessagePropertie.setListProuct(listProuct);


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (io != null) {
				io.close();
			}
		}
	}
}
