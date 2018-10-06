<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--Login page - if session is new render login-form otherwise message about session should render--%>
<link href="${pageContext.request.contextPath}/webshop.css" rel="stylesheet" type="text/css">

<html>

<title>Webshop</title>

<head></head>

<body>

    <div class="topnav">
        <a href="servlethandler?action=logout">back</a>
    </div>

    <div class="content">
        <form action="loginservlet" method="post">
            Username:<br>
            <input type="text" name="username" style="background-color: whitesmoke"><br>
            Password:<br>
            <input type="password" name="password" style="background-color: whitesmoke"><br><br>
            <input type="submit" name="login" value="logga in" style="background-color: whitesmoke">
            <%if(request.getAttribute("failedLoginMessage") != null){%>
                <%out.print(request.getAttribute("failedLoginMessage"));%>
            <%}%>
        </form>
    </div>

    <div class="content">
        <%--<p>You are already logged in as <%out.println(session.getAttribute("username"));%></p><br>--%>
        <a class="my-button" href="servlethandler?action=browse">Proceed to webshop</a>
        <a href="browse.jsp">heje</a>

    </div>

    <div class="footer">
        <p>Erik & Jocke</p>
    </div>
</body>
</html>