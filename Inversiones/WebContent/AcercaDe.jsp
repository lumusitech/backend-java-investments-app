<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- Se importan los paquetes java que se necesitan -->
<%@ page import="java.util.*, com.Inversiones.clientes.*" %>
    
<!-- Se importa las java tags libs para evitar poner código java -->
<!-- Con esto tendría etiquetas del core: forEach if choose etc -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Con esto tendría etiquetas de funciones predefinidas: split contains join etc. -->   
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 
 <!----------------------------------------------------------------------------------------------------->
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" href="styles/consulta.css">

<title>Acerca de</title>

<script>

function redireccionar(direccionRecibida){
	document.getElementById("direccion").value = direccionRecibida;
	
	document.forma.submit();
}

</script>

</head>
<!----------------------------------------------------------------------------------------------------->
<body>
	<header>
		<h1 id="titulo">Acerca de la Empresa - ${ cliente.getNombre() }</h1>
		<nav>
			<ul>
			  <li><a href="javascript:redireccionar('portafolio.jsp');">Inicio</a></li>
			  <li><a href="javascript:redireccionar('transferencia.jsp');">Transferencias</a></li>
			  <li><a href="javascript:redireccionar('consulta.jsp');">Consultas</a></li>
			  <li><a href="javascript:redireccionar('AcercaDe.jsp');">Acerca de</a></li>
			  <li><a href="javascript:redireccionar('login.jsp');">Salir</a></li>
			</ul>
		</nav>
		
		<!----------------------------------------------------------------------------------------------------->
		
	</header>

	<section>
		<h2>Mis productos</h2>
		<table class="tabla">
		  <tr>
		    <th>Cantidad</th>
		    <th>Mis productos</th>
		    <th>Precio unitario</th>
		  </tr>		  
			<c:forEach var = "producto" items="${ productosCliente }">
				
	       		<tr>
			        <td><c:out value = "${ producto.getCantidad() }"/></td>
			        <td><c:out value = "${ producto.getNombre() }"/></td>
			        <td><c:out value = "${ producto.getPrecio() }"/></td>
			        <td>
				        <a href="#openModal" onclick="recuperarDatosVenta(${ producto.getCantidad() },
				        '${ producto.getNombre() }', ${ producto.getPrecio() } 	)">Vender</a>
			        </td>		        
	     		 </tr> 
			  		   
	      	</c:forEach> 
		</table>
   			 
	</section>
	
<!----------------------------------------------------------------------------------------------------->

	<form action="ControladorLogin" name="forma" method="post">
		<input id="direccion" type="hidden" name="direccion" value="">
		<input id="id_cliente" type="hidden" name="id_cliente" value="${ cliente.getId() }" >
	</form>
	
	
</body>
</html>