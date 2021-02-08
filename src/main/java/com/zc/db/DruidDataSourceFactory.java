package com.zc.db;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

public class DruidDataSourceFactory implements DataSourceFactory {

    private static final Logger log = Logger.getLogger(DruidDataSourceFactory.class);
    private Properties properties;
    private DruidDataSource druidDataSource;

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public DataSource getDataSource() {

        druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(this.properties.getProperty("driver"));
        druidDataSource.setUrl(this.properties.getProperty("url"));
        druidDataSource.setUsername(this.properties.getProperty("username"));
        druidDataSource.setPassword(this.properties.getProperty("password"));
        setOtherProperties();
        try {
            druidDataSource.init();
        } catch (SQLException e) {
            log.error("druid数据库连接池初始化失败！", e);
        }
        return druidDataSource;
    }

    private void setOtherProperties() {

        String initialSize = this.properties.getProperty("initialSize");
        if (initialSize != null && !"".equals(initialSize.trim())) {
            druidDataSource.setInitialSize(Integer.valueOf(initialSize));
        }
        String maxActive = this.properties.getProperty("maxActive");
        if (maxActive != null && !"".equals(maxActive.trim())) {
            druidDataSource.setMaxActive(Integer.valueOf(maxActive));
        }
        String maxWait = this.properties.getProperty("maxWait");
        if (maxWait != null && !"".equals(maxWait.trim())) {
            druidDataSource.setMaxWait(Integer.valueOf(maxWait));
        }
        String keepAlive = this.properties.getProperty("keepAlive");
        if (keepAlive != null && !"".equals(keepAlive.trim())) {
            druidDataSource.setKeepAlive(Boolean.parseBoolean(keepAlive));
        }
    }


}
