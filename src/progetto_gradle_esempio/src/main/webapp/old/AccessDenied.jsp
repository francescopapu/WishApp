<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/style.css" rel="stylesheet" type="text/css">
<title>Access denied</title>
</head>
<body>
<div id="finestra" align="center">
<h1>Accesso Negato!</h1>
<h2>Non sei autorizzato ad accedere a questa area</h2>
<h3><a href="<%=request.getContextPath()%>/pagine/home.jsp">Torna alla home</a></h3>
<h3><a href="<%=request.getContextPath()%>/logout.jsp">Logout</a></h3>
</div>
</body>
</html>