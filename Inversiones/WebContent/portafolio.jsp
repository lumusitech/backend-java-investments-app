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
<title>Portafolio</title>
</head>
<body>
	<header>
		<h1>Portafolio - perfil ${ perfil }</h1>
		<c:forEach var = "producto" items="${ listaProductos }">
	         Producto: <c:out value = "${producto.getNombre() }"/><p>
	         Precio: $<c:out value = "${producto.getPrecio() }"/><p>
	         Cantidad: <c:out value = "${producto.getCantidad() }"/><p>
      	</c:forEach>
	</header>

	 <!--<c:if test="${ errorRegistro }">
        <p class="error">Error de registro: Revise los datos ingresados</p>
     </c:if>-->
		      
</body>
</html>