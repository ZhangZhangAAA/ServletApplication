package com.zc.service;

import com.zc.db.CommonDao;
import com.zc.pojo.User;

public class UserService {

    private CommonDao commonDao;
    private static final String MYBATIS_NS = "com.zc.mybatis.user.";

    public UserService(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    public User queryUser(User user) {
        User resultUser = commonDao.getSqlSession().selectOne(MYBATIS_NS + "queryOneUser", user);
        return resultUser;
    }

}