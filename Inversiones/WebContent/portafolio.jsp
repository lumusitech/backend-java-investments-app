<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- Se importan los paquetes java que se necesitan -->
<%@ page import="java.util.*, com.Inversiones.clientes.*" %>
    
<!-- Se importa las java tags libs para evitar poner código java -->
<!-- Con esto tendría etiquetas del core: forEach if choose etc -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Con esto tendría etiquetas de funciones predefinidas: split contains join etc. -->   
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Este es el portafolio del cliente: ${ cliente.nombre } con el pass: ${ cliente.pass }</h1>
</body>
</html>