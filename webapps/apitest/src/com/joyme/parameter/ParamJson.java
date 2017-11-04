package com.joyme.parameter;

import com.joyme.util.JsonBinder;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-9-9
 * Time: 下午5:29
 * To change this template use File | Settings | File Templates.
 */
public class ParamJson implements Serializable {
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static String toJson(ParamJson accountVirtualHeadIcon) {
		return JsonBinder.buildNonDefaultBinder().toJson(accountVirtualHeadIcon);
	}

	public static ParamJson fromJson(String jsonString) {
		ParamJson param = new ParamJson();
		try {
			if (!StringUtils.isEmpty(jsonString)) {
				param = JsonBinder.buildNonNullBinder().getMapper().readValue(jsonString, new TypeReference<ParamJson>() {
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return param;
	}

	public String toJson() {
		return JsonBinder.buildNonNullBinder().toJson(this);
	}



	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public static void main(String[] args) {
		String json="[{'key':'huangbiao','value':15},{'key':'liumei','value':14}]";
		JSONArray jsonarray = JSONArray.fromObject(json);
		System.out.println(jsonarray);
		List list = (List)JSONArray.toCollection(jsonarray, ParamJson.class);
		Iterator it = list.iterator();
		while(it.hasNext()){
			ParamJson p = (ParamJson)it.next();
			System.out.println(p.getKey()+"---"+p.getValue());
		}
	}
}
