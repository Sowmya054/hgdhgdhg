<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>view all</title>
<script>
	function goBack() {
		window.history.back();
	}
</script>
</head>
<body bgcolor="aqua">
     <table>
     	<tr>
     		<td>Customer Name</td>
     		<td>Mobile Number</td>
     		<td>Wallet Balance</td>
     	</tr>
     	<c:forEach  items="${customer}" var="customer">
			<td>${customer.name}</td>
			<td>${customer.mobileNo}</td>
			<td>${customer.wallet.balance}</td>
		</c:forEach>
     </table>
		<hr>
	
	

	<button onclick="goBack()">Go Back</button>
</body>
</html>