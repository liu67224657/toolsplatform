package com.joyme.parameter;

import com.joyme.env.AlphaParam;
import com.joyme.env.BetaParam;
import com.joyme.env.ComParam;
import com.joyme.env.DevParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-9-28
 * Time: 下午3:29
 * To change this template use File | Settings | File Templates.
 */
public class ParamSngl {
	private static Map<String, Param> syncCodeMap = new HashMap<String, Param>();

	private static ParamSngl instance;

	public synchronized static ParamSngl get() {
		if (instance == null) {
			instance = new ParamSngl();
		}
		return instance;
	}

	private ParamSngl() {
		syncCodeMap.put("dev", new DevParam());
		syncCodeMap.put("alpha", new AlphaParam());
		syncCodeMap.put("beta", new BetaParam());
		syncCodeMap.put("com", new ComParam());
	}

	public Param getEnvImpl(String env) {
		return syncCodeMap.get(env);
	}
}
