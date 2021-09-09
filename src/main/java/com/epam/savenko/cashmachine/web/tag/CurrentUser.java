package com.epam.savenko.cashmachine.web.tag;

import com.epam.savenko.cashmachine.dao.LocaleDao;
import com.epam.savenko.cashmachine.dao.RoleDao;
import com.epam.savenko.cashmachine.dao.UserDao;
import com.epam.savenko.cashmachine.dao.UserDetailsDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcRoleDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcUserDetailsDaoImp;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Locale;
import com.epam.savenko.cashmachine.model.Role;
import com.epam.savenko.cashmachine.model.User;
import com.epam.savenko.cashmachine.model.UserDetails;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Optional;

public class CurrentUser extends TagSupport {

    private static final Logger LOG = Logger.getLogger(CurrentUser.class);

    @Override
    public int doStartTag() throws JspException {
        LOG.debug("Start custom tag CurrentUser");
        HttpSession session = pageContext.getSession();
        LOG.debug("Get http session::cashUser");
        User user = (User)session.getAttribute("cashUser");
        if (user != null) {
            LOG.debug("Get http session::userDetails");
            UserDetails userDetails = (UserDetails) session.getAttribute("userDetails");
            LOG.debug("Get http session::userRole");
            Role userRole = (Role)session.getAttribute("userRole");
            LOG.debug("Get http session::userLocale");
            Locale userLocale = (Locale)session.getAttribute("userLocale");
            if ((userLocale == null)||(userRole==null)||(userDetails==null)) {
                Optional<UserDetails> oUserDetails = null;
                Optional<Role> oUserRole = null;
                Optional<Locale> oUserLocale = null;
                try {
                    oUserDetails = new JdbcUserDetailsDaoImp().findByUserId(user.getId());
                    oUserRole = new JdbcRoleDaoImpl().findById(user.getRoleId());
                    oUserLocale = new JdbcLocaleDaoImpl().findById(user.getLocaleId());
                    StringBuilder sb = new StringBuilder();
                    /*
                          <form class="d-flex">
        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success" type="submit">Search</button>
      </form>
                     */

                    sb.append("<span class=\"navbar-text\">");
                    sb.append("<span>"+oUserDetails.get().getFullName()+"</span>");
                    sb.append("<span>"+oUserRole.get().getName()+"</span>");
                    sb.append("<span>"+oUserLocale.get().getName()+"</span>");
                    sb.append("</span>");
                    pageContext.getOut().write(sb.toString());
                } catch (CashMachineException | IOException e) {
                    LOG.error("Error custom tag CurrentUser "+e.getMessage());
                }
            }
        }

        LOG.debug("End custom tag CurrentUser");
        return SKIP_BODY;
    }
}
