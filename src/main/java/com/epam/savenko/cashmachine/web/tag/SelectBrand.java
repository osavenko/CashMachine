package com.epam.savenko.cashmachine.web.tag;

import com.epam.savenko.cashmachine.dao.jdbc.JdbcBrandDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Brand;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class SelectBrand extends TagSupport {

    private static final Logger LOG = Logger.getLogger(SelectBrand.class);

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
        LOG.debug("Start custom tag select brands");
        List<Brand> brands = null;
        try {
            brands = new JdbcBrandDaoImpl().findAll();
            LOG.debug("Get " + brands.size() + " brands");
        } catch (CashMachineException e) {
            LOG.debug("Error selected brands");
        }
        if (brands != null) {
            JspWriter out = pageContext.getOut();
            try {
                out.write("<select name=\"" + name + "\" required>");
                out.write("<option disabled>" + localeMessage + "</option>");
                for (Brand brand : brands) {
                    out.write("<option value=\"" + brand.getId() + "\">" + brand.getName() + "</option>");
                }
                out.write("</select>");
            } catch (IOException e) {
                LOG.debug("Error make select brand");
            }
        }
        return SKIP_BODY;
    }
}
