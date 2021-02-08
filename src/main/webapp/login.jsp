<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String contextPath = request.getContextPath();
    String rootPath = scheme + "//" + serverName + ":" + serverPort + contextPath;
%>
<html>
<head>
    <title>登录</title>
    <link href="favicon.ico" rel="shortcut icon"/>
    <link href="./css/bootstrap.min.css" rel="stylesheet">
    <script language="JavaScript" type="text/javascript" src="js/jquery-3.5.1.js"></script>
</head>
<body>
<body style=" background: url(http://global.bing.com/az/hprichbg/rb/RavenWolf_EN-US4433795745_1920x1080.jpg) no-repeat center center fixed; background-size: 100%;">
<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title text-center" id="myModalLabel">登录</h4>
        </div>
        <div class="modal-body" id="model-body">
            <div class="form-group">
                <input type="text" name="userCode" class="form-control" placeholder="用户名" autocomplete="off">
            </div>
            <div class="form-group">
                <input type="password" name="password" class="form-control" placeholder="密码" autocomplete="off">
            </div>
            <div style="height: 20px">
                <span id="errorTipMsg" class="text-danger"></span>
            </div>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" onclick="login()" class="btn btn-primary form-control">登录</button>
            </div>
            <div class="form-group">
                <button type="button" onclick="creat()" class="btn btn-default form-control1">注册</button>
            </div>

        </div>
    </div>
</div>

</body>
</body>
<script language="JavaScript" type="text/javascript">
    function login() {
        var userCode = $("input[name='userCode']").val();
        var password = $("input[name='password']").val();
        setErrorTip("");

        if (!(userCode && password)) {
            setErrorTip("用户名密码不能为空！");
            return;
        }

        var reqData = {
            userCode: userCode,
            password: password
        };

        $.ajax({
            url: "http://localhost:8085/login",
            async: false,
            type: "POST",
            data: reqData,
            dataType: "json",
            success: function (data) {
                var retCode = data.retCode;
                var retMessage = data.retMessage;
                if (retCode == "200") {
                    location.href = "http://localhost:8085/view/success.jsp";
                } else {
                    setErrorTip(retMessage);
                }
            },
            error: function (data) {
                console.log("ajax错误:" + data);
            }

        });

    }

    function setErrorTip(msg) {
        $("#errorTipMsg").text(msg);
    }

    function creat() {
            location.href="http://localhost:8085/view/creat.jsp"
    }





</script>
</html>
