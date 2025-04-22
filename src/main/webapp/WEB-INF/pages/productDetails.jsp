<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.MediCareDrone.model.ProductModel" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Product Details</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/productDetails.css" />
</head>
<body>
<jsp:include page="header.jsp" />
<h2>Product Table</h2>
<table>
    <tr>
        <th>ID</th><th>Name</th><th>Payload</th><th>Range (KM)</th><th>Price</th><th>Warranty</th><th>Action</th>
    </tr>
    <%
        // Fetch product list from request attribute
        List<ProductModel> products = (List<ProductModel>) request.getAttribute("products");
        for (ProductModel p : products) {
    %>
    <tr>
        <td><%= p.getDroneId() %></td>
        <td><%= p.getDroneName() %></td>
        <td><%= p.getPayloadCapacity() %></td>
        <td><%= p.getMaxRangeKm() %></td>
        <td><%= p.getPrice() %></td>
        <td><%= p.getWarrantyPeriod() %></td>
        <td>
            <!-- Delete Form: sends droneId and delete action to servlet -->
            <form action="ProductDetails" method="post" style="display:inline;">
                <input type="hidden" name="droneId" value="<%= p.getDroneId() %>" />
                <input type="hidden" name="action" value="delete" />
                <input type="submit" value="Delete" />
            </form>
            
            <!-- Edit Button: Fills form fields for update -->
            <button onclick="fillForm('<%= p.getDroneId() %>','<%= p.getDroneName() %>','<%= p.getPayloadCapacity() %>', '<%= p.getMaxRangeKm() %>', '<%= p.getPrice() %>', '<%= p.getWarrantyPeriod() %>')">Edit</button>
        </td>
    </tr>
    <% } %>
</table>

<h3>Add / Update Product</h3>

<!-- Add / Update Form -->
<form id="productForm" action="ProductDetails" method="post">
    <!-- Hidden droneId used only during update -->
    <input type="hidden" name="droneId" id="droneId" />
    
    <!-- Input fields for product data -->
    <input type="text" name="droneName" placeholder="Drone Name" required />
    <input type="text" name="payloadCapacity" placeholder="Payload Capacity" />
    <input type="number" name="maxRangeKm" placeholder="Max Range (KM)" required />
    <input type="text" name="price" placeholder="Price" required />
    <input type="text" name="warrantyPeriod" placeholder="Warranty Period" />
    
    <br><br>
    
    <!-- Buttons for add and update (handled separately by servlet using action parameter) -->
    <button type="submit" name="action" value="add">Add</button>
    <button type="submit" name="action" value="update">Update</button>
</form>

<!-- JavaScript function to fill form with selected row data for editing -->
<script>
    function fillForm(id, name, payload, range, price, warranty) {
        // Set values in the form for updating
        document.getElementById('droneId').value = id;
        document.querySelector('input[name="droneName"]').value = name;
        document.querySelector('input[name="payloadCapacity"]').value = payload;
        document.querySelector('input[name="maxRangeKm"]').value = range;
        document.querySelector('input[name="price"]').value = price;
        document.querySelector('input[name="warrantyPeriod"]').value = warranty;

        // Smooth scroll to the form
        document.getElementById("productForm").scrollIntoView({ behavior: 'smooth' });
    }
</script>

</body>
</html>