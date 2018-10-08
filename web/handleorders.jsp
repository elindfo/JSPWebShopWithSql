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
    <a href="ControllerServlet?action=viewCart">ShoppingCart</a>
    <a href="ControllerServlet?action=logout">Logout</a>

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
        <form action="ControllerServlet?action=getAllOrders" method="POST">
            <select name="category">
                <%List<String> categories = Proxy.getOrders;%>
                <%for (String s : categories) {%>
                <option name="category" value=<%=s%>><%out.print(s);%></option>
                <%}%>
            </select>
            <button class="my-button" value="Search" type="submit">GO</button>
        </form>
    </div>
    <%
        if(request.getSession().getAttribute("orderRequest") != null)
        {%>
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
        } %>
        </table>
    </div>

</div>
</body>
</html>