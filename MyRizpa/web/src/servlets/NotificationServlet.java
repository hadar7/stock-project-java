package servlets;

import com.google.gson.Gson;
import engineClasses.Move;
import engineClasses.Notification;
import engineClasses.User;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotificationServlet  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            Gson gson = new Gson();
            List<String> res = new ArrayList<>();
            String username= SessionUtils.getUsername(req);
            User user= ServletUtils.getEngine(getServletContext()).getUsers().getUserByName(username);
            for (Notification n: user.getNotificationList()) {
                 res.add(n.getMessage());
            }
            String json = gson.toJson(res);
            user.cleanNotificationList();
            out.println(json);
            out.flush();
        }
    }

}
