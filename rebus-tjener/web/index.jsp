<%-- 
    Document   : index
    Created on : Apr 26, 2012, 9:25:50 AM
    Author     : 490501
--%>

<%@page import="db.GamePunkt"%>
<%@page import="java.util.Date"%>
<%@page import="db.Game"%>
<%@page import="java.util.List"%>
<%@page import="db.GameDBAdapter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  //setter opp forbindelse med database
    GameDBAdapter gameDB = new GameDBAdapter();
    //test
        Game g = new Game("name", 90,true, new Date(888888));
        //GamePunkt gg = new GamePunkt(47.5645,125.4565,50,"name 5", "text of mega rebus");
        //g.addPoint(gg);   
        //gg = new GamePunkt(48.5645,125.4565,50,"name 6", "text of mega rebus");
        //g.addPoint(gg);
        //gameDB.persistGame(g);
        //gameDB.addPointToGameInDB(gg, 1);
    //plukker ut spilllist
    List<Game> gameList = gameDB.getAllGames();
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
        <% if(gameList.size() > 0) {
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>name of game</th>");
            out.println("<th>Start time</th>");
            out.println("<th>varighet</th>");
            out.println("<th>Join</th>");
            out.println("</tr>");
            for(int i = 0; i < gameList.size(); i++) {

                    out.println("<tr>");
                        out.println("<td>");
                            out.println(gameList.get(i).getName());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(gameList.get(i).getStartDate());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(gameList.get(i).getVarighet());
                        out.println("</td>");
                        if(gameList.get(i).isIsOpen() == false & session.getAttribute("username")== null ) {
                        //om brukeren er ikke pålogget og spill bare for de som er registrert so viser ikke
                            out.println("<td>");
                                long gameId = gameList.get(i).getId();
                                out.print("<input type=\"submit\" onclick=\"gotoLogin()\" value=\"Sign up/in to join\" />");
                            out.println("</td>");      
                        }
                        else {
                            out.println("<td>");
                                long gameId = gameList.get(i).getId();
                                out.print("<input type=\"submit\" onclick=\"joinGame(");
                                out.print(gameId + ")\" value=\"Join\" />");
                            out.println("</td>");                            
                        }
                                                                       
                    out.println("</tr>");
                }   
            
            out.println("</table>");
        }
       else {
        out.println("a vot her tebe ");
       }
        %>
    </body>
    
</html>
