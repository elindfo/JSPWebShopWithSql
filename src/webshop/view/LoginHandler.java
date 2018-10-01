package webshop.bl.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginHandler extends HttpServlet {

    public LoginHandler(){
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username.isEmpty() || password.isEmpty()){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
            requestDispatcher.forward(request,response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.jsp");
            requestDispatcher.forward(request,response);
        }

    }
}
