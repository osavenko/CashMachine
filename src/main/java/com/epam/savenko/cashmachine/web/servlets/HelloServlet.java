package com.epam.savenko.cashmachine.web.servlets;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcRoleDaoImpl;
import com.epam.savenko.cashmachine.model.Role;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        JdbcRoleDaoImpl roleDao = new JdbcRoleDaoImpl();
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("<h1>Работа с ролями</h1>");
            sb.append("<div>").append("<h2>Перебор в цикле(findAll)</h2>");
            sb.append("<span>")
                    .append("</span><br>");
            sb.append("<span>===================</span>");
            for (Role role : roleDao.findAll()) {
                sb.append("<span>")
                        .append(role)
                        .append("</span><br>");
            }
            sb.append("</div>");
        } catch (CashMachineException e) {
            e.printStackTrace();
        }
        out.println("<html><body>");
        out.println(sb);
        out.println("</body></html>");
    }

    public void destroy() {
    }
}