<%--
  Created by IntelliJ IDEA.
  User: jgranb
  Date: 2018-10-02
  Time: 00:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if(username.isEmpty() || password.isEmpty()){
        response.sendRedirect("login.jsp");
    } else {
        response.sendRedirect("home.jsp");
    }
%>