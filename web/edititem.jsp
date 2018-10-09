<%@ page import="webshop.bl.Proxy" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${pageContext.request.contextPath}/webshop.css" rel="stylesheet" type="text/css">

<html>

<title>Webshop</title>

<head></head>

<body>

<div class="topnav">
    <a href="index.jsp">Home</a>
    <a href="createaccount.jsp">Sign Up</a>
</div>

<%
    HashMap<String, String> item = (HashMap<String, String>) session.getAttribute("item");
%>

<div class="content" align="center" >
    <form action="ControllerServlet?action=updateItem" method="post">
        <input type="text" placeholder="<%=item.get("name")%>" name="itemName" style="background-color: whitesmoke"><br>
        <input type="text" placeholder="<%=item.get("quantity")%>" name="itemQuantity" style="background-color: whitesmoke"><br>
        <input type="text" placeholder="<%=item.get("price")%>" name="itemPrice" style="background-color: whitesmoke"><br>
        <select name="itemCategory">
            <%List<String> categories = Proxy.getCategories();%>
            <%for (String s : categories) {%>
            <option <%if(s.equals(item.get("category"))){%>selected="<%out.print(item.get("category"));}%>" name="addItemCategory" value=<%=s%>><%out.print(s);%></option>
            <%}%>
        </select>
        <button class="my-button" value="UpdateItem" type="submit">Update Item</button><br><br>
        <%
            if(request.getSession().getAttribute("updateItemStatus") != null)
                out.print(request.getSession().getAttribute("updateItemStatus"));
        %>
    </form>
</div>

</body>
</html>