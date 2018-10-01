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
<html>
<head>
    <title>Webshop</title>
</head>
<body>

<div class="topnav">
    <a href="login.jsp">logout</a>
    <a href="home.jsp">Cart</a>
</div>

<div class="content">
    <%="What you entered"%><br>
    <p style="color: aquamarine">
        <jsp:getProperty name="userinfo" property="username"/>
    </p><br>

    <% String username = "jocke";
        if (username.equals(request.getParameter("username"))) {
            out.println("You have entered correct username, Welcome!...");
        } else {
            out.println("invalid username");
        }%>
    <div class="table">
        <table>
            <tr>
                <td>
                    <h6>Item id</h6>
                </td>
                <td>
                    <h6>Name</h6>
                </td>
                <td>
                    <h6>Price</h6>
                </td>
            </tr>
        </table>
    </div>
</div>

<div class="footer">
    <p>Erik & Jocke</p>
</div>

</body>
</html>



