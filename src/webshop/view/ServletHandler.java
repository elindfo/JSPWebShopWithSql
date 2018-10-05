package webshop.view;

import webshop.bl.Item;
import webshop.bl.Proxy;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ServletHandler extends HttpServlet {

    private ArrayList<HashMap<String, String>> items;

    public ServletHandler() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null || action.isEmpty()){
            action = (String) request.getAttribute("action");
        }
        items = new ArrayList<>();
        switch (action) {
            case "searchByName": {
                this.searchByName(request, response);
                break;
            }
            case "searchByCategory": {
                this.searchByCategory(request, response);
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
            case "username": {
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

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = null;
        String password = null;
        if (!request.getAttribute("username").equals("") || !request.getAttribute("password").equals("")) {
            browse(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
            requestDispatcher.forward(request, response);
            /*RequestDispatcher requestDispatcher = request.getRequestDispatcher("browse.jsp");
            requestDispatcher.forward(request,response);*/
        }
    }

    /**
     * This method will initiate a search filtered by name, which is provided as a parameter "name"
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void searchByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO Database interaction and formatin
        request.setAttribute("items", items);
        request.getRequestDispatcher("browse.jsp").forward(request, response);
    }

    /**
     * This method will initiate a search filtered by category, which is provided as a parameter "category".
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void searchByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO Database interaction and formating
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
        items = new ArrayList<>();
        items = (ArrayList<HashMap<String, String>>) Proxy.findAllItems();
        //request.removeAttribute("items");
        //items = (ArrayList<Item>) Proxy.findAllItems();
        request.getSession().setAttribute("items", items);
        //Item item = new Item(1,"Fullkorn", 25.5,2,Item.Category.SPORTS);
        //items.add(item);
        //request.setAttribute("items", items);
        request.getRequestDispatcher("browse.jsp").forward(request, response);
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("action", "login");
        doGet(request, response);
    }


}
