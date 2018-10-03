<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="${pageContext.request.contextPath}/webshop.css" rel="stylesheet" type="text/css">
<html>
<head>
    <title>Webshop</title>
</head>
<body>

<div class="topnav">
    <a href="servlethandler?action=browse">Home</a>
    <a href="servlethandler?action=viewCart">Cart</a>
    <a href="logout">Logout</a>
    <puser><%out.println(session.getAttribute("username"));%></puser>
    <puser>User: </puser>
</div>

<div class="content">

    <div class="search">

    </div>
    <div class="table">
        <table>
            <tr>
                <th>
                    <h6>Item</h6>
                </th>
                <th>
                    <h6>Name</h6>
                </th>
                <th>
                    <h6>Price</h6>
                </th>
            </tr>
            <% for (int i = 1; i < 50; i=i+3) {%>
            <tr>
                <td>
                    <%--load Itemnumber--%>
                    <%=i%>
                </td>
                <td>
                    <%--load name from ItemInfo--%>
                    <%--<%=itemlist.get(i).getName()%>--%>
                    <%=(i+1)%>
                </td>
                <td>
                    <%--load price from ItemInfo--%>
                    <%=(i+2)%>
                </td>
                <td>
                    <a class="my-button">buy</a>
                </td>
            </tr>
            <%}%>
        </table>
    </div>
</div>

<div class="footer">
    <p>Erik & Jocke</p>
</div>

</body>
</html>



