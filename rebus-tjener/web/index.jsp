<%-- 
    Document   : index
    Created on : Apr 26, 2012, 9:25:50 AM
    Author     : 490501
    Version    : 1.0.0
--%>

<%@page import="db.GamePunkt"%>
<%@page import="java.util.Date"%>
<%@page import="db.Game"%>
<%@page import="java.util.List"%>
<%@page import="db.GameDBAdapter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  //setter opp forbindelse med database
    GameDBAdapter gameDB = null;
    try {
        gameDB = new GameDBAdapter();
    }
    catch(Exception e) {
        log(e.getMessage());
        e.printStackTrace();
    }
    
    //test
        //Game g = new Game("test","name", 90,false, "29/04/2012 13:15:00");
        //GamePunkt gg = new GamePunkt(47.5645,125.4565,50,"name 5", "text of mega rebus");
        //g.addPoint(gg);   
        //gg = new GamePunkt(48.5645,125.4565,50,"name 6", "text of mega rebus");
        //g.addPoint(gg);
        //gameDB.persistGame(g);
        //gameDB.addPointToGameInDB(gg, 1);
    //plukker ut spilllist
    List<Game> gameList = gameDB.getAllGames();
    //gameDB.closeConnection();
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="main.css" />
        <title>MegaRebus</title>
    </head>
    <body>
        <%@include file='menu.jsp'%>
        <!--Viser list med alle spill som er registrert på systemmet-->
        <% if(gameList.size() > 0) {
            out.println("<table class=\"gamelist\">"); //table header
            out.println("<tr>");
            out.println("<th>name of game</th>");
            out.println("<th>Start time</th>");
            out.println("<th>varighet</th>");
            out.println("<th>Join</th>");
            out.println("</tr>");
            for(int i = 0; i < gameList.size(); i++) {
                    //tabell innhold
                    out.println("<tr>");
                        out.println("<td>");
                            out.println(gameList.get(i).getName());
                        out.println("</td>");
                        out.println("<td>");
                        Date d = new Date(gameList.get(i).getStartDate());
                            out.println(d);
                        out.println("</td>");
                        out.println("<td>");
                            out.println(gameList.get(i).getVarighet());
                        out.println("</td>");
                        //dersom løp satt i gang
                        if(gameList.get(i).getStartDate() < System.currentTimeMillis() & System.currentTimeMillis() < gameList.get(i).getStartDate() + gameList.get(i).getVarighet()*60000) {
                            out.println("<td>");
                            out.println("<div class=\"join-button-2\">Game in progress</div>");
                            out.println("</td>");
                        }
                        //dersom løp avsluttet
                        else if(System.currentTimeMillis() > gameList.get(i).getStartDate() + gameList.get(i).getVarighet()*60000) {
                            out.println("<td>");
                            out.println("<a href=\"results.jsp?gameid="+gameList.get(i).getId()+"\"><div class=\"join-button-2\">Game finished</div></a>");
                            out.println("</td>");
                        }
                        //dersom løp kun til registrerte brukere
                        else if(gameList.get(i).isIsOpen() == false & session.getAttribute("username")== null ) {
                        //om brukeren er ikke pålogget og spill bare for de som er registrert so viser tilsvarende melding
                            out.println("<td>");
                                long gameId = gameList.get(i).getId();
                                //html button
                                //out.print("<input type=\"submit\" onclick=\"gotoLogin()\" value=\"Sign up/in to join\" />");
                                //css button
                                out.println("<a href=\"login.jsp\"><div class=\"join-button-2\">Sign in &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;to join</div></a>"); 
                            out.println("</td>");      
                        }
                        //dersom  allerede deltar i løp
                        else if(gameList.get(i).getAllPartisipants() != null && gameList.get(i).getAllPartisipants().contains(session.getAttribute("userid"))) {
                            long gameId = gameList.get(i).getId();
                            out.println("<td>");
                            out.println("<a href=join.jsp?gameid="+gameId+ "><div class=\"join-button-2\">Joined</div></a>");
                            out.println("</td>");
                        }
                        //dersom eier av løp
                        else if(session.getAttribute("username") != null & gameList.get(i).getAuthorName().equals(session.getAttribute("username"))) {
                            out.println("<td>");
                                long gameId = gameList.get(i).getId();
                                //med vanlige html button
                                //out.print("<input type=\"submit\" onclick=\"joinGame(");
                                //out.print(gameId + ")\" value=\"Join\" />");
                                //med pure css button
                                out.println("<a href=\"edit.jsp?gameid="+gameId+ "\"><div class=\"join-button\">Edit or Delete</div></a>");
                                
                            out.println("</td>"); 
                        }
                        
                        else {
                            out.println("<td>");
                                long gameId = gameList.get(i).getId();
                                //med vanlige html button
                                //out.print("<input type=\"submit\" onclick=\"joinGame(");
                                //out.print(gameId + ")\" value=\"Join\" />");
                                //med pure css button
                                out.println("<a href=join.jsp?gameid="+gameId+ "><div class=\"join-button\">Join</div></a>");
                            out.println("</td>");                            
                        }                                               
                    out.println("</tr>");
                }   
            out.println("</table>");
        }
       else {
        %>
        <div class="no_game_message">
            <h1>No one game created, be first!</h1>
        </div>
        <%
       }
        %>
        <%@include file="footer.jsp" %>
    </body>
</html>
