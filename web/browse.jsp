<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="webshop.bl.Proxy" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    if(request.getSession().getAttribute("username") == null){
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
        <puser><%=request.getSession().getAttribute("username")%></puser>
        <puser>User: </puser>
    </div>

    <div class="content">
        <form action="findByCategory" method="POST" style="padding-left: 100px">
            <select name="category">
                <%List<String> categories = Proxy.getCategories();%>
                <%for(String s : categories){%>
                <option name="category" value=<%=s%>><%out.print(s);%></option>
                <%}%>
            </select>
            <button class="my-button" value="Search" type="submit">GO</button>
        </form>

        <div class="table">
            <table>
                <tr>
                    <th><h6>Id</h6></th>
                    <th><h6>Name</h6></th>
                    <th><h6>Price</h6></th>
                    <th><h6>Quantity</h6></th>
                    <th><h6>Category</h6></th>
                </tr>

                <%
                    List<HashMap<String, String>> items = (List<HashMap<String, String>>)request.getSession().getAttribute("items");
                    for(int i = 0; i < items.size(); i++) {
                %>

                <tr>
                    <td><%=items.get(i).get("itemId")%></td>
                    <td><%=items.get(i).get("name")%></td>
                    <td><%=items.get(i).get("price")%></td>
                    <td><%=items.get(i).get("quantity")%></td>
                    <td><%=items.get(i).get("category")%></td>
                    <td><a class="my-button" href="ControllerServlet?action=addToCart&iid=<%=items.get(i).get("itemId")%>">BUY</a></td>
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



