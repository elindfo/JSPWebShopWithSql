<%@ page import="java.util.Date" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${pageContext.request.contextPath}/webshop.css" rel="stylesheet" type="text/css">
<%Date date = new Date();%>
<html>
<head>
    <title>Webshop</title>
</head>
<body>
    <div class="topnav">
        <a href="login.jsp">Login</a>
    </div>
    <div class="content" align="center">
        <div class="color-animation-welcome-text">Welcome!</div>
    </div>

    <div class="footer">
        <p>Erik & Jocke</p>
        <%=date%>
    </div>
</body>
</html>
