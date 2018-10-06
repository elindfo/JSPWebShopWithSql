<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="webshop.bl.Proxy" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="webshop.css" rel="stylesheet" type="text/css">
<html>
<head>
    <title>Webshop</title>
</head>

<body>
    <div class="topnav">
        <a href="servlethandler?action=browse">Home</a>
        <a href="servlethandler?action=viewCart">Cart</a>
        <a href="servlethandler?action=logout">Logout</a>
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
                    <th>
                        <h6>Category</h6>
                    </th>
                </tr>

                <%
                    List<HashMap<String, String>> items = (List<HashMap<String, String>>)request.getAttribute("items");
                    for(int i = 0; i < items.size(); i++) {
                %>

                <tr>
                    <td>
                        <%----%>
                        <%=items.get(i).get("itemId")%>
                    </td>
                    <td>
                        <%--load name from ItemInfo--%>
                        <%--<%=itemlist.get(i).getName()%>--%>
                        <%=items.get(i).get("name")%>
                    </td>
                    <td>
                                    <%--load price from ItemInfo--%>
                        <%=items.get(i).get("price")%>
                    </td>
                    <td>
                                    <%--load price from ItemInfo--%>
                        <%=items.get(i).get("quantity")%>
                    </td>
                    <td>
                        <a class="my-button" href="servlethandler?action=addToCart">buy</a>
                    </td>
                </tr>
                    <%}
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



