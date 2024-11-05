<%-- 
    Document   : listproduct
    Created on : Oct 22, 2024, 3:01:51 PM
    Author     : ADMIN
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="model.Hoa"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../shared/header.jsp" />

<jsp:include page="../shared/nav.jsp" />

<%
    if (request.getAttribute("success") != null) {
%>
<script>
    Swal.fire("<%=request.getAttribute("success")%>");
</script>
<%
} else if (request.getAttribute("error") != null) {

%>
<script>
    Swal.fire({
        icon: 'error',
        title: 'Lỗi',
        text: '<%=request.getAttribute("error")%>'
    });
</script>
<%
    }
%>

<div class="container">
    <h2> Danh sách sản phẩm</h2>
    <div class="mb-2 text-end">
        <a href="ManageProduct?action=add" class="btn btn-success"> <i class="bi bi-plus-circle"></i> Thêm mới</a>
    </div>

    <table class="table table-bordered table-striped">
        <tr>
            <th>Tên hoa</th>
            <th>Giá</th>
            <th>Hình ảnh</th>
            <th>Loại</th>
            <th>Action</th>
        </tr>  
        <%
            DecimalFormat fmt = new DecimalFormat("#,##0");
            ArrayList<Hoa> dsHoa = (ArrayList<Hoa>) request.getAttribute("dsHoa");
            for (Hoa x : dsHoa) {
        %>
        <tr>
            <td><%=x.getTenhoa()%></td>
            <td><%=fmt.format(x.getGia())%></td>
            <td> <img src="assets/images/products/<%=x.getHinh()%>" style="width: 100px">
            </td>
            <td><%=x.getMaloai()%></td>
            <td>
                <a href="ManageProduct?action=edit&mahoa=<%=x.getMahoa()%>" class="btn btn-secondary"> <i class="bi bi-pencil-square"></i> Sửa</a>
                <a href="ManageProduct?action=delete&mahoa=<%=x.getMahoa()%>" class="btn btn-danger" onclick="return confirm('Bạn có chắc chắn xoá không?');"> <i class="bi bi-trash" ></i> Xoá</a>
            </td>
        </tr>  
        <% }
        %>        
    </table>
    <div>
        <ul class="pagination justify-content-center">
            <%
                int pageSum = (int) request.getAttribute("pageSum");
                int pageIndex = (int) request.getAttribute("pageIndex");
                for (int i = 1; i <= pageSum; i++) {
            %>
            <li class="page-item <%= pageIndex ==i?"active":""%>">
                <a href="ManageProduct?page=<%=i%>" class="page-link">  <%=i%></a>
            </li>

            <%
                }%>

        </ul>

    </div>

</div>
<jsp:include page="../shared/footer.jsp" />