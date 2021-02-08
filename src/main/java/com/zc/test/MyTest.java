package com.zc.test;

import com.zc.util.FileUtil;
import org.junit.Test;

import java.util.Set;

public class MyTest {

    @Test
    public void test01() {
        Set<String> stringSet = FileUtil.matchefilePath("mybatis/*-mybatis.xml");
        for (String s : stringSet) {
            System.out.println(s);
        }
    }

}
