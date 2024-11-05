/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.HoaDAO;
import dao.LoaiDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Hoa;

/**
 *
 * @author ACER
 */
@WebServlet(name = "ManageProduct", urlPatterns = {"/ManageProduct"})
@MultipartConfig
public class ManageProduct extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("username") == null)
        {
            session.getAttribute("username");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        HoaDAO hoaDAO = new HoaDAO();
        LoaiDAO loaiDAO = new LoaiDAO();
        String action = "list";
        if (request.getParameter("action") != null) {
            action = request.getParameter("action");
        }

        switch (action) {
            case "list":
                
                // xử lý Liệt kê sản phẩm để quan trị ( có phân trang )
                int pageSize = 5;
                int pageIndex =1;
                if(request.getParameter("page") !=null)
                {
                    pageIndex = Integer.parseInt(request.getParameter("page"));
                }
                
                //tính tổng số trang
                int pageSum = (int) Math.ceil((double)hoaDAO.getAll().size()/pageSize);
                ArrayList<Hoa> dsHoa = hoaDAO.getByPage(pageIndex, pageSize);
                request.setAttribute("dsHoa", dsHoa);
                request.setAttribute("pageSum",pageSum);
                request.setAttribute("pageIndex",pageIndex);
                request.getRequestDispatcher("admin/list_product.jsp").forward(request, response);
                break;
            case "add":

                if (request.getMethod().equals("GET")) {
                    request.setAttribute("dsLoai", loaiDAO.getAll());
                    request.getRequestDispatcher("admin/add_product.jsp").forward(request, response);
                } else if (request.getMethod().equals("POST")) {
                    String tenhoa = request.getParameter("tenhoa");
                    double gia = Double.parseDouble(request.getParameter("gia"));
                    Part part = request.getPart("hinh");
                    int maloai = Integer.parseInt(request.getParameter("maloai"));
                    
                    String realPath = request.getServletContext().getRealPath("assets/images/products");
                    String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    part.write(realPath + "/" + filename);
                    
                    Hoa objInsert = new Hoa(0, tenhoa, gia, filename, maloai, new Date((new java.util.Date().getTime())));
                    if (hoaDAO.Insert(objInsert)) {
                        
                        request.setAttribute("success", "Thao tác thêm sản phẩm thành công");
                    } else {
                        request.setAttribute("error", "Thao tác thêm sản phẩm thất bại");
                    }
                    // chuyển tiếp  người dùng về action=LIST để liệt kê lại danh sách sản phẩm
                    request.getRequestDispatcher("ManageProduct?action=list").forward(request, response);
                }
                break;
            case "edit":


                 if (request.getMethod().equalsIgnoreCase("get")) {
                    int mahoa = Integer.parseInt(request.getParameter("mahoa"));
                    request.setAttribute("hoa", hoaDAO.getById(mahoa));
                    request.setAttribute("dsLoai", loaiDAO.getAll());
                    request.getRequestDispatcher("admin/edit_product.jsp").forward(request, response);

                } else if (request.getMethod().equalsIgnoreCase("post")) {
                    int mahoa = Integer.parseInt(request.getParameter("mahoa"));
                    String tenhoa = request.getParameter("tenhoa");
                    double gia = Double.parseDouble(request.getParameter("gia"));
                    Part part = request.getPart("hinh");
                    int maloai = Integer.parseInt(request.getParameter("maloai"));
                    String filename = request.getParameter("oldImg");
                    if (part.getSize() > 0) {
                        String realPath = request.getServletContext().getRealPath("/assets/images/products");
                        filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                        part.write(realPath + "/" + filename);

                    }                    
                    Hoa objUpdate =new Hoa(mahoa, tenhoa, gia, filename, maloai, new Date(new java.util.Date().getTime()));
                    if (hoaDAO.Update(objUpdate)) {
                        request.setAttribute("success", "Cập nhật sản phẩm thành công");
                    } else {
                        request.setAttribute("error", "Cập nhật sản phẩm thất bại");
                    }
                    request.getRequestDispatcher("ManageProduct?action=list").forward(request, response);

                }
                break;
            case "delete":
                
                int mahoa = Integer.parseInt(request.getParameter("mahoa"));
                if (hoaDAO.Delete(mahoa)) {
                    request.setAttribute("success", "Thao tác xóa sản phẩm thành công");
                } else {
                    request.setAttribute("error", "Thao tác xóa sản phẩm thất bại");
                }
                request.getRequestDispatcher("ManageProduct?action=list").forward(request, response);
                break;
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
    