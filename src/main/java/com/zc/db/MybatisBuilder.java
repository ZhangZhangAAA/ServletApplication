package com.zc.db;

import com.zc.util.FileUtil;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

public class MybatisBuilder {

    private static final Logger log = Logger.getLogger(MybatisBuilder.class);

    private SqlSessionFactory sqlSessionFactory;

    private Configuration configuration;

    private String mybatisConfPath;

    private String mapperLocation;


    public MybatisBuilder() {
        initParam();
        initConfiguration();
        buildXmlMapper();
    }


    private void initParam() {
        String propPath = "conf/application.properties";
        InputStream propIs = MybatisBuilder.class.getClassLoader().getResourceAsStream(propPath);
        Properties properties = new Properties();
        try {
            properties.load(propIs);
        } catch (IOException e) {
            log.error("系统配置文件加载异常！", e);
            throw new RuntimeException("系统配置文件加载异常！", e);
        }

        this.mybatisConfPath = properties.getProperty("mybatis-config-path");
        this.mapperLocation = properties.getProperty("mybatis-mapper-locations");
    }


    private void initConfiguration() {

        InputStream mybatisIs = MybatisBuilder.class.getClassLoader().getResourceAsStream(this.mybatisConfPath);
        XMLConfigBuilder configBuilder = new XMLConfigBuilder(mybatisIs);
        this.configuration = configBuilder.parse();
    }

    public void addMapper(String resourcePath) throws FileNotFoundException {
        buildXmlMapper(resourcePath);
    }

    public void buildXmlMapper() {
        Set<String> pathSet = FileUtil.matchefilePath(this.mapperLocation);
        try {
            this.buildXmlMapper(pathSet);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("myBtais文件不存在！", e);
        }
    }


    public void buildXmlMapper(String filePath) throws FileNotFoundException {
        InputStream xmlMapperInputStream = new FileInputStream(filePath);
        buildXmlMapper(xmlMapperInputStream, filePath);
    }

    private void buildXmlMapper(Collection<String> paths) throws FileNotFoundException {
        for (String path : paths) {
            InputStream xmlMapperInputStream = new FileInputStream(path);
            buildXmlMapper(xmlMapperInputStream, path);
        }
    }


    private void buildXmlMapper(InputStream xmlMapperInputStream, String mybatisMapperPath) {
        log.info("正在加载myBatis映射文件：" + mybatisMapperPath);
        XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(xmlMapperInputStream, this.configuration, mybatisMapperPath, this.configuration.getSqlFragments());
        mapperBuilder.parse();
    }


    public SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionFactory;
    }


    public SqlSession getSqlSession(boolean autoCommit) {
        if (sqlSessionFactory == null) {
            throw new RuntimeException("MybatisConfig初始化失败，mybatis会话工厂未创建！");
        }
        return this.sqlSessionFactory.openSession(autoCommit);
    }


    public SqlSession getSqlSession() {
        return this.getSqlSession(false);
    }

    public void buildSqlSessionFactory() {
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(this.configuration);

    }


}
