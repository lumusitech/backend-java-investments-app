<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			<form method="post" action="ControladorEncuesta">
			
		        <p class="pregunta">¿A cuánto tiempo quiere invertir?</p>
		       
	          	<p class="opcion"><input type="radio" name="tiempo" value="6"> 6 meses</p>
	        
	            <p class="opcion"><input type="radio" name="tiempo" value="12"> 12 meses</p>
	       
	            <p class="opcion"><input type="radio" name="tiempo" value="36"> 36 meses</p>
		        
			    
			    <p class="pregunta">Si tiene inversión en un activo y el mismo baja un 10% ¿Qué hace?</p>
			       
	            <p class="opcion"><input type="radio" name="reaccion10" value="6"> Retira</p>
	        
	            <p class="opcion"><input type="radio" name="reaccion10" value="12"> Espera</p>
	       
	            <p class="opcion"><input type="radio" name="reaccion10" value="36"> Invierte más</p>
	            
	            
	            <p class="pregunta">Si tiene inversión en un activo y el mismo baja un 25% ¿Qué hace?</p>
			       
	            <p class="opcion"><input type="radio" name="reaccion25" value="6"> Retira</p>
	        
	            <p class="opcion"><input type="radio" name="reaccion25" value="12"> Espera</p>
	       
	            <p class="opcion"><input type="radio" name="reaccion25" value="36"> Invierte más</p>
	            
	            
	            <p class="pregunta">Experiencia en inversiones</p>
			       
	            <p class="opcion"><input type="radio" name="experiencia" value="escasa"> Escasa</p>
	        
	            <p class="opcion"><input type="radio" name="experiencia" value="media"> Media</p>
	       
	            <p class="opcion"><input type="radio" name="experiencia" value="alta"> Alta</p>
	            
	            
	            <p class="pregunta">preferencias de inversión</p>
			       
	            <p class="opcion"><input type="radio" name="preferencia" value="rentaFija"> Renta fija</p>
	        
	            <p class="opcion"><input type="radio" name="preferencia" value="fondos"> Fondos comunes de inversión</p>
	       
	            <p class="opcion"><input type="radio" name="preferencia" value="rentaVariable"> Renta variable</p>
	            
	            <input class="boton" type="submit" value="Enviar">
			</form>
		</div>
	</div>
	
		
	
	
</body>
</html>