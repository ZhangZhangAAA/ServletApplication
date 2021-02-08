package com.zc.listener;

import com.zc.db.MybatisBuilder;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DataSourceListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(DataSourceListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        MybatisBuilder mybatisBuilder;
        try {
            mybatisBuilder = new MybatisBuilder();
            mybatisBuilder.buildSqlSessionFactory();
            SqlSession sqlSession = mybatisBuilder.getSqlSession();
            context.setAttribute("sqlSession", sqlSession);
        } catch (Exception e) {
            log.error("myBatis初始化异常！", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
