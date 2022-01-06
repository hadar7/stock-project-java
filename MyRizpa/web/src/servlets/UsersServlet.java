package servlets;

import com.google.gson.Gson;
import engineClasses.Item;
import engineClasses.Stocks;
import engineClasses.User;
import engineClasses.Users;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class UsersServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            Users users = ServletUtils.getEngine(getServletContext()).getUsers();
            Collection<User> values = users.getUsers().values();
            String json = gson.toJson(values);
            out.println(json);
            out.flush();
        }
    }

    protected void processRequest2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        int amount = 0;
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String userName = SessionUtils.getUsername(request);
            User user = ServletUtils.getEngine(getServletContext()).getUsers().getUserByName(userName);
            Item item = user.getItemByName(request.getParameter("name"));
            if (item != null) {
                amount = item.getQuantity();
            }
            String json = gson.toJson(amount);
            out.println(json);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String company = req.getParameter("company");
        String symbol = req.getParameter("symbol");
           String amountS = req.getParameter("amount");
           int amount = Integer.parseInt(amountS);
           String valueS = req.getParameter("value");
           int value = Integer.parseInt(valueS);
           int price = value / amount;
           String username = SessionUtils.getUsername(req);
           User user = ServletUtils.getEngine(getServletContext()).getUsers().getUserByName(username);
           synchronized (getServletContext()) {
               try {
                   ServletUtils.getEngine(getServletContext()).getStocks().addNewStock(symbol, company, price);
                   user.addNewHolding(symbol, amount);
               } catch (Exception e) {
                   resp.setStatus(401);
                   resp.getOutputStream().println(e.getMessage());
               }
           }
       }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String Stype=req.getParameter("type");
        int type;
        if (Stype==null) {
            type=0;
        }
        else
            type=Integer.parseInt(Stype);
          if (type==2)
            processRequest2(req, resp);
          else processRequest(req, resp);
    }
}
