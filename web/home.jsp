<%--
  Created by IntelliJ IDEA.
  User: jgranb
  Date: 2018-10-01
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="webshop.css" rel="stylesheet" type="text/css">
<jsp:useBean id="userinfo" class="webshop.view.LoginBean"></jsp:useBean>
<jsp:setProperty name="userinfo" property="*"></jsp:setProperty>

</head>
<body>

<div class="topnav">
    <a href="#">login</a>
    <a href="#">logout</a>
    <a href="#">Cart</a>
</div>

<div class="content">
    <%="Details entered in loginform"%><br>
    <jsp:getProperty name="userinfo" property="username"/><br>
    <jsp:getProperty name="userinfo" property="password"/><br>
</div>

<div class="footer">
    <p>Erik & Jocke</p>
</div>

</body>
</html>



