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

<link rel="stylesheet" href="styles/portafolio.css">

<title>Portafolio</title>

<script>
	function recuperarDatos(cantidad, nombre, precio){
		var prod = nombre;
		var cant = cantidad;
		var prec = precio;
		
		
		document.getElementById("producto").value = prod;
		document.getElementById("cantidad").value = cant;
		document.getElementById("precio").value = prec;
		
		var total = (prec * cant) - 100;
		
		document.getElementById("total").value = total.toFixed(2);
		
	}
	
	function calcular(cantidad){
		var precio = document.getElementById("precio").value;
		var total = 0;	
		var comision = 100;
		
		precio = parseFloat(precio);
		cantidad = parseFloat(cantidad);
		
		total = (cantidad * precio) - 100;
		
		document.getElementById('total').value = total.toFixed(2);
	}
</script>

</head>
<body>
	<header>
		<h1 id="titulo">Portafolio - ${ cliente.getNombre() }</h1>
		<nav>
			<ul>
			  <li><a href="#">Inicio</a></li>
			  <li><a href="#">Transferencias</a></li>
			  <li><a href="consulta.jsp">Consultas</a></li>
			  <li><a href="#">Acerca de</a></li>
			  <li><a href="#">Salir</a></li>
			</ul>
		</nav>
		
		<c:if test="${ confirmacion }">
       		<p>${ producto }</p>
       		<p>${ cantidad }</p>
     	</c:if>
		
	</header>
	
	<section>
		<table border="1">
		  <tr>
		    <th>Cantidad</th>
		    <th>Mis Produtos</th>
		    <th>Precio unitario</th>
		  </tr>		  
			<c:forEach var = "producto" items="${ listaProductos }">
			  <tr>
		        <td><c:out value = "${ producto.getCantidad() }"/></td>
		        <td><c:out value = "${ producto.getNombre() }"/></td>
		        <td><c:out value = "${ producto.getPrecio() }"/></td>
		        <td>
			        <a href="#openModal" onclick="recuperarDatos(${ producto.getCantidad() },
			        '${ producto.getNombre() }', ${ producto.getPrecio() } 	)">Vender</a>
		        </td>		        
		      </tr> 		   
	      	</c:forEach> 
		</table>
		
		<div id="openModal" class="modalDialog">
       		<div>
	            <a href="#close" title="Close" class="close">X</a>
	            <h2>Venta</h2>
	            <form action="ControladorTransaccion" method="post">
	            	<input type="hidden" name="transaccion" value="venta">
	            	<input type="hidden" name="id_cliente" value= ${ cliente.getId() }>
	            	<input id="producto" type="text"  name="nombre"><br>
	            	<input id="precio" type="text" name="precio"><br>
	            	<input id="cantidad" type="text" placeholder="Ingrese la cantidad" name="cantidad" onchange="calcular(this.value)" required><br>
	            	<input id="total" type="text" placeholder="Total" name="total"><br>
	            	<input type=submit value="Vender">
	            </form>
       		</div>
   		</div>
   			 
	</section>
		
		

    
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	<section>
	
		<%-- 		<c:if test="${ errorRegistro }"> --%>
<!--        		<p class="error">Error de registro: Revise los datos ingresados</p> -->
<%--      	</c:if> --%>
		<h1>Portafolio - perfil ${ perfil }</h1>
		<c:forEach var = "producto" items="${ listaProductos }">
	         Producto: <c:out value = "${producto.getNombre() }"/><p>
	         Precio: $<c:out value = "${producto.getPrecio() }"/><p>
	         Cantidad: <c:out value = "${producto.getCantidad() }"/><p>
      	</c:forEach>
	</section>      
</body>
</html>