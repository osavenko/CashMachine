package com.epam.savenko.cashmachine.web.tag;

import com.epam.savenko.cashmachine.dao.MenuDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcMenuDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.MenuItem;
import com.epam.savenko.cashmachine.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class MenuLine extends TagSupport {

    private static final Logger LOG = Logger.getLogger(MenuLine.class);

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute("cashUser");
        StringBuilder sb = new StringBuilder();
        if (user != null) {
            MenuDao menuDao = new JdbcMenuDaoImpl();
            try {
                List<MenuItem> listReferences = menuDao.findRoleMenuItemsFromGroupByLocale(user.getRoleId(), 1, user.getLocaleId());
                buildDropMenu(sb, listReferences);
                List<MenuItem> listJournals = menuDao.findRoleMenuItemsFromGroupByLocale(user.getRoleId(), 2, user.getLocaleId());
                buildDropMenu(sb, listJournals);
                List<MenuItem> listReports = menuDao.findRoleMenuItemsFromGroupByLocale(user.getRoleId(), 3, user.getLocaleId());
                buildDropMenu(sb, listReports);
            } catch (CashMachineException e) {
                e.printStackTrace();
            }
        } else {
            sb.append("Меню отсутствует");
        }
        try {
            pageContext.getOut().write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    private void buildDropMenu(StringBuilder sb, List<MenuItem> menuItems) {
        if (!menuItems.isEmpty()) {
            sb.append("<li class=\"nav-item dropdown\">")
                    .append("<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">")
                    .append("Название")
                    .append("</a>")
                    .append("<ul class=\"dropdown-menu\" aria-labelledby=\"navbarDropdown\">");
            for (MenuItem menuItem : menuItems) {
                sb.append("<li><a class=\"dropdown-item\" href=\"")
                        .append(menuItem.getUrl())
                        .append("\">")
                        .append(menuItem.getName())
                        .append("</a></li>");
            }
            sb.append("</ul>").append("</li>");
        }
    }
}
