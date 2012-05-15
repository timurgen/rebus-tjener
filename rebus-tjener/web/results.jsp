<%-- 
    Document   : results
    Created on : 15.mai.2012, 11:36:16
    Author     : 490501
--%>
<%
String wrongMessage = null;
long gameId;
if(request.getParameter("gameid") == null) {
    wrongMessage = "Game id is not specified";
}
else {
    gameId = Long.valueOf(request.getParameter("gameid"));
}
    


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
        <%@include file="footer.jsp" %>
        
    </body>
</html>
