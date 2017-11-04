package com.joyme.env;

import com.joyme.parameter.Param;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-9-28
 * Time: 下午3:27
 * To change this template use File | Settings | File Templates.
 */
public

class ComParam implements Param {
	private String uno = "157ab749-ee23-4871-92da-c18005ae0cbe";

	private String secret = "9c71c62e0d0f8041e50a63303482a1579bb4";

	private String appkey = "06aM10yEF6XrG8DgSQREFJ";

	@Override
	public String getUno() {
		return uno;
	}

	@Override
	public String getSecret() {
		return secret;
	}

	@Override
	public String getAppKey() {
		return appkey;
	}
}
