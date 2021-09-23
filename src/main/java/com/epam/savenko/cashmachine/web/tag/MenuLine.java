package com.epam.savenko.cashmachine.web.tag;

import com.epam.savenko.cashmachine.dao.MenuDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcMenuDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.GroupMenuView;
import com.epam.savenko.cashmachine.model.MenuItem;
import com.epam.savenko.cashmachine.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MenuLine extends TagSupport {

    private static final Logger LOG = Logger.getLogger(MenuLine.class);
    private static final long serialVersionUID = 2383228673564292557L;

    @Override
    public int doStartTag() {

        LOG.debug("Start custom tag MenuLine");
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute("cashUser");
        StringBuilder sb = new StringBuilder();

        if (user != null) {
            LOG.debug("Checked user" + user);
            MenuDao menuDao = new JdbcMenuDaoImpl();
            try {
                List<GroupMenuView> groupMenus = menuDao.findAllGroupMenuByLocale(user.getLocaleId());
                Map<String, List<MenuItem>> userMenus = new LinkedHashMap<>();
                for (GroupMenuView gMenu : groupMenus) {
                    int gMenuId = gMenu.getId();
                    int userRoleId = user.getRoleId();
                    int localeId = user.getLocaleId();
                    userMenus.put(gMenu.getName(), menuDao.findRoleMenuItemsFromGroupByLocale(userRoleId, gMenuId, localeId));
                }
                for (Map.Entry<String, List<MenuItem>> stringListEntry : userMenus.entrySet()) {
                    buildDropMenu(sb, stringListEntry.getKey(), stringListEntry.getValue());
                }
            } catch (CashMachineException e) {
                LOG.error("Error current tag MenuLine" + e.getMessage());
            }
        } else {
            LOG.debug("No user");
        }
        try {
            pageContext.getOut().write(sb.toString());
        } catch (IOException e) {
            LOG.error("Error: " + e.getMessage());
        }
        LOG.debug("End custom tag MenuLine");
        return SKIP_BODY;
    }

    private void buildDropMenu(StringBuilder sb, String gName, List<MenuItem> menuItems) {
        if (!menuItems.isEmpty()) {
            sb.append("<li class=\"nav-item dropdown\">")
                    .append("<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">")
                    .append(gName)
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
