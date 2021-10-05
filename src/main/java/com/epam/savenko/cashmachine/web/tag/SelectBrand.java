package com.epam.savenko.cashmachine.web.tag;

import com.epam.savenko.cashmachine.dao.jdbc.JdbcBrandDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Brand;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class SelectBrand extends TagSupport {

    private static final Logger LOG = Logger.getLogger(SelectBrand.class);
    private static final long serialVersionUID = -3438080619100682939L;

    private String name;
    private String localeMessage;
    private String defaultId;

    public void setDefaultId(String defaultId) {
        this.defaultId = defaultId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocaleMessage(String localeMessage) {
        this.localeMessage = localeMessage;
    }

    @Override
    public int doStartTag() {
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
                out.write("<select class=\"form-select\" name=\"" + name + "\" required>");
                out.write("<option disabled>" + localeMessage + "</option>");
                int id = getDefaultId();
                for (Brand brand : brands) {
                    if (id == brand.getId()) {
                        out.write("<option value=\"" + brand.getId() + " \" selected>" + brand.getName() + "</option>");
                    } else {
                        out.write("<option value=\"" + brand.getId() + "\">" + brand.getName() + "</option>");
                    }
                }
                out.write("</select>");
            } catch (IOException e) {
                LOG.debug("Error make select brand");
            }
        }
        return SKIP_BODY;
    }

    private int getDefaultId() {
        if (defaultId == null) {
            return 0;
        }
        if (defaultId.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(defaultId);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
