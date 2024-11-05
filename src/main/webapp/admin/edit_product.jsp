<%-- 
    Document   : editproduct
    Created on : Oct 22, 2024, 3:00:16 PM
    Author     : ADMIN
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="dao.HoaDAO"%>
<%@page import="model.Hoa"%>
<%@page import="model.Loai"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.LoaiDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../shared/header.jsp" />
<jsp:include page="../shared/nav.jsp" />
<%

    Hoa hoa = (Hoa) request.getAttribute("hoa");
    ArrayList<Loai> dsLoai = (ArrayList<Loai>) request.getAttribute("dsLoai");
%>
<div class="container">  
    <h2>Cập nhật sản phẩm (Hoa)</h2>    
    <form method="post" enctype="multipart/form-data">
        <input type="hidden" name="mahoa" value="<%= hoa.getMahoa()%>" />
        <input type="hidden" name="oldImg" value="<%= hoa.getHinh()%>" />
        <div class="mb-2">
            <label>Tên hoa</label>
            <input type="text" name="tenhoa" class="form-control" required="" value="<%=hoa.getTenhoa()%>" />
        </div>
        <div class="mb-2">
            <label>Giá</label>
            <input type="number" name="gia"  class="form-control" required="" value="<%=hoa.getGia()%>"/>
        </div>
        <div class="mb-2">
            <label>Hình ảnh</label>
            <input type="file" name="hinh"  class="form-control" />
            <img src="assets/images/products/<%=hoa.getHinh()%>" alt="Current Image" style="width: 100px" />
        </div>
        <div class="mb-2">
            <label>Thể loại</label>
            <select name="maloai" class="form-control">      
                <%
                    for (Loai x : dsLoai) {
                %>
                <option value="<%=x.getMaloai()%>" <%=hoa.getMaloai() == x.getMaloai() ? "selected" : ""%> >
                    <%=x.getTenloai()%>
                </option>
                <%
                    }
                %>
            </select>
        </div>        
        <button type="submit" class="btn btn-primary">Save</button>
    </form>       
</div>
<jsp:include page="../shared/footer.jsp" />