<%-- 
    Document   : join.jsp
    Created on : May 4, 2012, 9:45:53 PM
    Author     : obu
--%>

<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="db.UserDBAdapter"%>
<%@page import="db.GameDBAdapter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String wrongMessage = null;
    String name = (String)session.getAttribute("username");
    long gameId = Long.valueOf(request.getParameter("gameid"));
    GameDBAdapter gdb = new GameDBAdapter();
    UserDBAdapter udb = new UserDBAdapter();
    try {
        //add as guest om det er lov
        if(name == null) {
            String id = session.getId();
            wrongMessage = "your id is:<font color=\"red\"> " + id.substring(0, 8) + "</font> use it to begin game on your mobile phone";
        }
        else {
            gdb.addPartisipantToGame(gameId, udb.getUserByName(name).getId());
            wrongMessage = "User added successfully";           
        }

        
    }
    catch(Exception e) {
        wrongMessage = e.getMessage();
        //gdb.closeConnection();
    }
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="main.css" />
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file='menu.jsp'%>
        <% if(wrongMessage != null) { %>
        <div class="join_list"><h5><% out.print(wrongMessage); %></h5>
        <% } %>
        <% 
        ArrayList<Long> list = gdb.getGameById(gameId).getAllPartisipants();
        out.println("Start date:" + new Date(gdb.getGameById(gameId).getStartDate()));
        out.println("<p>Registred users</p><table class=\"userlist\"><th>nr.</th><th>Name</th>");
            for(int i = 0; i < list.size(); i++) {
                out.println("<tr>");
                out.println("<td>");
                out.println(i+1);
                out.println("</td>");
                out.println("<td>");
                out.println(udb.getUserById(list.get(i)).getName());
                out.println("</tr>");
            }
        out.println("</table>");
        %>
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>
