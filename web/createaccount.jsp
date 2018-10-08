<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--Login page - if session is new render login-form otherwise message about session should render--%>
<link href="${pageContext.request.contextPath}/webshop.css" rel="stylesheet" type="text/css">

<html>

<title>Webshop</title>

<head></head>

<body>

<div class="topnav">
    <a href="index.jsp">Home</a>
    <a href="login.jsp">Login</a>
</div>

<%
    if(session.getAttribute("loggedIn") == Boolean.TRUE){
        response.sendRedirect("ControllerServlet?action=browse");
    }
    else{
%>

<div class="content">
    <form action="ControllerServlet?action=createAccount" method="post">
        Username:<br>
        <input type="text" name="username" style="background-color: whitesmoke"><br>
        Password:<br>
        <input type="password" name="password" style="background-color: whitesmoke"><br><br>
        <input type="submit" name="login" value="Create Account" style="background-color: whitesmoke">
        <%if(session.getAttribute("createAccountInfo") != null) out.print(session.getAttribute("createAccountInfo"));%>
    </form>
</div>

<%
    }
%>
</body>
</html>