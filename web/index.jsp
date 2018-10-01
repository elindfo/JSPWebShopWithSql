<%@ page import="java.util.Date" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="webshop.css" rel="stylesheet" type="text/css">
<%Date date = new Date();%>
<html>
<head>
    <title>Webshop</title>
</head>
<body>

<div class="topnav">
    <a href="login.jsp">login</a>
    <a href="about:blank">logout</a>
    <a href="#">Cart</a>
</div>

<div class="content">
    <h1>WELCOME</h1>
</div>

<div class="footer">
    <p>Erik & Jocke</p>
    <%=date%>
</div>

</body>
</html>
