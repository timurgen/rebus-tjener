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
        <title>MegaRebus add new game</title>
        <script type="text/javascript"
            src="http://maps.googleapis.com/maps/api/js?key=AIzaSyA9zgSiW2FxR4J66TviLfa1RO4c14fLUEY&sensor=false"></script>
    </head>
    <body onload="initialize()">
        <%@include file='menu.jsp'%>
        <% if(session.getAttribute("username") == null) { %>
            <div class="wrong_credentials"><h3>You have to be logged in to add new games</h3></div>
        <% } else {%>
        
        <div class="create_game_form">
            <form action="addgame" name="add_game_form" method="post">
                Name of game: <input class="addGame" type="text" id="gameNameId" name="gameName" /> 
                Varighet : <input class="addGame" type="text" id="gameVarighetId" name="gameVarighet" /> 
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
