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
        <puser>
            <%
                out.println(session.getAttribute("username"));
            %>
        </puser>
        <puser>User: </puser>
    </div>

    <div class="content">

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
                    List<HashMap<String, String>> items = (List<HashMap<String, String>>)request.getSession().getAttribute("cart");
                    out.print(items.size());
                    for(int i = 0; i < items.size(); i++) {
                %>

                <tr>
                    <td><%=items.get(i).get("itemId")%></td>
                    <td><%=items.get(i).get("name")%></td>
                    <td><%=items.get(i).get("price")%></td>
                    <td><%=items.get(i).get("quantity")%></td>
                    <td><%=items.get(i).get("category")%></td>
                    <td><a class="my-button" href="ControllerServlet?action=removeFromCart&iid=<%=items.get(i).get("itemId")%>">Remove</a></td>
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