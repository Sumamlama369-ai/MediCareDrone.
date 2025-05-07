<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
			
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Explore</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/explore.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
    <%-- Session check using JSTL --%>
    <c:if test="${empty sessionScope.username}">
        <c:redirect url="${pageContext.request.contextPath}/login" />
    </c:if>

    <jsp:include page="header.jsp" />
    
	<div class="main-container">
        <!-- Sidebar for Filters -->
        <aside class="sidebar">
            <h2>Open Filter</h2>
            <div class="filter-section">
                <label for="price-range">Price Range (€)</label>
                <input type="range" id="price-range" min="1" max="12890" value="12890">
                <div class="price-labels">
                    <span>1</span>
                    <span>12890</span>
                </div>
            </div>
        </aside>

        <!-- Product List -->
        <main class="product-list">
            <h1>Products - Drones</h1>
            <!-- Search Bar -->
            <form action="${pageContext.request.contextPath}/Explore" method="get" class="search-container">
			  <input
			    type="text"
			    name="query"
			    id="search-input"
			    placeholder="Search drones..."
			    value="${param.query}"
			  />
			  <button type="submit" id="search-button">Search</button>
			</form>
			

          
			
			<div class="products">
			  <c:choose>
			    <c:when test="${not empty products}">
			      <c:forEach var="product" items="${products}">
			        <div class="product-card">
			          <!-- Use the drone ID to pick the image file -->
			          <c:set var="imgName" value="drone${product.droneId}.png" />
			          <img
			            src="${pageContext.request.contextPath}/resources/images/system/${imgName}"
			            alt="${product.droneName}"
			          />
			          <h3>${product.droneName}</h3>
			          <p>${product.payloadCapacity}</p>
			          <p class="stock">In stock</p>
			          <p class="price">€${product.price} incl. VAT</p>
			          <p class="price-excl">€${product.price}</p>
			          <button class="add-to-cart">Buy</button>
			        </div>
			      </c:forEach>
			    </c:when>
			    <c:otherwise>
			      <p>No drones found matching “<strong>${param.query}</strong>”.</p>
			    </c:otherwise>
			  </c:choose>
			</div>


        </main>
    </div>
  
    <jsp:include page="footer.jsp" />
</body>
</html>