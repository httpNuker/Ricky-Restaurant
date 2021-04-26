<%@ page import = "com.virtualpairprogrammers.domain.MenuItem" %>
<%@ page import = "java.util.List" %>

<html>
	<body>
		<jsp:include page="/header.jsp" />
		<h2>Order your food</h2>

		<form action='orderReceived.html' method='POST' >
		<ul>
		
		<%
		
		List<MenuItem> menuItems = (List<MenuItem>)request.getAttribute("menuItems");
		
		for (MenuItem menuItem : menuItems) {
			out.println("<li>" + menuItem + "<input type='text' name='item_" +menuItem.getId() +"' /> </li>");
		}
		 %>
		
		</ul>
		<input type='submit' />
		</form>
		
		<jsp:include page="/footer.jsp" />
	</body>
</html>