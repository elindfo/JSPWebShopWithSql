<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${pageContext.request.contextPath}/webshop.css" rel="stylesheet" type="text/css">
<%
    if(request.getSession().getAttribute("username") == null){
        response.sendRedirect("login.jsp");
    }
%>
<body>
    <div class="topnav">
        <a href="ServletHandler?action=browse">Browse</a>
        <a href="ServletHandler?action=viewCart">ShoppingCart</a>
        <a href="ServletHandler?action=logout">Logout</a>
        <puser>
            <%
                out.println(session.getAttribute("username"));
            %>
        </puser>
        <puser>User: </puser>
    </div>

    <div class="content"></div>

    <div class="footer">
        <p>Erik & Jocke</p>
    </div>

</body>
</html>