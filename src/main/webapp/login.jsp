<%-- 
    Document   : login
    Created on : Oct 31, 2024, 3:04:13 PM
    Author     : ADMIN
--%>
<jsp:include page="shared/header.jsp" />
<jsp:include page="shared/nav.jsp" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container">
    <form action="LoginServlet" method="post" >
        <h2>Login System</h2>
        <div>
            <label for="username">Username</label>
            <input type="text" name="username" required="" class="form-control">
        </div>
        <div >
            <label for="password">Password</label>
            <input type="password" name="password" required="" class="form-control">
        </div>
        <div class="mt-2">
            <button class="btn btn-primary" type="submit" > Login </button>
        </div>
        <%
            if (request.getAttribute("error") != null) {
        %>
        <div class="text-danger mb-3">
            <%=request.getAttribute("error")%>
        </div>
        <%
            }
        %>
    </form>

</div>
<jsp:include page="shared/footer.jsp" />