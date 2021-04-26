<%@ page import = "com.virtualpairprogrammers.domain.MenuItem" %>
<%@ page import = "java.util.List" %>

<html>
	<body>
		<jsp:include page="/header.jsp" />
		<h2>Your search results:</h2>
		<ul>
			<%
			
			List<MenuItem> menuItems = (List<MenuItem>)request.getAttribute("menuItems");
			
			for (MenuItem menuItem : menuItems) {
			%>
				<li><%=menuItem %></li>	
			<%
				}
			%>
		</ul>
		
		<jsp:include page="/footer.jsp" />
	</body>
</html>