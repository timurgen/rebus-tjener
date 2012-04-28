<%-- 
    Document   : register
    Created on : Apr 28, 2012, 12:42:41 PM
    Author     : 490501
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
        <div class="register_area">
            
            <div class="register_box">
                <form action="login?mode=login" method="post" name="login_form">
                
                    <% if(wrongMessage != null) { %>
                    <div class="wrong_message" id="msg_wrong"><center><% out.print(wrongMessage); %> </center></div>
                    <% } %>
                    <label name="lbl_name" id="id_lbl_name">Username:</label>
                    <input class="register" type="text" name="name"/><br />
                    <label name="lbl_pass" id="id_lbl_pass">Password:</label>
                    <input class="register" type="password" name="pass"/><br/>
                    <label name="lbl_pass" id="id_lbl_pass">Retype:&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <input class="register" type="password" name="pass2"/><br/>
                    <input type="submit" value="Send" name="btn_login" />
                </form>
            </div>
        </div>
        
        <%@include file="footer.jsp" %>
        
    </body>
</html>