<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--Login page - if session is new render login-form otherwise message about session should render--%>
<link href="${pageContext.request.contextPath}/webshop.css" rel="stylesheet" type="text/css">

<html>

<title>Webshop</title>

<head></head>

<body>

    <div class="topnav">
        <a href="index.jsp">Home</a>
        <a href="createaccount.jsp">Sign Up</a>
    </div>

    <%
        if(session.getAttribute("loggedIn") == Boolean.TRUE){
            response.sendRedirect("ControllerServlet?action=browse");
        }
        else if(request.getParameter("username") != null || request.getParameter("password") != null){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            response.sendRedirect("ControllerServlet?action=login");
        }
        else{
    %>

    <div class="content" align="center" >
        <form action="login.jsp" method="post">
            Username:<br>
            <input type="text" name="username" style="background-color: whitesmoke"><br>
            Password:<br>
            <input type="password" name="password" style="background-color: whitesmoke"><br><br>
            <input type="submit" name="login" value="LogIn" style="background-color: whitesmoke">
        </form>
    </div>

    <%
        }
    %>











    <div class="footer">
        <p>Erik & Jocke</p>
    </div>
</body>
</html>