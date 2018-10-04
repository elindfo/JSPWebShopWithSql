package webshop.view;

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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/content/login.jsp");
        requestDispatcher.forward(request,response);
        //request.getRequestDispatcher("login.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");


        //Authenticate user with proxy

        if(username.isEmpty() || password.isEmpty()){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/content/login.jsp");
            request.getSession().setMaxInactiveInterval(1);
            requestDispatcher.forward(request,response);
        } else {
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("password", password);
            ServletHandler servletHandler = new ServletHandler();
            request.setAttribute("action", "browse");
            servletHandler.doGet(request,response);
            //RequestDispatcher requestDispatcher = request.getRequestDispatcher("/content/browse.jsp");
            //requestDispatcher.forward(request,response);
        }

    }
}
