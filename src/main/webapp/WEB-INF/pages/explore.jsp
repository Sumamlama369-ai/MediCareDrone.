<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
            <div class="products">
                <!-- Drone M690 Pro -->
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/drone1.png" alt="Drone M690 Pro">
                    <h3>Drone M690 Pro</h3>
                    <p>The M690 Pro quadcopter is a long endurance and multi-functional flight platform.</p>
                    <p class="stock">In stock</p>
                    <p class="price">€3,145 incl. VAT</p>
                    <p class="price-excl">€2,599</p>
                    <button class="add-to-cart">Buy</button>
                </div>

                <!-- Drone MX860 -->
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/drone2.png" alt="Drone MX860">
                    <h3>Drone MX860</h3>
                    <p>Coaxial multi-rotor with remarkable load capacity and flight time.</p>
                    <p class="stock">In stock</p>
                    <p class="price">€5,202 incl. VAT</p>
                    <p class="price-excl">€4,299</p>
                    <button class="add-to-cart">Buy</button>
                </div>

                <!-- VTOL VA25 -->
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/drone3.png" alt="VTOL VA25">
                    <h3>VTOL VA25</h3>
                    <p>Fixed Wing VTOL Drone with reliable power system and long flight time.</p>
                    <p class="stock">In stock</p>
                    <p class="price">€9,558 incl. VAT</p>
                    <p class="price-excl">€7,899</p>
                    <button class="add-to-cart">Buy</button>
                </div>

                <!-- Drone M1200 -->
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/drone4.png" alt="Drone M1200">
                    <h3>Drone M1200</h3>
                    <p>The preferred platform for 2-5 kg payload with long endurance.</p>
                    <p class="stock">In stock</p>
                    <p class="price">€4,355 incl. VAT</p>
                    <p class="price-excl">€3,599</p>
                    <button class="add-to-cart">Buy</button>
                </div>

                <!-- Drone M1500 -->
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/drone5.png" alt="Drone M1500">
                    <h3>Drone M1500</h3>
                    <p>The preferred platform for 2-5 kg payload with long endurance.</p>
                    <p class="stock">In stock</p>
                    <p class="price">€6,533 incl. VAT</p>
                    <p class="price-excl">€5,399</p>
                    <button class="add-to-cart">Buy</button>
                </div>

                <!-- Drone M690B -->
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/drone6.png" alt="Drone M690B">
                    <h3>Drone M690B</h3>
                    <p>The M690B quadcopter is lightweight, portable, and offers long endurance.</p>
                    <p class="stock">In stock</p>
                    <p class="price">€2,419 incl. VAT</p>
                    <p class="price-excl">€1,999</p>
                    <button class="add-to-cart">Buy</button>
                </div>
            </div>
        </main>
    </div>
    



    <jsp:include page="footer.jsp" />
</body>
</html>