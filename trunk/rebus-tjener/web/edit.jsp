<%-- 
    Document   : edit
    Created on : May 4, 2012, 7:25:11 PM
    Author     : 490501
--%>

<%@page import="db.Game"%>
<%@page import="db.UserDBAdapter"%>
<%@page import="db.GameDBAdapter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int gameId = Integer.valueOf(request.getParameter("gameid")); // id the game to editing 
    String userName = (String)session.getAttribute("username"); //username
    //sjekker om bruker er eier og kan redigere det 
    GameDBAdapter gdb = new GameDBAdapter();
    UserDBAdapter udb = new UserDBAdapter();
    Game gm = gdb.getGameById(gameId);
    if(gm.getAuthorName().equals(userName)){
        
    }
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="main.css" />
        <title>MegaRebus, edit game</title>
    </head>
    <%@include file= "menu.jsp" %>
    <%@include file="footer.jsp" %>
    <body>
        
    </body>
</html>
