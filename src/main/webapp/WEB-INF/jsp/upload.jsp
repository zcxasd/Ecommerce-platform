<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/14
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--action没写等价于http://localhost:8080/BusinessProject/upload--%>
    <form action="" method="post" enctype="multipart/form-data">
        文件上传：<input type="file" name="upload">
        <br/>
        <input type="submit" value="submit"/>
    </form>
</body>
</html>
