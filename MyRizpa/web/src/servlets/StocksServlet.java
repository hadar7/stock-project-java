package servlets;

import com.google.gson.Gson;
import dtos.AddDto;
import dtos.StockDto;
import engineClasses.Stock;
import engineClasses.Stocks;
import engineClasses.User;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;



public class StocksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            Gson gson = new Gson();
            Stocks stocks = ServletUtils.getEngine(getServletContext()).getStocks();
            Collection<Stock> values = stocks.getAllStocks().values();
            String json = gson.toJson(values);
                out.println(json);
                out.flush();
        }
    }
    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AddDto res = null;
        int type = 0;
        String Sdirection=req.getParameter("toggle");
        int direction;
        if (Sdirection.equals("Buy")) {
            direction=1;
        }
        else direction=0;
        String Samout=req.getParameter("amount");
        int amount=Integer.parseInt(Samout);
        String stockname= (String) req.getSession().getAttribute("stock");
        StockDto stock= null;
        try {
            stock = ServletUtils.getEngine(getServletContext()).showStockDto(stockname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String name=stock.getSymbol();
        String username= SessionUtils.getUsername(req);
        User user=ServletUtils.getEngine(getServletContext()).getUsers().getUserByName(username);
        synchronized (getServletContext()) {
            if (direction == 0 && user.howMuchAvaliableForSell(stock) < amount) {
                resp.setStatus(401);
                resp.getOutputStream().println("You dont have enough from this stock");
            } else {
                String Stype = req.getParameter("toggle1");

                if (Stype.equals("LMT"))
                    type = 0;
                else if (Stype.equals("MKT"))
                    type = 1;
                else if (Stype.equals("IOC"))
                    type = 2;
                else type = 3;//FOK
                String Slimit = req.getParameter("limit");
                int limit;
                if (Slimit == null)
                    limit = -1;
                else limit = Integer.parseInt(Slimit);
                try {
                    res = ServletUtils.getEngine(getServletContext()).addNewOrdinance(direction, name, amount, limit, type, username);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
            assert res != null;
            if (res.getTransactionDtoList().size()==0 && (type==3 || type==2))
            {
                resp.setStatus(401);
                resp.getOutputStream().println("The command not made at all");
            }
        }
    }

