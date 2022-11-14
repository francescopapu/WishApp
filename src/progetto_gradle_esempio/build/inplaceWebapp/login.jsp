<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=request.getContextPath() %>/css/style.css" rel="stylesheet" type="text/css">

<title>Login</title>
</head>

<body>
<div id="finestra" align="center">
<%  HttpSession sessione = request.getSession();
	String error = (String)sessione.getAttribute("error");
    if(error!=null){%>

<p><%=error %></p><%} %>
<h2>Login</h2>


<div id="form-login">
<table>

   <form action="<%=request.getContextPath() %>/accedi" method="post" >
     <tr>&nbsp;</tr>
     <tr>
        <td><b>username</b></td>
        <td><input type="text" name="username"></td>
     </tr>

     <tr>
        <td><b>password</b></td>
        <td><input type="password" name="password"></td>
     </tr>
          
     <tr><td align="center"><input type="submit" value="login"></td></tr>
   </form>
   
</table>
</div>


</div>
</body>
</html>