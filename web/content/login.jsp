<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--Login page - if session is new render login-form otherwise message about session should render--%>
<link href="${pageContext.request.contextPath}/webshop.css" rel="stylesheet" type="text/css">
<html>
<title>Webshop</title>
<head>
</head>
<body>

<div class="topnav">
    <a href="logout">back</a>
</div>
<%if(session.getAttribute("username") == null){%>
<div class="content">
    <form action="login" method="post">
        Username:<br>
        <input type="text" name="username" style="background-color: whitesmoke"><br>
        Password:<br>
        <input type="password" name="password" style="background-color: whitesmoke"><br><br>
        <input type="submit" name="login" style="background-color: whitesmoke">
    </form>
</div>
<%} else {%>
<div class="content">
    <p>You are already logged in as <%out.println(session.getAttribute("username"));%></p><br>
    <a class="my-button" href="servlethandler?action=browse">Proceed to webshop</a>
    <a href="browse.jsp"></a>
<%}%>
</div>
<div class="footer">
    <p>Erik & Jocke</p>
</div>

</body>
</html>