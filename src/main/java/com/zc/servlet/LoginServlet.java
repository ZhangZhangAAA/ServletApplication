package com.zc.servlet;

import com.alibaba.fastjson.JSON;
import com.zc.pojo.User;
import com.zc.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet({"/login"})
public class LoginServlet extends BaseHttpServlet {

    private UserService userService;

    @Override
    void doResponse(HttpServletRequest req, HttpServletResponse resp) {
        HashMap<String, String> resultMap = new HashMap<>();
        String userCode = req.getParameter("userCode");
        String password = req.getParameter("password");
        User user = new User();
        user.setUserCode(userCode);
        user.setPassword(password);
        userService = new UserService(commonDao);
        User resultUser = userService.queryUser(user);
        if (resultUser == null) {
            resultMap.put("retCode", "300");
            resultMap.put("retMessage", "用户名或密码不正确！");
        } else {
            resultMap.put("retCode", "200");
            resultMap.put("retMessage", "success");
        }

        resp.reset();
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            String jsonStr = JSON.toJSONString(resultMap);
            writer.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
}
