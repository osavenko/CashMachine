package com.epam.savenko.cashmachine.web.tag;

import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Locale;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class SelectLocale extends TagSupport {

    private static final Logger LOG = Logger.getLogger(SelectLocale.class);

    private String name;
    private String defaultLocale;
    private String localeMessage;

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocaleMessage(String localeMessage) {
        this.localeMessage = localeMessage;
    }

    @Override
    public int doStartTag() throws JspException {
        LOG.debug("Start custom tag select locales");
        List<Locale> locales = null;
        try {
            locales = new JdbcLocaleDaoImpl().findAll();
            LOG.debug("Get " + locales.size() + " locales");
        } catch (CashMachineException e) {
            LOG.debug("Error selected locales");
        }
        if (locales != null) {
            JspWriter out = pageContext.getOut();
            try {
                out.write("<select name=\"" + name + "\" required>");
                out.write("<option disabled>" + localeMessage + "</option>");
                for (Locale locale : locales) {
                    LOG.debug("SelectLocale defaultLocale:"+defaultLocale);
                    if (defaultLocale != null && !defaultLocale.isEmpty()) {
                        if (defaultLocale.equals(locale.getName())) {
                            out.write("<option value=\"" + locale.getName() + "\">" + locale.getDescription() + "</option>");
                            break;
                        }
                    }else{
                        out.write("<option value=\"" + locale.getName() + "\">" + locale.getDescription() + "</option>");
                    }
                }
                out.write("</select>");
            } catch (IOException e) {
                LOG.debug("Error make select locale");
            }
        }
        return SKIP_BODY;
    }
}
