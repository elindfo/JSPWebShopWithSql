package webshop.view;

import webshop.bl.Item;
import webshop.bl.Proxy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class FindbyCategoryServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Item.Category category = Item.convertStringToCategory(request.getParameter("category"));
        ArrayList<HashMap<String, String>> items = (ArrayList<HashMap<String, String>>) Proxy.findByCategory(category);
        request.getSession().setAttribute("items", items);
        request.getRequestDispatcher("browse.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
