package webshop.view;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class ServletHandler extends HttpServlet {

    ArrayList<Iteminfo> products = new ArrayList<>();
    HttpSession session = null;

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
                this.browseRedirect(request, response);
                break;
            }
            case "viewCart": {
                this.viewCart(request, response);
                break;
            }
            default:
                this.shop(request, response);
        }
        //add products from search
        session.setAttribute("products", products);


        request.getRequestDispatcher("browseshop.jsp").forward(request, response);
    }

    private void viewCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", products);
        request.getRequestDispatcher("/content/shopping-cart.jsp").forward(request, response);
    }


    private void browseRedirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", products);
        request.getRequestDispatcher("/content/browse.jsp").forward(request, response);

    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO Database interaction and formating
        request.setAttribute("products", products);
        request.getRequestDispatcher("/content/browse.jsp").forward(request, response);
    }

    private void searchByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO Database interaction and formating
        request.setAttribute("products", products);
        request.getRequestDispatcher("/content/browse.jsp").forward(request, response);
    }

    private void searchByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO Database interaction and formating
        request.setAttribute("products", products);
        request.getRequestDispatcher("/content/browse.jsp").forward(request, response);
    }

    private void shop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", products);
        request.getRequestDispatcher("/content/browse.jsp").forward(request, response);
    }
}
