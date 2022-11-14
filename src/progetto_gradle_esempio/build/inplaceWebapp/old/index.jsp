<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/style.css" rel="stylesheet" type="text/css">

<title>Esempio</title>
</head>

<body>
<div id="finestra" align="center">
&nbsp;
<h2>APPLICAZIONE DI TEST</h2>
L'applicazione di test considera due tipologie di utenti, aventi rispettivamente i ruoli di Utente e Amministratore.
</br>Entrambi sono autorizzati, previa autenticazione, ad accedere all'area protetta dell'applicazione, costituita dagli URL nella forma
/pagine/*
</br>ma solo gli utenti aventi il ruolo di Amministratore possono accedere agli URL del tipo /riservata/*
<h3><a href="<%=request.getContextPath()%>/login">entra</a></h3>

</div>
</body>
</html>