package com.epam.savenko.cashmachine.web.tag;

import com.epam.savenko.cashmachine.dao.LocaleDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Optional;

public class CashLocale extends TagSupport {

    private String shortName;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out= pageContext.getOut();
        LocaleDao localeDao = new JdbcLocaleDaoImpl();

        Optional<Locale> oLocale = null;
        try {
            oLocale = localeDao.findByName(shortName);
            out.write("<span>");
            out.write(message);
            out.write(": ");
            if (oLocale.isPresent()) {
                out.write(oLocale.get().getDescription());
            }else {
                out.write("Unknown language");
            }
            out.write("</span>");
        } catch (IOException | CashMachineException e) {

        }
        return SKIP_BODY;
    }
}
