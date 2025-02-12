<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="webshop.bl.Proxy" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${pageContext.request.contextPath}/webshop.css" rel="stylesheet" type="text/css">
<%
    if(request.getSession().getAttribute("username") == null){
        response.sendRedirect("login.jsp");
    }
%>
<html>

<title>Webshop</title>

<head></head>

<body>
<div class="topnav">
    <a href="ControllerServlet?action=browse">Browse</a>
    <a href="ControllerServlet?action=viewCart">Shopping Cart</a>
    <a href="ControllerServlet?action=logout">Logout</a>
    <%
        if(request.getSession().getAttribute("currentOrderHandled") != null){
    %>
            <a href="ControllerServlet?action=packOrder">Pack Order</a>
    <%
        }
    %>
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

    <div>
        <form action="ControllerServlet?action=getOrder" method="POST">
            <select name="oid">
                <%
                    int[] orders = Proxy.getOrderIds();
                    for (int oid : orders) {
                        if(Boolean.parseBoolean((Proxy.getOrder(oid)).get(0).get("packed")) == false){
                %>
                <option name="oid" value=<%=String.valueOf(oid)%>><%out.print(oid);%></option>
                <%
                        }
                    }
                %>
            </select>
            <button class="my-button" value="Search" type="submit">GO</button>
        </form>
    </div>
    <%
        if(request.getSession().getAttribute("currentOrderHandled") != null)
        {
    %>
    <div class="table">
        <table id="table">
            <tr>
                <th>Order</th>
                <th>Item Id</th>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Category</th>
                <th>Remove</th>
                <th>All</th>
            </tr>
            <%
                List<HashMap<String, String>> items = (List<HashMap<String, String>>)request.getSession().getAttribute("currentOrderHandled");
                for(int i = 0; i < items.size(); i++) {
            %>

            <tr>
                <td><%=items.get(i).get("oid")%></td>
                <td><%=items.get(i).get("itemId")%></td>
                <td><%=items.get(i).get("name")%></td>
                <td><%=items.get(i).get("price")%></td>
                <td><%=items.get(i).get("quantity")%></td>
                <td style="min-width:150px"><%=items.get(i).get("category")%></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <%
        }
        else{
            out.print(session.getAttribute("handleordersInfo"));
        }
    %>

</div>
</body>
</html>