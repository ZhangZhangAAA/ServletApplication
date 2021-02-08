package com.zc.db;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zc.exception.TransactionException;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class CommonDao {

    private SqlSession sqlSession;

    public CommonDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public SqlSession getSqlSession() {
        return this.sqlSession;
    }

    public void commit() throws TransactionException {
        this.sqlSession.commit();
    }

    public void rollback() {

        this.sqlSession.commit();
    }

    public PageInfo<?> selectListWithPage(String sqlId, int pageNum, int pageSize, boolean count, Object param) {
        PageHelper.startPage(pageNum, pageSize, count);
        List<?> list = this.sqlSession.selectList(sqlId, param);
        return new PageInfo<>(list);
    }

    public PageInfo<?> selectListWithPage(String sqlId, int pageNum, int pageSize, Object param) {
        return selectListWithPage(sqlId, pageNum, pageSize, false, param);
    }


}
