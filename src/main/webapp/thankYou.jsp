<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<script>
			function updateStatus() {
				var request = new XMLHttpRequest();
				request.onreadystatechange = function() {
					if (this.readyState == 4) {
						var json = JSON.parse(this.responseText);
						document.getElementById("status").innerHTML = json.status;
						document.getElementById("time").innerHTML = "Last updated : " + json.time;
					}
				}
				request.open("GET","/updatedStatus?id=${id}",true)
				request.send();
			}
			
			window.setInterval(
				function() {
					updateStatus();
				}
					, 2000);
			
		</script>
	
	</head>

	<body>
		<jsp:include page="/header.jsp" />
		<h2>Order your food</h2>
	 
		 <p>Thank you - your order has been received. You need to pay 
		 <fmt:formatNumber value="${total}" type="currency" currencyCode="${currency}" /></p>
		 
		 <p>The current status of your order is : <span id="status">${status}</span>  <input type="button" value="refresh status" onclick="updateStatus()" />  </p>
		 <p id="time"></p>
		 				
		<jsp:include page="/footer.jsp" />
	</body>
</html>