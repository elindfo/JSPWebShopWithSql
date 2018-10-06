<%@ page import="java.util.ArrayList" %>
<%@ page import="webshop.view.ItemInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${pageContext.request.contextPath}/webshop.css" rel="stylesheet" type="text/css">

</head>
<body>

    <div class="topnav">
        <a href="servlethandler?action=browse">Home</a>
        <a href="servlethandler?action=viewCart">Cart</a>
        <a href="servlethandler?action=logout">Logout</a>
        <puser><%out.println(session.getAttribute("username"));%></puser>
        <puser>User: </puser>
    </div>

    <div class="content"></div>

    <div class="footer">
        <p>Erik & Jocke</p>
    </div>

</body>
</html>