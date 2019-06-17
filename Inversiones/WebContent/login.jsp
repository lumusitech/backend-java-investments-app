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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="styles/login.css">
    <!--<asset:stylesheet href="login.css"/> usado para que en grails tome el css de la carpeta assets del proyecto-->
    <title>Login</title>
    
</head>
<body>
    <div id="main">
       <div class="content-all">
            <div class="content-login" id="form-login">
            	<form action="ControladorLogin" method="post">
	                <h3>Login</h3>
	                <input type="hidden" name="instruccion" value="login">
	                <input type="text" placeholder="Nombre de usuario" name="nombre" required>
	                <input type="password" placeholder="Contraseña" name="pass" required>
	                <input type="submit" value="iniciar">
	                <p class="msj">¿Aún no te has registrado? <a href="registro.jsp">Regístrate aquí</a></p>
                	<c:if test="${ errorLogin }">
                		<p class="error">Las credenciales no son correctas.</p>
                	</c:if>
                </form>
            </div>
            
            
       </div>
    </div>
</body>
</html>