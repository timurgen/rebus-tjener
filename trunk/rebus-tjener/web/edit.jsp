<%-- 
    Document   : edit
    Created on : May 4, 2012, 7:25:11 PM
    Author     : 490501
--%>

<%@page import="java.util.Date"%>
<%@page import="db.Game"%>
<%@page import="db.UserDBAdapter"%>
<%@page import="db.GameDBAdapter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%

    if(request.getParameter("gameid")== null) {
        response.sendRedirect("wrong.jsp?message=\"Game id is not set\"");
        return;
    } 
    long gameId = Long.valueOf(request.getParameter("gameid")); // id the game to editing 
    String userName = (String)session.getAttribute("username"); //username
    //sjekker om bruker er eier og kan redigere det 
    GameDBAdapter gdb = new GameDBAdapter();
    UserDBAdapter udb = new UserDBAdapter();
    Game gm = gdb.getGameById(gameId);
    if(!gm.getAuthorName().equals(userName)){
        response.sendRedirect("wrong.jsp?message=\"You have no right to do it\"");
        return;
    }
    if(System.currentTimeMillis() > gm.getStartDate()) {
        response.sendRedirect("wrong.jsp?message=\"Too late to do it\"");
        return;
    }



%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="main.css" />
        <script type="text/javascript" src="main.js"></script>
        <!-- Copyright (c) 2003 TengYong Ng -->
        <script type="text/javascript" src="datetimepicker.js"></script>
        <title>MegaRebus, edit game</title>
    </head>
    <body>
        <%@include file= "menu.jsp" %>
        <%--выводим информацию о игре --%>
        <div class="editgame">
        <form action="edit.jsp" method="post" name="formEdit" id="formEditId" >
            <table> 
                <tr>
                    <td>
                        <p>Name:</p> 
                    </td>
                    <td>
                        <input class="login" type="text" name="textName" id="textNameId" value=<%="\""+gm.getName()+"\""%>/><br/>
                    </td>
                </tr>
                <% Date d = new Date(gm.getStartDate()); %>
                <tr>
                    <td>
                        <p>Start date:</p>
                    </td>
                    <td>
                        <input class="login" type="Text" id="gameStartDateId" maxlength="25" size="25" name="startDate" value=<%="\""+d.toString()+"\""%> /><a href="javascript:NewCal('gameStartDateId','ddmmmyyyy',true,24)"><img src="images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
                    </td>
                </tr>
                <tr>
                    <td>
                        Varighet:
                    </td>
                    <td>
                        <select id="gameVarighetId" name="gameVarighet">
                        <% for(int i = 15; i < 180; i++) {
                            out.println("<option>"+i+"</option>");
                        } %>
                </select>
                    </td>
                </tr>
            </table>
                <input type="submit" value="Save" />
        </form>
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>

