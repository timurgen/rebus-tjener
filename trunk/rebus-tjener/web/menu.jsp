<%-- 
    Document   : menu
    Created on : Apr 27, 2012, 9:06:16 PM
    Author     : 490501
--%>

<%

%>

<div class="menu_main">
    <div class="menu_element"><a href="">Main page</a></div>
    <div class="menu_element"><a href="">About</a></div>
    <%
    if(session.getAttribute("username") != null) {
    %>
    <div class="menu_element"><a href="">Log out</a></div>
    
    <%
       } else {
    %>
    <div class="menu_element"><a href="">Sign in</a></div>
    <div class="menu_element"><a href="">Sign up</a></div>
    <%
       }
    %>    
    <hr>   
</div>