<%@ page import="webshop.bl.Proxy" %>
<%@ page import="java.util.List" %>
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

<div class="content" align="center" >
    <form action="ControllerServlet?action=addItem" method="post">
        <input type="text" placeholder="Item name" name="itemName" style="background-color: whitesmoke"><br>
        <input type="text" placeholder="Quantity" name="itemQuantity" style="background-color: whitesmoke"><br>
        <input type="text" placeholder="Price" name="itemPrice" style="background-color: whitesmoke"><br>
        <select name="itemCategory">
            <%List<String> categories = Proxy.getCategories();%>
            <%for (String s : categories) {%>
            <option name="addItemCategory" value=<%=s%>><%out.print(s);%></option>
            <%}%>
        </select>
        <button class="my-button" value="AddItem" type="submit">Add Item</button><br><br>
        <%
            if(request.getSession().getAttribute("addItemStatus") != null)
                out.print(request.getSession().getAttribute("addItemStatus"));
        %>
    </form>
</div>

</body>
</html>