<%-- 
    Document   : results
    Created on : 15.mai.2012, 11:36:16
    Author     : 490501
--%>
<%@page import="db.Result"%>
<%@page import="java.util.ArrayList"%>
<%@page import="db.GameDBAdapter"%>
<%
String wrongMessage = null;
long gameId = 0;

if(request.getParameter("gameid") == null) {
    wrongMessage = "Game id is not specified";
}
else {
    gameId = Long.valueOf(request.getParameter("gameid"));
}
GameDBAdapter gdb = new GameDBAdapter();

ArrayList<Result> results = gdb.getGameById(gameId).getResults();    


%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="main.css" />
        <title>Results page</title>
    </head>
    <body>
        <%@include file='menu.jsp'%>
        <table>
        <%
            for(Result s : results) {
                out.println("<tr>");
                    out.println("<td>");
                        out.println(s.getGamerId());
                    out.println("</td>");
                    out.println("<td>");
                        out.println(s.getPoints());
                    out.println("</td>");
                out.println("</tr>");
            }
            
        %>
        </table>
        <%@include file="footer.jsp" %>
        
    </body>
</html>
