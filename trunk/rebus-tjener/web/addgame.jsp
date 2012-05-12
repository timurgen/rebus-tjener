<%-- 
    Document   : addgame
    Created on : May 2, 2012, 7:37:31 PM
    Author     : 490501
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
        <link type="text/css" rel="stylesheet" href="main.css" />
        <script type="text/javascript" src="main.js"></script>
        <!-- Copyright (c) 2003 TengYong Ng -->
        <script type="text/javascript" src="datetimepicker.js"></script>
        <!--  -->
        <title>MegaRebus add new game</title>
        <script type="text/javascript"
            src="http://maps.googleapis.com/maps/api/js?key=AIzaSyA9zgSiW2FxR4J66TviLfa1RO4c14fLUEY&sensor=false"></script>
    </head>
    <body onload="initialize()">
        <%@include file='menu.jsp'%>
        <% if(session.getAttribute("username") == null) { %>
            <div class="wrong_credentials"><h3>You have to be logged in to add new games</h3></div>
        <% } else {%>
        <div class="wrong_message" id="wrngMsg"></div>
        <div class="create_game_form">
            <form action="addgame" id="addGameFormId" name="add_game_form" onsubmit="return saveGame()" method="post" >
                Name of game: <input class="addGame" type="text" id="gameNameId" name="gameName" /> 
                Varighet min: <select id="gameVarighetId" name="gameVarighet">
                    <% for(int i = 15; i < 180; i++) {
                        out.println("<option>"+i+"</option>");
                    } %>
                </select>
                <input type="Text" id="gameStartDateId" maxlength="25" size="25" name="startDate"><a href="javascript:NewCal('gameStartDateId','ddmmyyyy',true,24)"><img src="images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
                Open for all:  
                <input type="checkbox" id="gameIsOpenBoxId" name="gameIsOpenBox" checked="false">
                <input type="submit" value="Save game" />
            </form>
            <table border="0">
                <tr>
            <td>
        <div id="map_canvas"></div></td>
        <td valign="top">
        <table id="points" width="350px" border="1">
            <tr>
                <td>number</td>
                <td>name</td>
                <td>text</td>
                <td>radius</td>
            </tr>
        </table>
            <br />
        </td>
                </tr>
            </table>
        </div>
        <% } %>
        <%@include file="footer.jsp" %>
    </body>
</html>
