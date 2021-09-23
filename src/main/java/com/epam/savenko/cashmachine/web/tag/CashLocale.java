package com.epam.savenko.cashmachine.web.tag;

import com.epam.savenko.cashmachine.dao.LocaleDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Locale;
import com.epam.savenko.cashmachine.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Optional;

public class CashLocale extends TagSupport {

    public static final Logger LOG = Logger.getLogger(CashLocale.class);
    private static final long serialVersionUID = 5044605011941322483L;

    private String shortName;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        LocaleDao localeDao = new JdbcLocaleDaoImpl();
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute("cashUser");
        try {
            Optional<Locale> oLocale;
            if (user != null) {
                oLocale = localeDao.findById(user.getLocaleId());
            } else {
                oLocale = localeDao.findByName(shortName);
            }
            out.write("<span>");
            out.write(message);
            out.write(": ");
            if (oLocale.isPresent()) {
                out.write(oLocale.get().getDescription());
            } else {
                out.write("Unknown language");
            }
            out.write("</span>");
        } catch (IOException | CashMachineException e) {
            LOG.error("Error cash locale", e);
        }
        return SKIP_BODY;
    }
}
