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
        <a href="login">login</a>

    </div>

    <div class="content">
        <form>
            <input type="search" placeholder="Search" style="background-color: white" formaction="servlethandler?action=searchByNames">
            <button formaction="servlethandler?action=searchByName">search</button>
        </form>
        <div class="color-animation-welcome-text">Welcome!</div>
    </div>

    <div class="footer">
        <p>Erik & Jocke</p>
        <%=date%>
    </div>
</body>
</html>
