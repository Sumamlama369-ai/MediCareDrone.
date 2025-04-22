<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Explore</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dashboard.css" />
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
                    <img src="${pageContext.request.contextPath}/resources/images/system/eye.png" alt="Eye Icon">
                </div>
                <h2>8,457</h2>
                <p>Daily Visits</p>
            </div>
            <div class="card">
                <div class="icon cyan">
                    <img src="${pageContext.request.contextPath}/resources/images/system/cart.png" alt="Cart Icon">
                </div>
                <h2>52,160</h2>
                <p>Sales</p>
            </div>
            <div class="card">
                <div class="icon red">
                    <img src="${pageContext.request.contextPath}/resources/images/system/drone.png" alt="Comment Icon">
                </div>
                <h2>15,823</h2>
                <p>No. of drones</p>
            </div>
            <div class="card">
                <div class="icon orange">
                    <img src="${pageContext.request.contextPath}/resources/images/system/user.png" alt="Users Icon">
                </div>
                <h2>36,752</h2>
                <p>No. of registered users</p>
            </div>
        </div>
    </div>
    
    
    <!-- Button Section -->
	<div class="button-container">
	    <a href="${pageContext.request.contextPath}/UserDetails" class="btn primary-btn">View User Details</a>
	    <a href="${pageContext.request.contextPath}/ProductDetails" class="btn secondary-btn">Manage Drones</a>
	</div>



    <jsp:include page="footer.jsp" />
</body>
</html>