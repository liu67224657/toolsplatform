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
public class ParamJsonV2 implements Serializable {
	private String key;

	private String parentkey;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getParentkey() {
		return parentkey;
	}

	public void setParentkey(String parentkey) {
		this.parentkey = parentkey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static String toJson(ParamJsonV2 accountVirtualHeadIcon) {
		return JsonBinder.buildNonDefaultBinder().toJson(accountVirtualHeadIcon);
	}

	public static ParamJsonV2 fromJson(String jsonString) {
		ParamJsonV2 param = new ParamJsonV2();
		try {
			if (!StringUtils.isEmpty(jsonString)) {
				param = JsonBinder.buildNonNullBinder().getMapper().readValue(jsonString, new TypeReference<ParamJsonV2>() {
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


}
