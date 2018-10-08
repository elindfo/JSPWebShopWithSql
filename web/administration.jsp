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
        if(level == 2){
    %>
    <a href="ControllerServlet?action=administration">Handle Orders</a>
    <%
        }
        if(level == 3){
    %>
    <a href="ControllerServlet?action=administration">Administration</a>
    <%
        }
    %>
    <puser><%=request.getSession().getAttribute("username")%>
    </puser>
    <puser>User:</puser>
</div>

<div class="content" align="center">

    <div class="table">
        <table id="table">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Level</th>
                <th>Action</th>
                <th></th>
            </tr>

            <%
                List<HashMap<String, String>> userAccounts = (List<HashMap<String, String>>) request.getSession().getAttribute("useraccounts");
                for (int i = 0; i < userAccounts.size(); i++) {

            %>
                    <tr>
                        <td><%=userAccounts.get(i).get("uid")%></td>
                        <td><%=userAccounts.get(i).get("uname")%></td>
                        <td><%
                            int l = Integer.parseInt(userAccounts.get(i).get("ulevel"));
                            switch(l){
                                case 0: out.print("REMOVED"); break;
                                case 1: out.print("CUSTOMER"); break;
                                case 2: out.print("EMPLOYEE"); break;
                                case 3: out.print("ADMIN"); break;
                                default: out.print(l); break;
                            }
                        %></td>
                        <%if(!userAccounts.get(i).get("uid").equals(session.getAttribute("uid")) && !userAccounts.get(i).get("ulevel").equals("0")){%>
                            <td><a class="my-button" name="Remove"
                                   href="ControllerServlet?action=removeUser&uid=<%=userAccounts.get(i).get("uid")%>">Remove</a></td>
                        <%}%>
                     </tr>
            <%
                }
            %>
        </table>
    </div>
</div>
</body>
</html>



