package servlets;

import com.google.gson.Gson;
import dtos.StockDto;
import engineClasses.Stock;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class OneStockServlet extends HttpServlet {
    private final String STOCK_URL = "../oneStock/stock.html";
    private final String ADMIN_STOCK_URL = "../oneStock/stockForAdmin.html";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String stockname=req.getParameter("stockname");

        req.getSession(false).setAttribute("stock",stockname);
        boolean isAdmin= (boolean) req.getSession().getAttribute("isAdmin");
        if (!isAdmin)
            resp.sendRedirect(STOCK_URL);//TO SECOND PAGE
        else    resp.sendRedirect(ADMIN_STOCK_URL);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String stockname= (String) req.getSession().getAttribute("stock");
        StockDto thisStock= null;
        try {
            thisStock = ServletUtils.getEngine(getServletContext()).showStockDto(stockname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (PrintWriter out = resp.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(thisStock);
            out.println(json);
            out.flush();
        }
    }
}
