package webshop.view;

import webshop.bl.Proxy;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/ServletHandler")
public class ServletHandler extends HttpServlet {

    private ArrayList<HashMap<String, String>> items;

    public ServletHandler() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = (String)request.getAttribute("action");
        if(action == null){
            action = request.getParameter("action");
        }
        items = new ArrayList<>();
        switch (action) {
            case "findByName": {
                this.findByName(request, response);
                break;
            }
            case "addToCart": {
                this.addToCart(request, response);
                break;
            }
            case "browse": {
                this.browse(request, response);
                break;
            }
            case "viewCart": {
                this.viewCart(request, response);
                break;
            }
            case "emptyCart": {
                this.emptyCart(request, response);
                break;
            }
            case "login": {
                this.login(request, response);
                break;
            }
            case "logout": {
                this.logout(request, response);
                break;
            }
            default:
                this.browse(request, response);
        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(Proxy.tryLogin((String)request.getSession().getAttribute("username"), (String)request.getSession().getAttribute("password"))){
            request.getSession().setAttribute("loggedIn", Boolean.TRUE);
        }
        else{
            request.getSession().setAttribute("loggedIn", Boolean.FALSE);
        }
        request.getSession().setAttribute("password", null);
        response.sendRedirect("login.jsp");
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("loggedIn", Boolean.FALSE);
        request.getSession().setAttribute("username", null);
        request.getSession().setAttribute("password", null);
        response.sendRedirect("login.jsp");
    }

    /**
     * This method will initiate a search filtered by name, which is provided as a parameter "name"
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void findByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO Database interaction and formatin
        request.setAttribute("items", items);
        request.getRequestDispatcher("browse.jsp").forward(request, response);
    }

    /**
     * This method will check if an item is in stock and add it to the customers cart. If item is not available the customer needs to be notified in some way.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO Database interaction and formating create interface to db? proxy yes.. that's right proxy
        /*
         * ItemInfo item = new ItemInfo();
         * if(proxy.getItem(request.getParameter("item").getQuantity() > 0){
         *
         * items.add(item);
         * request.setAttribute("items", items);
         * }*/

        request.setAttribute("items", items);
        request.getRequestDispatcher("browse.jsp").forward(request, response);
    }

    /**
     * This method will redirect the customer to browse.jsp.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void browse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("items", Proxy.findAllItems());
        response.sendRedirect("browse.jsp");
    }

    /**
     * This method will redirect to shopping-cart.jsp if items is not null or empty.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void viewCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("items", items);
        request.getRequestDispatcher("shopping-cart.jsp").forward(request, response);
    }

    /**
     * This method will empty the customers shoppingcart
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void emptyCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("items", items);
        request.getRequestDispatcher("shopping-cart.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}
