package servlets;

import engineClasses.Engine;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Collection;
import java.util.Scanner;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileLoadServlet  extends HttpServlet {

    private final String INFO_URL = "../info/trader2page.html";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //  response.setContentType("text/html");
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();


        Part part = request.getPart("fake-key-1");


        StringBuilder fileContent = new StringBuilder();
        // for (Part part : parts) {
        part.write("samplefile");
        fileContent.append(readFromInputStream(part.getInputStream()));
        if (fileContent.toString().equals("undefined")) {
            response.setStatus(401);
            out.println("there is no file loaded");
        } else {
            Engine engine = (Engine) getServletContext().getAttribute("Engine");
            String username = SessionUtils.getUsername(request);
            try {
                engine.loadDataFromXml(part.getInputStream(), username);
                // response.sendRedirect(INFO_URL);
            } catch (Exception e) {
                response.setStatus(401);
                out.println(e.getMessage());
                //` response.getOutputStream().println(e.getMessage());
            }
            //}
        }
    }
    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request,response);
    }
}