<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.MediCareDrone.model.UserModel" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>User Details</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userDetails.css" />
</head>
<body>
<jsp:include page="header.jsp" />
<h2>User Table</h2>
<table>
    <tr>
        <th>First</th><th>Last</th><th>Email</th><th>Phone</th><th>Username</th><th>Gender</th><th>Password</th><th>Action</th>
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
        <td><%= u.getPassword() %></td>
        <td>
            <!-- Delete Button -->
            <form action="UserDetails" method="post" style="display:inline;">
                <input type="hidden" name="username" value="<%= u.getUsername() %>" />
                <input type="hidden" name="action" value="delete" />
                <input type="submit" value="Delete" />
            </form>
            <!-- Edit Button -->
            <button type="button" onclick="fillForm(
                '<%= u.getFirstName() %>',
                '<%= u.getLastName() %>',
                '<%= u.getEmail() %>',
                '<%= u.getPhone() %>',
                '<%= u.getUsername() %>',
                '<%= u.getGender() %>',
                '<%= u.getPassword() %>'
            )">Edit</button>
        </td>
    </tr>
    <% } %>
</table>

<h3>Add / Update User</h3>
<form id="userForm" action="UserDetails" method="post">
    <input type="text" name="firstName" placeholder="First Name" required />
    <input type="text" name="lastName" placeholder="Last Name" required />
    <input type="email" name="email" placeholder="Email" required />
    <input type="text" name="phone" placeholder="Phone" required />
    <input type="text" name="username" placeholder="Username" required id="usernameInput" />
    <input type="text" name="gender" placeholder="Gender" />
    <input type="password" name="password" placeholder="Password" required />
    <br><br>
    <button type="submit" name="action" value="add">Add</button>
    <button type="submit" name="action" value="update">Update</button>
</form>

<script>
    function fillForm(firstName, lastName, email, phone, username, gender, password) {
        document.querySelector('input[name="firstName"]').value = firstName;
        document.querySelector('input[name="lastName"]').value = lastName;
        document.querySelector('input[name="email"]').value = email;
        document.querySelector('input[name="phone"]').value = phone;
        document.querySelector('input[name="username"]').value = username;
        document.querySelector('input[name="gender"]').value = gender;
        document.querySelector('input[name="password"]').value = password;

        // Make username read-only to prevent changing primary key
        document.querySelector('input[name="username"]').readOnly = true;

        // Scroll to form
        document.getElementById("userForm").scrollIntoView({ behavior: 'smooth' });
    }
</script>
</body>
</html>