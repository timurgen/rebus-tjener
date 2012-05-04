<%-- 
    Document   : test
    Created on : May 4, 2012, 3:11:02 PM
    Author     : obu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
  <form name="myForm" method="get">
     <button type="submit" onclick="submitForm(); return false">ButtonLabel</button>
  </form>

<script type="text/javascript">
function submitForm()
{
   var form = document.forms['myForm'];
   // form.action = 'put your url here';
   var el = document.createElement("input");
   el.type = "hidden";
   el.name = "myHiddenField";
   el.value = "myValue";
   form.appendChild(el);
   //form.submit();
}
</script>
</body>
</html>
