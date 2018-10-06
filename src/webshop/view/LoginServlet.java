package webshop.view;

import webshop.bl.Proxy;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    public LoginServlet(){
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");

        if (!username.isEmpty() && !password.isEmpty()) {
            if(Proxy.tryLogin(username, password)){
                request.setAttribute("items", Proxy.findAllItems());
                request.getRequestDispatcher("browse.jsp").forward(request, response);
            }
            else{
                request.setAttribute("failedLoginMessage", "Invalid username or password");
                requestDispatcher.forward(request, response);
            }
        } else {
            request.setAttribute("failedLoginMessage", "Empty field");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}
