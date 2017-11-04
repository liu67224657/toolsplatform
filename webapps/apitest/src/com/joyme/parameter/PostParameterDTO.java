package com.joyme.parameter;

import com.joyme.util.PostParameter;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-10-11
 * Time: 下午1:47
 * To change this template use File | Settings | File Templates.
 */
public class PostParameterDTO implements Serializable {
	private String url;
	private PostParameter postParameter[];

	public PostParameter[] getPostParameter() {
		return postParameter;
	}

	public void setPostParameter(PostParameter[] postParameter) {
		this.postParameter = postParameter;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
