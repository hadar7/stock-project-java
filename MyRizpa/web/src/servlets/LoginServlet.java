package servlets;

import engineClasses.Engine;
import engineClasses.Users;
import utils.ServletUtils;
import utils.SessionUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class LoginServlet extends HttpServlet {

    private final String INFO_URL = "../info/trader2page.html";
    private final String INFO_URL_ADMIN = "../info/admin2page.html";
    private final String SIGN_UP_URL = "../pages/login/login.html";
    private final String LOGIN_ERROR_URL = "/pages/login/loginError.jsp";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String userNameFromSession = SessionUtils.getUsername(request);
        boolean isAdmin;
        String isAdminString = request.getParameter("isAdmin");
        if (isAdminString==null)
            isAdmin=false;
        else
            isAdmin=true;

        Engine myEngine= ServletUtils.getEngine(getServletContext());
        Users myUsers= myEngine.getUsers();
        if (userNameFromSession == null) {
            String usernameFromInput = request.getParameter("username");
            if (usernameFromInput == null || usernameFromInput.isEmpty()) {
                response.sendRedirect(SIGN_UP_URL);
            } else {
                usernameFromInput = usernameFromInput.trim();
                synchronized (this) {
                    if (myUsers.isUserExist(usernameFromInput)) {
                        String errorMessage = "Username " + usernameFromInput + " already exists. Please enter a different username.";
                        request.setAttribute("usernameError", errorMessage);
                        getServletContext().getRequestDispatcher(LOGIN_ERROR_URL).forward(request, response);
                    }
                    else {
                        if (myUsers.isAdminExist() && isAdmin) {
                            String errorMessage = " admin is already exists.";
                            request.setAttribute("usernameError", errorMessage);
                            getServletContext().getRequestDispatcher(LOGIN_ERROR_URL).forward(request, response);
                        }
                        else {

                            myUsers.addUser(usernameFromInput, isAdmin);
                            request.getSession(true).setAttribute("username", usernameFromInput);
                            request.getSession().setAttribute("isAdmin", isAdmin);
                            if (!isAdmin)
                                response.sendRedirect(INFO_URL);//TO SECOND PAGE
                            else    response.sendRedirect(INFO_URL_ADMIN);
                        }
                    }
                }
            }
        } else {
            response.sendRedirect(INFO_URL);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
