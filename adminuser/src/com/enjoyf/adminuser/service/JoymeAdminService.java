package com.enjoyf.adminuser.service;

import java.util.List;

import com.enjoyf.adminuser.bean.JoymeUser;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.MD5Util;

public class JoymeAdminService {
    private static JoymeUserService joymeUserService = new JoymeUserService();
    private static JoymeUserPropertiesService joymeUserPropertiesService = new JoymeUserPropertiesService();

    /**
     * 根据用户名密码查找用户
     * 
     * @param userName
     * @param password
     *            明文密码
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public JoymeUser queryUserbyUserName(String userName, String password) throws JoymeDBException, JoymeServiceException {
        password = MD5Util.Md5(password);
        JoymeUser user = joymeUserService.queryJoymeUserByUserName(null, userName, password);
        if (user != null) {
            int userId = user.getUserId();
            List propertiesList = joymeUserPropertiesService.queryJoymeUserPropertiesByUserId(null, userId);
            user.setPropertiesList(propertiesList);
        }
        return user;
    }

    public boolean updateUserPassword(String userName, String password, String newPassword) throws JoymeDBException, JoymeServiceException {
        try {
            JoymeUser user = this.queryUserbyUserName(userName, password);
            if (user == null)
                return false;

            user.setUserPassword(MD5Util.Md5(newPassword));
            int i = joymeUserService.updateJoymeUser(null, user);
            return i == 1;
        } catch (Exception e) {
            return false;
        }
    }
}
