package com.joyme.env;

import com.joyme.parameter.Param;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-9-28
 * Time: 下午3:27
 * To change this template use File | Settings | File Templates.
 */
public class BetaParam implements Param {
	private String uno = "5bb377bb-6d5d-4a0e-a153-2bcf65b7e1c0";

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
