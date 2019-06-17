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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="styles/registro.css">
    <!--<asset:stylesheet href="login.css"/> usado para que en grails tome el css de la carpeta assets del proyecto-->
    <title>Register</title>
    
</head>
<body>
	<div id="main">
		<div class="content-all">
			<div class="content-register" id="form-register">
		   		<form action="ControladorLogin" method="post">
		         <h3>Registro</h3>
		         <input type="hidden" name="instruccion" value="registro">
		         <input type="text" placeholder="Nombre de usuario" name="nombre">
		         <input type="date" placeholder="Fecha de nacimiento" name="nac">
		         <input type="text" placeholder="email" name="email">
		         <input type="text" placeholder="DNI" name="dni">		   
		         <input type="password" placeholder="Contraseña" name="pass">
		         <input type="submit" value="registrarse">
		         <p>¿Ya estás registrado? <a href="login.jsp">Inicia sesión aquí</a></p>
		         <c:if test="${ errorRegistro }">
		         		<p class="error">Error de registro: Revise los datos ingresados</p>
		         </c:if>
		      </form>
		    </div>
	    </div>
	</div>
</body>
</html>