package com.epam.savenko.cashmachine.web.tag;

import com.epam.savenko.cashmachine.dao.jdbc.JdbcRoleDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Role;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class SelectRole extends TagSupport {

    private static final Logger LOG = Logger.getLogger(SelectRole.class);

    private String name;
    private String localeMessage;

    public void setName(String name) {
        this.name = name;
    }

    public void setLocaleMessage(String localeMessage) {
        this.localeMessage = localeMessage;
    }

    @Override
    public int doStartTag() throws JspException {
        LOG.debug("Start custom tag select role");
        List<Role> roles = null;
        try {
            roles = new JdbcRoleDaoImpl().findAll();
            LOG.debug("Get " + roles.size() + " roles");
        } catch (CashMachineException e) {
            LOG.debug("Error selected roles");
        }
        if (roles != null) {
            JspWriter out = pageContext.getOut();
            try {
                out.write("<select name=\"" + name + "\" required>");
                out.write("<option disabled>" + localeMessage + "</option>");
                for (Role role : roles) {
                    out.write("<option value=\"" + role.getId() + "\">" + role.getName() + "</option>");
                }
                out.write("</select>");
            } catch (IOException e) {
                LOG.debug("Error make select role");
            }
        }

        return SKIP_BODY;
    }
}
