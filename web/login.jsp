<%--
  Created by IntelliJ IDEA.
  User: jgranb
  Date: 2018-10-01
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="webshop.css" rel="stylesheet" type="text/css">
<html>
<title>Webshop</title>
<head>
</head>
<body>

<div class="topnav">
    <a href="#">login</a>
    <a href="#">logout</a>
    <a href="#">Link</a>
</div>

<div class="content">
    <form action="redirectLogin.jsp" method="post">
        Username:<br>
        <input type="text" name="username" style="background-color: darkgray"><br>
        Password:<br>
        <input type="password" name="password" style="background-color: darkgray"><br><br>
        <input type="submit" name="login" style="background-color: darkgray">
    </form>
</div>

<div class="footer">
    <p>Erik & Jocke</p>
</div>

</body>
</html>