package com.epam.savenko.cashmachine.web.tag;

import com.epam.savenko.cashmachine.dao.jdbc.JdbcUserDetailsDaoImp;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.User;
import com.epam.savenko.cashmachine.model.UserDetails;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Optional;

public class CurrentUser extends TagSupport {

    private static final Logger LOG = Logger.getLogger(CurrentUser.class);
    private static final long serialVersionUID = -1980870781200046687L;

    @Override
    public int doStartTag() {
        LOG.debug("Start custom tag CurrentUser");
        HttpSession session = pageContext.getSession();
        LOG.debug("Get http session::cashUser");
        User user = (User) session.getAttribute("cashUser");
        if (user != null) {
            try {
                Optional<UserDetails> oUserDetails = new JdbcUserDetailsDaoImp().findByUserId(user.getId());
                if (oUserDetails.isPresent()) {
                    StringBuilder sb = new StringBuilder()
                            .append("<span class=\"navbar-text\">")
                            .append("<span>")
                            .append(oUserDetails.get().getFullName())
                            .append("</span>")
                            .append("</span>");
                    pageContext.getOut().write(sb.toString());
                }
            } catch (CashMachineException | IOException e) {
                LOG.error("Error custom tag CurrentUser " + e.getMessage());
            }
        }

        LOG.debug("End custom tag CurrentUser");
        return SKIP_BODY;
    }
}
