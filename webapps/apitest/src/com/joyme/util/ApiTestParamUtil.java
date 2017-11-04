package com.joyme.util;

import com.joyme.parameter.Param;
import com.joyme.parameter.ParamJson;
import com.joyme.parameter.ParamSngl;
import com.joyme.parameter.PostParameterDTO;
import com.joyme.servlet.MessagePropertie;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-9-28
 * Time: 下午4:38
 * To change this template use File | Settings | File Templates.
 */
public class ApiTestParamUtil {
	public static String getPost_params(String url, PostParameter[] params) {
		StringBuffer str = new StringBuffer(url);
		str.append("?");
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				PostParameter p = params[i];
				if (i != 0) {
					str.append("&");
				}

				str.append(p.getName() + "=" + p.getValue());
			}
		}
		return str.toString();
	}


	public static PostParameterDTO getMessagePropertes(String product, String env, String api) {
		String messageVal = MessagePropertie.getMessageMap().get(product + "." + env + "." + api);
		PostParameterDTO dto = new PostParameterDTO();
		if (!StringUtils.isEmpty(messageVal)) {
			String mess[] = messageVal.split("\\?");
			dto.setUrl(mess[0]);

			String me[] = mess[1].split("&");
			PostParameter postParameter[] = new PostParameter[me.length];
			for (int i = 0; i < me.length; i++) {
				String params[] = me[i].split("=");
				if (params.length == 2) {
					PostParameter parameter = new PostParameter(params[0], params[1]);
					postParameter[i] = parameter;
				} else {
					PostParameter parameter = new PostParameter(params[0], "");
					postParameter[i] = parameter;
				}
			}
			dto.setPostParameter(postParameter);
		}
		return dto;
	}


	public static List<ParamJson> getNewListParmV2(PostParameter[] postParameters, List<ParamJson> list) {
		List<ParamJson> newArrayList = new ArrayList<ParamJson>();
		Map<String, String> map = new HashMap<String, String>();


		if (postParameters != null && postParameters.length > 0) {
			for (PostParameter postParameter : postParameters) {
				map.put(postParameter.getName(), postParameter.getValue());
			}
		}

		if (list != null && list.size() > 0) {
			for (ParamJson paramJson : list) {
				map.put(paramJson.getKey(), paramJson.getValue());
			}
		}

		if (map.containsKey("uno")) {
			String uno = map.get("uno");
			String time = map.get("time");
			String secret = "";
			if (StringUtils.isEmpty(time)) {
				time = System.currentTimeMillis() + "";
				map.put("time", time);
			}

			try {
				secret = DESUtil.desEncrypt(map.get("secret"), uno + time);
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("secret", secret);
		}

		for (String key : map.keySet()) {
			ParamJson paramJson = new ParamJson();
			paramJson.setKey(key);
			paramJson.setValue(map.get(key));
			newArrayList.add(paramJson);
		}

		return newArrayList;
	}

	public static List<ParamJson> getNewListParm(String env, List<ParamJson> list) {
		if (StringUtils.isEmpty(env)) {
			return list;
		}
		Param paramEnvImpl = ParamSngl.get().getEnvImpl(env);
		List<ParamJson> newArrayList = new ArrayList<ParamJson>();
		Map<String, String> keyMap = new HashMap<String, String>();

		String uno = "";
		String time = "";
		String secret = "";


		for (ParamJson paramJson : list) {
			keyMap.put(paramJson.getKey(), paramJson.getValue());
			ParamJson par = new ParamJson();
			par.setKey(paramJson.getKey());
			par.setValue(paramJson.getValue());
			newArrayList.add(par);
		}

		if (!keyMap.containsKey("uno")) {
			ParamJson par = new ParamJson();
			par.setKey("uno");
			par.setValue(paramEnvImpl.getUno());
			uno = paramEnvImpl.getUno();
			newArrayList.add(par);
		} else {
			uno = keyMap.get("uno");
		}

		if (!keyMap.containsKey("time")) {
			time = System.currentTimeMillis() + "";
			ParamJson par = new ParamJson();
			par.setKey("time");
			par.setValue(time);
			newArrayList.add(par);
		} else {
			time = keyMap.get("time");
		}

		if (!keyMap.containsKey("secret")) {
			ParamJson par = new ParamJson();
			par.setKey("secret");
			try {
				secret = DESUtil.desEncrypt(paramEnvImpl.getSecret(), uno + time);
			} catch (Exception e) {
				e.printStackTrace();
			}
			par.setValue(secret);
			newArrayList.add(par);
		} else {
			secret = keyMap.get("secret");
		}

		if (!keyMap.containsKey("appkey")) {
			ParamJson par = new ParamJson();
			par.setKey("appkey");
			par.setValue(paramEnvImpl.getAppKey());
			newArrayList.add(par);
		} else {
			secret = keyMap.get("secret");
		}

		return newArrayList;
	}

}
