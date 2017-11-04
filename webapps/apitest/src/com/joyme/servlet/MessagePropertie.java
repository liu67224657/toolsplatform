package com.joyme.servlet;

import com.joyme.parameter.ParamJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-3-19
 * Time: 上午10:29
 * MessagePropertie
 */
public class MessagePropertie {

	private static MessagePropertie instance = new MessagePropertie();

	public static Map<String,String> messageMap = new HashMap<String, String>();


	public static Map<String,List<ParamJson>> productMaps = new HashMap<String, List<ParamJson>>();

	public static String test_name;
	public static String product;

	public static MessagePropertie getInstance() {
		return instance;
	}

	private static List<ParamJson> listProuct = new ArrayList<ParamJson>();

	public static List<ParamJson> getListProuct() {
		return listProuct;
	}

	public static void setListProuct(List<ParamJson> listProuct) {
		MessagePropertie.listProuct = listProuct;
	}

	public static String getTest_name() {
		return test_name;
	}

	public static void setTest_name(String test_name) {
		MessagePropertie.test_name = test_name;
	}

	public static String getProduct() {
		return product;
	}

	public static void setProduct(String product) {
		MessagePropertie.product = product;
	}

	public static Map<String, String> getMessageMap() {
		return messageMap;
	}

	public static void setMessageMap(Map<String, String> messageMap) {
		MessagePropertie.messageMap = messageMap;
	}

	public static Map<String, List<ParamJson>> getProductMaps() {
		return productMaps;
	}

	public static void setProductMaps(Map<String, List<ParamJson>> productMaps) {
		MessagePropertie.productMaps = productMaps;
	}
}
