package com.enjoyf.mcms.bean.temp;

import com.enjoyf.mcms.bean.json.JoymePointArchiveJsonBean;
import com.enjoyf.mcms.bean.json.JoymePointJsonBean;

import java.util.List;

public class PointArchiveListJsonBean {
    public JoymePointJsonBean module = null;
    public List<JoymePointArchiveJsonBean> list = null;

    public JoymePointJsonBean getModule() {
        return module;
    }

    public void setModule(JoymePointJsonBean module) {
        this.module = module;
    }

    public List<JoymePointArchiveJsonBean> getList() {
        return list;
    }

    public void setList(List<JoymePointArchiveJsonBean> list) {
        this.list = list;
    }
}
