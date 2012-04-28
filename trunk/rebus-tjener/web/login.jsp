<%-- 
    Document   : login
    Created on : Apr 28, 2012, 9:12:25 AM
    Author     : obu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    String wrongMessage = request.getParameter("wrongmessage");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="main.css" />
        <title>Login Page</title>
    </head>
    <body>
        <%@include file='menu.jsp'%>
        <div class="login_area">
            
            <div class="login_box">
                <form action="login?mode=login" method="post" name="login_form">
                
                    <% if(wrongMessage != null) { %>
                    <div class="wrong_message" id="msg_wrong"><center><% out.print(wrongMessage); %> </center></div>
                    <% } %>
                
                    <label name="lbl_name" id="id_lbl_name">Username:</label>
                    <input class="login" type="text" name="name"/><br />
                    <label name="lbl_pass" id="id_lbl_pass">Password:</label>
                    <input class="login" type="password" name="pass"/><br/>
                    <input type="submit" value="Log in" name="btn_login" />
                    <a href="login?mode=register">Sign up</a>
                </form>
            </div>
        </div>
        
        <%@include file="footer.jsp" %>
        
    </body>
</html>
