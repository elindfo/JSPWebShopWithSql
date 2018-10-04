<%@ page import="webshop.bl.Item" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
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
            <input type="search" placeholder="Search" style="background-color: white">
        </div>

        <div class="table">
            <table>
                <tr>
                    <th>
                        <h6>Id</h6>
                    </th>
                    <th>
                        <h6>Name</h6>
                    </th>
                    <th>
                        <h6>Price</h6>
                    </th>
                    <th>
                        <h6>Quantity</h6>
                    </th>
                </tr>

                <%
                    List<Item> items = (List<Item>) request.getAttribute("items");
                    for(Item item : items) {
                %>

                <tr>
                    <td>
                        <%----%>
                        <%=item.getId()%>
                    </td>
                    <td>
                        <%--load name from ItemInfo--%>
                        <%--<%=itemlist.get(i).getName()%>--%>
                        <%=item.getName()%>
                    </td>
                    <td>
                                    <%--load price from ItemInfo--%>

                        <%=item.getPrice()%>
                    </td>
                    <td>
                                    <%--load price from ItemInfo--%>
                        <%=item.getQty()%>
                    </td>
                    <td>
                        <a class="my-button" href="servlethandler?action=addToCart">buy</a>
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



