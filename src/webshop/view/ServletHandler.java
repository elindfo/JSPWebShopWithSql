package webshop.view;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class ServletHandler extends HttpServlet {

    private ArrayList<Iteminfo> items = new ArrayList<>();
    private HttpSession session = null;

    public ServletHandler() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        session = request.getSession();
        String action = request.getParameter("action");
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
            default:
                this.browse(request, response);
        }

    }

    /**
     * This method will initiate a search filtered by name, which is provided as a parameter "name"
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void searchByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO Database interaction and formating
        request.setAttribute("items", items);
        request.getRequestDispatcher("/content/browse.jsp").forward(request, response);
    }

    /**
     * This method will initiate a search filtered by category, which is provided as a parameter "category".
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void searchByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO Database interaction and formating
        request.setAttribute("items", items);
        request.getRequestDispatcher("/content/browse.jsp").forward(request, response);
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
        //request.getRequestDispatcher("/content/browse.jsp").forward(request, response);
    }

    /**
     * This method will redirect the customer to browse.jsp.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void browse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("items", items);
        request.getRequestDispatcher("/content/browse.jsp").forward(request, response);
    }

    /**
     * This method will redirect to shopping-cart.jsp if items is not null or empty.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void viewCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("items", items);
        request.getRequestDispatcher("/content/shopping-cart.jsp").forward(request, response);
    }

    /**
     * This method will empty the customers shoppingcart
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void emptyCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("items", items);
        request.getRequestDispatcher("/content/shopping-cart.jsp").forward(request, response);
    }
}
