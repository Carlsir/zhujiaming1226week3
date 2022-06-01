package com.YangZhihao.controller;

import com.YangZhihao.dao.IUserDao;
import com.YangZhihao.dao.Userdao;
import com.YangZhihao.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "UpdateServlet", value = "/updateUser")
public class UpdateServlet extends HttpServlet {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @Override
    public void init() throws ServletException {
        super.init();
        con = (Connection) getServletContext().getAttribute("con");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/views/updateUser.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id=request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("Email");
        String gender = request.getParameter("gender");
        String birthdate = request.getParameter("Birthdate");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        user.setId(Integer.parseInt(id));
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setGender(gender);
        user.setBirthdate(Date.valueOf(birthdate));
        IUserDao userDao = new Userdao();
        try {
            int update = userDao.updateUser(con,user);
            if (update == 0) {
                request.getRequestDispatcher("WEB-INF/views/userInfo.jsp").forward(request, response);
            }else{
                request.setAttribute("message","updateUser fail!");
                request.getRequestDispatcher("WEB-INF/views/updateUser.jsp").forward(request,response);
            }
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
