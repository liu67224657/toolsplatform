package com.joyme.env;

import com.joyme.parameter.Param;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-9-28
 * Time: 下午3:30
 * To change this template use File | Settings | File Templates.
 */
public class AlphaParam implements Param {
	private String uno = "456cded3-1f78-471d-a952-4f9b49e3acef";

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
