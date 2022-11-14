    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Test delle policy xacml!</title>
    </head>
    <body>
    <h1>Test delle policy xacml!</h1>
    <form action="<%=request.getContextPath() %>/servlet_xacml" method="post">
    			<table style="with: 50%">
    				<tr>
    					<td>id-utente</td>
    					<td><input type="text" name="id-utente" /></td>
    				</tr>
    				<tr>
    					<td>resource-type</td>
    					<td><input type="text" name="resource-type" /></td>
    				</tr>
    				<tr>
    					<td>id-lista</td>
    					<td><input type="text" name="id-lista" /></td>
    				</tr>
    				
    				<tr>
    					<td>id-gruppo</td>
    					<td><input type="text" name="id-gruppo" /></td>
    				</tr>
    				<tr>
    					<td>action-type</td>
    					<td><input type="text" name="action-type" /></td>
    				</tr>
    				
    				</table>
    			<input type="submit" value="Submit" /></form>
    </body>
    </html>