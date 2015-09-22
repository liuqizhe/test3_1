<%--
  Created by IntelliJ IDEA.
  User: liuqizhe
  Date: 15/9/22
  Time: 下午2:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <font color="red">${error}</font>
    <form action="upload.from" method="post" enctype="multipart/form-data">
      <input type="file" name="file"/>
      <input type="submit" value="上传"/>
    </form>
</body>
</html>
