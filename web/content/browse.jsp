<%@ page import="java.util.ArrayList" %>
<%@ page import="webshop.bl.Item" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="${pageContext.request.contextPath}/webshop.css" rel="stylesheet" type="text/css">
<%
    ArrayList<Item> items = new ArrayList<>();
    items = (ArrayList<Item>) session.getAttribute("items");
%>
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
            <% for(Item i : items) {%>
            <tr>
                <td>
                    <%----%>
                    <%=i.getId()%>
                </td>
                <td>
                    <%--load name from ItemInfo--%>
                    <%--<%=itemlist.get(i).getName()%>--%>
                    <%=i.getName()%>
                </td>
                <td>
                    <%--load price from ItemInfo--%>

                    <%=i.getPrice()%>
                </td>
                <td>
                    <%--load price from ItemInfo--%>

                    <%=i.getQty()%>
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



