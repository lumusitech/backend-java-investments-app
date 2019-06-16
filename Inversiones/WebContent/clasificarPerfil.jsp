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
<title>Test de inversor</title>
<link rel="stylesheet" href="styles/clasificacion.css">
</head>
<body>
	<div id="content-all">
		<h1>Test de inversor</h1>
		<div id="encuesta">
			<form action="ControladorLogin" method="post">
				
				<input type="hidden" name="id_cliente" value="${ cliente.getId() }">
				<input type="hidden" name="instruccion" value="login">
				
		        <p class="pregunta">¿A cuánto tiempo quiere invertir?</p>
		       
	          	<p class="opcion"><input type="radio" name="tiempo" value="1" checked>	6 meses</p>
	        
	            <p class="opcion"><input type="radio" name="tiempo" value="5">	12 meses</p>
	       
	            <p class="opcion"><input type="radio" name="tiempo" value="10">	36 meses</p>
		        
			    
			    <p class="pregunta">Si tiene inversión en un activo y el mismo baja un 10% ¿Qué hace?</p>
			       
	            <p class="opcion"><input type="radio" name="reaccion10" value="1" checked>	Retira</p>
	        
	            <p class="opcion"><input type="radio" name="reaccion10" value="5">	Espera</p>
	       
	            <p class="opcion"><input type="radio" name="reaccion10" value="10">	Invierte más</p>
	            
	            
	            <p class="pregunta">Si tiene inversión en un activo y el mismo baja un 25% ¿Qué hace?</p>
			       
	            <p class="opcion"><input type="radio" name="reaccion25" value="1" checked>	Retira</p>
	        
	            <p class="opcion"><input type="radio" name="reaccion25" value="5">	Espera</p>
	       
	            <p class="opcion"><input type="radio" name="reaccion25" value="10">	Invierte más</p>
	            
	            
	            <p class="pregunta">Experiencia en inversiones</p>
			       
	            <p class="opcion"><input type="radio" name="experiencia" value="1" checked>	Escasa</p>
	        
	            <p class="opcion"><input type="radio" name="experiencia" value="5">	Media</p>
	       
	            <p class="opcion"><input type="radio" name="experiencia" value="10">	Alta</p>
	            
	            
	            <p class="pregunta">preferencias de inversión</p>
			       
	            <p class="opcion"><input type="radio" name="preferencia" value="1" checked>	Renta fija</p>
	        
	            <p class="opcion"><input type="radio" name="preferencia" value="5">	Fondos comunes de inversión</p>
	            
	            <p class="opcion"><input type="radio" name="preferencia" value="10">	Renta variable</p>
	        
	            <input class="boton" type="submit" value="Enviar">
			</form>
		</div>
	</div>
	
		
	
	
</body>
</html>