package com.enjoyf.webapps.joyme.webpage.controller;

import com.enjoyf.webapps.joyme.webpage.Constants;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-8-15 下午6:11
 * Description:
 */
public abstract class BaseController {

    private static final String EXCEL_PATH = "excel";

    protected String getUploadPath() {
        return Constants.BASE_UPLOAD_PATH + "/" + EXCEL_PATH;
    }
}
