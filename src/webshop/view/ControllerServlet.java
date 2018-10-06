package webshop.view;

import webshop.bl.Item;
import webshop.bl.Proxy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {

    private ArrayList<HashMap<String, String>> items;

    public ControllerServlet() {
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
            case "findByCategory": {
                this.findByCategory(request, response);
                break;
            }
            case "addToCart": {
                this.addToCart(request, response);
                break;
            }
            case "removeFromCart": {
                this.removeFromCart(request, response);
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


    private void findByCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Item.Category category = Item.convertStringToCategory(request.getParameter("category"));
        ArrayList<HashMap<String, String>> items = (ArrayList<HashMap<String, String>>) Proxy.findByCategory(category);
        request.getSession().setAttribute("items", items);
        response.sendRedirect("browse.jsp");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(Proxy.tryLogin((String)request.getSession().getAttribute("username"), (String)request.getSession().getAttribute("password"))){
            request.getSession().setAttribute("cart", new ArrayList<HashMap<String, String>>());
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
        request.getSession().invalidate();
        response.sendRedirect("index.jsp");
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
    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<HashMap<String, String>> cart = ((ArrayList<HashMap<String, String>>)(request.getSession().getAttribute("cart")));
        List<HashMap<String, String>> items = ((ArrayList<HashMap<String, String>>)(request.getSession().getAttribute("items")));

        String itemId = request.getParameter("iid");

        boolean hasItem = false;

        for(HashMap<String, String> cartItem : cart){
            if(cartItem.get("itemId").equals(itemId)){
                cartItem.replace("quantity", String.valueOf(Integer.parseInt(cartItem.get("quantity")) + 1));
                hasItem = true;
                break;
            }
        }

        if(!hasItem){
            for(HashMap<String, String> item : items){
                if(item.get("itemId").equals(itemId)){
                    HashMap<String, String> newItem = new HashMap<>();
                    newItem.put("itemId", item.get("itemId"));
                    newItem.put("name", item.get("name"));
                    newItem.put("price", item.get("price"));
                    newItem.put("quantity", "1");
                    newItem.put("category", item.get("category"));
                    cart.add(newItem);
                    break;
                }
            }
        }

        response.sendRedirect("browse.jsp");
    }

    private void removeFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<HashMap<String, String>> cart = ((ArrayList<HashMap<String, String>>)(request.getSession().getAttribute("cart")));

        String itemId = request.getParameter("iid");

        boolean toBeRemoved = false;
        int indexToBeRemoved = -1;

        for(HashMap<String, String> cartItem : cart){
            if(cartItem.get("itemId").equals(itemId)){
                int qty = Integer.parseInt(cartItem.get("quantity"));
                if(qty <= 1){
                    indexToBeRemoved = cart.indexOf(cartItem);
                    toBeRemoved = true;
                    break;
                }
                cartItem.replace("quantity", String.valueOf(qty - 1));
                break;
            }
        }

        if(toBeRemoved){
            cart.remove(indexToBeRemoved);
        }
        response.sendRedirect("shopping-cart.jsp");
    }

    /**
     * This method will redirect the customer to browse.jsp.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void browse(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
