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

<link rel="stylesheet" href="styles/portafolio.css">

<title>Portafolio</title>

<!----------------------------------------------------------------------------------------------------->

<script>
	
	var tipoDeCalculo = "";
	
	function escribir(operacion){
		document.getElementById("tituloModal").innerHTML=operacion;
	}

	function recuperarDatosVenta(cantidad, nombre, precio){
	
		escribir("Venta");
		tipoDeCalculo = "venta"
		document.getElementById("tipo").value = "venta";
		
		var prod = nombre;
		var cant = cantidad;
		var prec = precio;
		
		document.getElementById("cantidadASetear").value = cant;
		
		document.getElementById("producto").value = prod;
		document.getElementById("cantidad").value = cant;
		document.getElementById("precio").value = prec;
		
		var total = (prec * cant) - 100;
		
		document.getElementById("total").value = total.toFixed(2);
		
	}
	
	function recuperarDatosCompra(cantidad, nombre, precio){
		
		escribir("Compra");
		tipoDeCalculo = "compra"
		document.getElementById("tipo").value = "compra";
		
		var prod = nombre;
		var cant = cantidad;
		var prec = precio;
		
		document.getElementById("cantidadASetear").value = cant;
		
		document.getElementById("producto").value = prod;
		document.getElementById("cantidad").value = cant;
		document.getElementById("precio").value = prec;
		
		var total = (prec * cant) - 100;
		
		document.getElementById("total").value = total.toFixed(2);
		
	}
	
	function calcular(cantidad){
		if(tipoDeCalculo == "compra"){
			calcularCompra(cantidad)
		}else{
			calcularVenta(cantidad)
		}
	}
	
	function calcularCompra(cantidad){
		
		var precio = document.getElementById("precio").value;
		var total = 0;	
		var comision = 100;
		
		precio = parseFloat(precio);
		cantidad = parseFloat(cantidad);
		
		total = (cantidad * precio) + 100;
		
		document.getElementById('total').value = total.toFixed(2);
	}
	
	function calcularVenta(cantidad){
		
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

<!----------------------------------------------------------------------------------------------------->

<body>
	<header>
		<h1 id="titulo">Portafolio - ${ cliente.getNombre() } - $${ cliente.getSaldo() }</h1>
		<nav>
			<ul>
			  <li><a href="#">Inicio</a></li>
			  <li><a href="#">Transferencias</a></li>
			  <li><a href="consulta.jsp">Consultas</a></li>
			  <li><a href="#">Acerca de</a></li>
			  <li><a href="login.jsp">Salir</a></li>
			</ul>
		</nav>
		
		<c:if test="${ errorSaldo }">
			<h2 id="error">La operación no puede efectuarse por saldo insuficiente</h2>	 
     	</c:if>
     	
     	<c:if test="${ errorCantidad }">
			<h2 id="error">La operación no puede efectuarse por cantidad de productos insuficiente</h2>	 
     	</c:if>
		

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
	
	<section>
		<h2>Acciones / Bonos / Criptomonedas</h2>
		<table class="tabla">
		  <tr>
		    <th>Cantidad</th>
		    <th>Productos</th>
		    <th>Precio unitario</th>
		  </tr>		  
			<c:forEach var = "producto" items="${ listaProductos }">
				<c:if test="${ producto.getNombre() != 'Fondo conservador'
				 && producto.getNombre() != 'Fondo moderado' 
				 && producto.getNombre() != 'Fondo arriesgado'
				 && producto.getCantidad() > 0}">
		       		<tr>
				        <td><c:out value = "${ producto.getCantidad() }"/></td>
				        <td><c:out value = "${ producto.getNombre() }"/></td>
				        <td><c:out value = "${ producto.getPrecio() }"/></td>
				        <td>
					        <a href="#openModal" onclick="recuperarDatosCompra(${ producto.getCantidad() },
					        '${ producto.getNombre() }', ${ producto.getPrecio() } 	)">Comprar</a>
				        </td>		        
		     		 </tr> 
     			</c:if>
			  		   
	      	</c:forEach> 
		</table>
   			 
	</section>
	
<!----------------------------------------------------------------------------------------------------->
	
	<section>
		<h2>Fondos de inversión</h2>
		<table class="tabla">
		  <tr>
		    <th>Cantidad</th>
		    <th>Mis Productos</th>
		    <th>Precio unitario</th>
		  </tr>		  
			<c:forEach var = "producto" items="${ listaProductos }">
			  <c:if test="${ producto.getNombre() == 'Fondo conservador'
				 || producto.getNombre() == 'Fondo moderado' 
				 || producto.getNombre() == 'Fondo arriesgado'
				 && producto.getCantidad() > 0}">
		       		<tr>
				        <td><c:out value = "${ producto.getCantidad() }"/></td>
				        <td><c:out value = "${ producto.getNombre() }"/></td>
				        <td><c:out value = "${ producto.getPrecio() }"/></td>
				        <td>
					        <a href="#openModal" onclick="recuperarDatosCompra(${ producto.getCantidad() },
					        '${ producto.getNombre() }', ${ producto.getPrecio() } 	)">Comprar</a>
				        </td>		        
		     		 </tr> 
     			</c:if>	   
	      	</c:forEach> 
		</table>
   			 
	</section>
	
<!----------------------------------------------------------------------------------------------------->
	
<!--------------------------------------Ventana modal-------------------------------------------------->
	
	<div id="openModal" class="modalDialog">
       		<div>
	            <a href="#close" title="Close" class="close">X</a>
	            <h3 id="tituloModal"></h3>
	            <form id="miForm" action="ControladorTransaccion" method="post">
	            	<input id="tipo" type="hidden" name="transaccion">
	            	<input type="hidden" name="id_cliente" value= ${ cliente.getId() }>
	            	<input id="cantidadASetear" type="hidden" name="cantidadInicial">
	            	<input id="producto" type="text"  name="nombre"><br>
	            	<input id="precio" type="text" name="precio"><br>
	            	<input id="cantidad" type="text" placeholder="Ingrese la cantidad" name="cantidad" onchange="calcular(this.value)" required><br>
	            	<input id="total" type="text" placeholder="Total" name="total"><br>
	            	<input type="submit" value="Confirmar">
	            </form>
       		</div>
   		</div>
   		
<!----------------------------------------------------------------------------------------------------->
		
</body>
</html>