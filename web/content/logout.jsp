<%--
  Created by IntelliJ IDEA.
  User: jgranb
  Date: 2018-10-03
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session.invalidate();
    response.sendRedirect("index.jsp");
%>
