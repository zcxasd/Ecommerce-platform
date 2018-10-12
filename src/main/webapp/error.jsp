<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/4
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>${ex.msg}</h1>
    <span id="warn">${ex.warn}</span>
<script>
    window.onload=function () {
        var counter = 3;

        window.setInterval(function () {
            counter--;
            if (counter==0){
                window.location.href="${ex.url}";
                // 结束setInterval方法
                window.clearInterval();
            }
            document.getElementById("warn").innerHTML=counter+"s后页面跳转";
        },1000);

    }
</script>
</body>
</html>
