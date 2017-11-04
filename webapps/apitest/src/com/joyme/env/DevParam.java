package com.joyme.env;

import com.joyme.parameter.Param;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-9-28
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 */
public class DevParam implements Param {
	private String uno = "27c470b0-8835-4899-ac5a-9c507990d129";
	private String secret = "cc0003850f100b0007585ec81e00c006";
	private String appkey = "0VsYSLLsN8CrbBSMUOlLNx";

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
