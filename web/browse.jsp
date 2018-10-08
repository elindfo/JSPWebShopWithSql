<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="webshop.bl.Proxy" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    if (request.getSession().getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    }
%>

<link href="webshop.css" rel="stylesheet" type="text/css">
<html>
<head>
    <title>Webshop</title>
</head>

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
    <a href="ControllerServlet?action=administration" style="display: inline; float: right; background-color: darkgray;" >Administration</a>
    <%
        }
    %>
    <puser><%=request.getSession().getAttribute("username")%></puser>
    <puser>User:</puser>

</div>

<div class="content" align="center">
    <div style="height: 50px; align-self: center">
        <form action="ControllerServlet?action=findByCategory" method="POST">
            <select name="category">
                <%List<String> categories = Proxy.getCategories();%>
                <%for (String s : categories) {%>
                <option name="category" value=<%=s%>><%out.print(s);%></option>
                <%}%>
            </select>
            <button class="my-button" value="Search" type="submit">GO</button>
        </form>
    </div>
    <div class="table">
        <table id="table">
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Price</th>
                <th>In stock</th>
                <th>Category</th>
                <th></th>
            </tr>
            </thead>

            <%
                List<HashMap<String, String>> items = (List<HashMap<String, String>>) request.getSession().getAttribute("items");
                for (int i = 0; i < items.size(); i++) {
            %>

            <tr>
                <td><%=items.get(i).get("itemId")%>
                </td>
                <td><%=items.get(i).get("name")%>
                </td>
                <td><%=items.get(i).get("price")%>
                </td>
                <td><%=items.get(i).get("quantity")%>
                </td>
                <td style="min-width:150px"><%=items.get(i).get("category")%>
                </td>
                <td><a class="my-button" name="BUY"
                       href="ControllerServlet?action=addToCart&iid=<%=items.get(i).get("itemId")%>">buy</a></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
</div>
</body>
</html>



