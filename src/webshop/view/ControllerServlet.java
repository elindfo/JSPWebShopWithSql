package webshop.view;

import webshop.bl.Item;
import webshop.bl.Proxy;
import webshop.bl.UserAccount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            case "removeAllFromCart": {
                this.removeAllFromCart(request, response);
                break;
            }
            case "placeOrder": {
                this.placeOrder(request, response);
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
            case "administration": {
                this.administration(request, response);
                break;
            }
            case "handleOrders": {
                this.handleOrders(request, response);
                break;
            }
            case "getOrder": {
                this.getOrder(request, response);
                break;
            }
            case "createAccount": {
                this.createAccount(request, response);
                break;
            }
            case "removeUser": {
                this.removeUser(request, response);
                break;
            }
            default:
                this.browse(request, response);
        }

    }

    private void getOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int oid = Integer.parseInt(request.getParameter("oid"));
        request.getSession().setAttribute("currentOrderHandled", Proxy.getOrder(oid));
        response.sendRedirect("handleorders.jsp");
    }

    private void removeUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(Proxy.removeAccount(Integer.parseInt(request.getParameter("uid")), (String) request.getSession().getAttribute("ulevel"))){
            administration(request, response);
        } else {
            response.getWriter().println("Unable to remove Account");
            response.sendRedirect("browse.jsp");
        }
    }

    private void handleOrders(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("orderIds", Proxy.getOrderIds());
        response.sendRedirect("handleorders.jsp");
    }

    private void administration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<HashMap<String, String>> userAccounts = Proxy.findAllUsers();
        request.getSession().setAttribute("useraccounts", userAccounts);
        response.sendRedirect("administration.jsp");
    }

    private void createAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(Proxy.addUser(username, password, 1)){
            request.getSession().setAttribute("createAccountInfo", "Success! Account Created");
        }
        else{
            request.getSession().setAttribute("createAccountInfo", "Error! Account already exists");
        }
        response.sendRedirect("createaccount.jsp");
    }


    private void findByCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Item.Category category = Item.convertStringToCategory(request.getParameter("category"));
        ArrayList<HashMap<String, String>> items = (ArrayList<HashMap<String, String>>) Proxy.findByCategory(category);
        request.getSession().setAttribute("items", items);
        response.sendRedirect("browse.jsp");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(Proxy.tryLogin((String)request.getSession().getAttribute("username"), (String)request.getSession().getAttribute("password"))){
            String uid = String.valueOf(Proxy.getUserId((String) request.getSession().getAttribute("username")));
            String ulevel = String.valueOf(Proxy.getUserLevel(Integer.parseInt(uid)));
            request.getSession().setAttribute("uid", uid);
            request.getSession().setAttribute("ulevel", ulevel);
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
        request.getSession().invalidate();
        response.sendRedirect("index.jsp");
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

    private void placeOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = (String)request.getSession().getAttribute("uid");
        if(Proxy.placeOrder((ArrayList<HashMap<String, String>>) request.getSession().getAttribute("cart"), Integer.parseInt(uid))){
            emptyCart(request, response);
        }
        else{
            response.sendRedirect("shopping-cart.jsp");
        }
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

    private void removeAllFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<HashMap<String, String>> cart = ((ArrayList<HashMap<String, String>>)(request.getSession().getAttribute("cart")));

        String itemId = request.getParameter("iid");

        int indexToBeRemoved = -1;

        for(HashMap<String, String> cartItem : cart){
            if(cartItem.get("itemId").equals(itemId)){
                indexToBeRemoved = cart.indexOf(cartItem);
                break;
            }
        }

        cart.remove(indexToBeRemoved);

        response.sendRedirect("shopping-cart.jsp");
    }

    private void emptyCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<HashMap<String, String>> cart = ((ArrayList<HashMap<String, String>>)(request.getSession().getAttribute("cart")));
        cart.clear();
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
    private void viewCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("shopping-cart.jsp");
    }

    /**
     * This method will empty the customers shoppingcart
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}
