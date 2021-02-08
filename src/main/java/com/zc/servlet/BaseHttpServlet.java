package com.zc.servlet;

import com.zc.db.CommonDao;
import org.apache.ibatis.session.SqlSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseHttpServlet extends HttpServlet {

    public CommonDao commonDao;

    @Override
    public void init() {
        SqlSession sqlSession = (SqlSession) getServletContext().getAttribute("sqlSession");
        this.commonDao = new CommonDao(sqlSession);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doResponse(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doResponse(req, resp);
    }

    abstract void doResponse(HttpServletRequest req, HttpServletResponse resp);
}
