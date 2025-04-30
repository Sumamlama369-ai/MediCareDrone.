<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.MediCareDrone.model.UserModel" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Explore</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dashboardUser.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
    <%-- Session check using JSTL --%>
    <c:if test="${empty sessionScope.username}">
        <c:redirect url="${pageContext.request.contextPath}/login" />
    </c:if>

    <jsp:include page="header.jsp" />
    
    
    	<!-- Dashboard Section -->
		<div class="container">
        <h1>Dashboard Summary of MediCareDrone</h1>
        <div class="dashboard">
            <div class="card">
			    <div class="icon green">
			        <img src="${pageContext.request.contextPath}/resources/images/system/eye.png" alt="eye Icon">
			    </div>
			    <h2>₹${avgOrderValue}</h2>
			    <p>Avg. Order Value</p>
			</div>

            <div class="card">
			    <div class="icon cyan">
			        <img src="${pageContext.request.contextPath}/resources/images/system/cart.png" alt="Cart Icon">
			    </div>
			    <h2>${totalOrders}</h2> <!-- 👈 Dynamic order count -->
			    <p>Sales</p>
			</div>

            <div class="card">
			    <div class="icon red">
			        <img src="${pageContext.request.contextPath}/resources/images/system/drone.png" alt="Comment Icon">
			    </div>
			    <h2>${totalDrones}</h2> <!-- 👈 Dynamic value -->
			    <p>No. of drones</p>
			</div>

            <div class="card">
			    <div class="icon orange">
			        <img src="${pageContext.request.contextPath}/resources/images/system/user.png" alt="Users Icon">
			    </div>
			    <h2>${totalUsers}</h2> <!-- 👈 dynamic value here -->
			    <p>No. of registered users</p>
			</div>


        </div>
    </div>
    
    
    <!-- Button Section -->
	<!--<div class="button-container">
	    <a href="${pageContext.request.contextPath}" class="btn primary-btn">View User Details</a>
	    <a href="${pageContext.request.contextPath}" class="btn secondary-btn">Manage Drones</a>
	</div>

	-->


		
		
	<h3>User Table</h3>
	<table>
	    <tr>
	        <th>First</th><th>Last</th><th>Email</th><th>Phone</th><th>Username</th><th>Gender</th>
	    </tr>
	    <%
	        List<UserModel> users = (List<UserModel>) request.getAttribute("users");
	        for (UserModel u : users) {
	    %>
	    <tr>
	        <td><%= u.getFirstName() %></td>
	        <td><%= u.getLastName() %></td>
	        <td><%= u.getEmail() %></td>
	        <td><%= u.getPhone() %></td>
	        <td><%= u.getUsername() %></td>
	        <td><%= u.getGender() %></td>

	    </tr>
	    <% } %>
	</table>
			
		
	

    <!--<jsp:include page="footer.jsp" />   -->
</body>
</html>