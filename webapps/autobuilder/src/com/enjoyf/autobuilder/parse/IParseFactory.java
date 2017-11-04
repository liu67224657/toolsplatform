package com.enjoyf.autobuilder.parse;

import com.enjoyf.autobuilder.bean.AndroidParamBean;
import com.enjoyf.autobuilder.bean.BatchAndroidParamBean;
import com.enjoyf.autobuilder.bean.BatchIOSParamBean;
import com.enjoyf.autobuilder.bean.IOSParamBean;

public interface IParseFactory {
    //设置android参数
    void setAndroidParamBean(AndroidParamBean bean);

    //设置ios参数
    void setIOSParamBean(IOSParamBean bean);

    void setBatchAndroidParamBean(BatchAndroidParamBean bean);

    void setBatchIosParamBean(BatchIOSParamBean bean);

    //执行
    boolean execute() throws Exception;

}
