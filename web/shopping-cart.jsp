<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${pageContext.request.contextPath}/webshop.css" rel="stylesheet" type="text/css">
<%
    if(request.getSession().getAttribute("username") == null){
        response.sendRedirect("login.jsp");
    }
%>
<body>
    <div class="topnav">
        <a href="ControllerServlet?action=browse">Browse</a>
        <a href="ControllerServlet?action=viewCart">ShoppingCart</a>
        <a href="ControllerServlet?action=logout">Logout</a>
        <a href="ControllerServlet?action=emptyCart">Empty Cart</a>
        <a href="ControllerServlet?action=placeOrder">Place Order</a>

        <%
            int level = Integer.parseInt((String) session.getAttribute("ulevel"));
            if (level == 2) {
        %>
        <a href="ControllerServlet?action=handleOrders" style="display: inline; float: right">Handle Orders</a>
        <%
            }
            if (level == 3) {
        %>
        <a href="ControllerServlet?action=administration" style="display: inline; float: right; background-color: darkgray">Administration</a>
        <%
            }
        %>
        <puser><%=request.getSession().getAttribute("username")%></puser>
        <puser>User:</puser>
    </div>

    <div class="content" align="center">
        <div style="height: 50px"></div>
        <div class="table">
            <table id="table">
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Category</th>
                    <th>Remove</th>
                    <th>All</th>

                </tr>

                <%
                    List<HashMap<String, String>> items = (List<HashMap<String, String>>)request.getSession().getAttribute("cart");
                    for(int i = 0; i < items.size(); i++) {
                %>

                <tr>
                    <td><%=items.get(i).get("itemId")%></td>
                    <td><%=items.get(i).get("name")%></td>
                    <td><%=items.get(i).get("price")%></td>
                    <td><%=items.get(i).get("quantity")%></td>
                    <td style="min-width:150px"><%=items.get(i).get("category")%></td>
                    <td><a class="my-button" href="ControllerServlet?action=removeFromCart&iid=<%=items.get(i).get("itemId")%>">-</a></td>
                    <td><a class="my-button" href="ControllerServlet?action=removeAllFromCart&iid=<%=items.get(i).get("itemId")%>">X</a></td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>

    </div>

    <div class="footer">
        <p>Erik & Jocke</p>
    </div>

</body>
</html>