<%-- 
    Document   : menu
    Created on : Apr 27, 2012, 9:06:16 PM
    Author     : 490501
--%>

<%

%>

<div class="menu_main">
    <div class="menu_element"><a class="button" href="index.jsp"><span>Main page</span></a></div>
    <div class="menu_element"><a class="button" href="about.jsp"><span>About</span></a></div>
    <%
    if(session.getAttribute("username") != null) {
    %>
    <div class="menu_element"><a class="button" href="addgame.jsp"><span>Create game</span></a></div>
    <div class="menu_element"><a class="button" href="login?mode=logout"><span>Log out</span></a></div>
    <p class="logged_as">Logged as: <% out.println(session.getAttribute("username")); %></p>
    
    
    <%
       } else {
    %>
    <div class="menu_element"><a class="button" href="login.jsp"><span>Sign in</span></a></div>
    <div class="menu_element"><a class="button" href="register.jsp"><span>Sign up</span></a></div>
    <%
       }
    %>     
</div>