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
<title></title>
<script>
	function redireccionar(direccionRecibida){
	
		document.getElementById("direccion").value = direccionRecibida;
		
		document.forma.submit();
	}
</script>


</head>
<body>
	<form action="ControladorLogin" name="forma" method="post">
		<input id="direccion" type="hidden" name="direccion">
		<input type="hidden" name="id_cliente" value= ${ cliente.getId() }>
		<input type="hidden" name="transaccion" value="otro">
	</form>
	
	<script>
		console.log("Paso por la redirección");
		redireccionar("portafolio.jsp");
	</script>
</body>
</html>