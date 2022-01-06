package servlets;

import com.google.gson.Gson;
import engineClasses.Move;
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
public class MovesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            Gson gson = new Gson();
            String username= SessionUtils.getUsername(req);
            User user=ServletUtils.getEngine(getServletContext()).getUsers().getUserByName(username);
            Collection<Move> moves = user.getMoves();
            String json = gson.toJson(moves);
            out.println(json);
            out.flush();
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String Samount=req.getParameter("amountToLoad");
        int amount=Integer.parseInt(Samount);
        String userName=SessionUtils.getUsername(req);
        User user = ServletUtils.getEngine(getServletContext()).getUsers().getUserByName(userName);
        synchronized (getServletContext()) {
            user.addMove(1, amount, null);
        }
    }
}

