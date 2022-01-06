<%--
  Created by IntelliJ IDEA.
  User: 97250
  Date: 02/07/2021
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<%@page import="utils.*" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>RSE wrold</title>
    <!--        Link the Bootstrap (from twitter) CSS framework in order to use its classes-->
    <!--        Link jQuery JavaScript library in order to use the $ (jQuery) method-->
    <!--        <script src="script/jquery-2.0.3.min.js"></script>-->
    <!--        and\or any other scripts you might need to operate the JSP file behind the scene once it arrives to the client-->
</head>
<body>
<div class="container">
    <% String usernameFromSession = SessionUtils.getUsername(request);%>
    <% String usernameFromInput = request.getParameter("username") != null ? request.getParameter("username") : "";%>
    <% if (usernameFromSession == null) {%>
    <h1>Welcome to the RSE world</h1>
    <br/>
    <h2>Please enter a unique user name:</h2>
    <form method="GET" action="">
        <input type="text" name="<%="username"%>" value="<%=usernameFromInput%>"/>
        <input type="submit" value="Login"/>
        <br>
        <input type="checkbox" id="isAdmin" name="isAdmin" value="isAdmin">
        <label for="isAdmin"> Is Admin ?</label><br>
    </form>
    <% Object errorMessage = request.getAttribute("usernameError");%>
    <% if (errorMessage != null) {%>
    <span class="danger" style="color:red;"><%=errorMessage%></span>
    <% } %>
    <% } else {%>
    <h1>Welcome back, <%=usernameFromSession%></h1>
    <a href="infoFile.html">Click here to enter the engineClasses.chat room</a>
    <br/>
    <a href="?logout=true" id="logout">logout</a>
    <% }%>
</div>
</body>
</html>