package com.epam.savenko.cashmachine.web.filter;

import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcUserDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Locale;
import com.epam.savenko.cashmachine.model.User;
import com.epam.savenko.cashmachine.web.constant.SessionParam;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.savenko.cashmachine.web.constant.SessionParam.USER;

@WebFilter(filterName = "LanguageFilter")
public class LanguageFilter implements Filter {
    public static final Logger LOG = Logger.getLogger(LanguageFilter.class);
    private static List<Locale> locales;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            locales = new JdbcLocaleDaoImpl().findAll();
        } catch (CashMachineException e) {
            LOG.debug("Error get data about locales ");
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        LOG.debug("Start LanguageFilter");
        req.setAttribute(SessionParam.LANGUAGES, locales.stream()
                .map(locale -> locale.getName())
                .collect(Collectors.toList()));
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        String choiceLanguage = (String) session.getAttribute(SessionParam.LANGUAGE);
        LOG.debug("Current language: " + choiceLanguage);
        if (choiceLanguage != null) {
            LOG.debug("choiceLanguage: " + choiceLanguage);
            choiceLanguage = req.getParameter(SessionParam.CHOICE_LANGUAGE);
            if (choiceLanguage != null) {
                session.setAttribute(SessionParam.LANGUAGE, choiceLanguage);
                User user = (User) session.getAttribute(USER);
                if (user != null) {
                    try {
                        String currLang = choiceLanguage;
                        int localeId = locales.stream()
                                .filter(locale -> locale.getName().equals(currLang))
                                .collect(Collectors.toList()).get(0).getId();
                        user.setLocaleId(localeId);
                        new JdbcUserDaoImpl().update(user);
                    } catch (CashMachineException e) {
                        LOG.error("Error when update locale to user ", e);
                    }
                }
                LOG.debug("Set " + SessionParam.LANGUAGE + " " + choiceLanguage);
            }
        } else {
            session.setAttribute(SessionParam.LANGUAGE, "en");
            LOG.debug("Set " + SessionParam.LANGUAGE + " en");
        }
        LOG.debug("End LanguageFilter");
        chain.doFilter(req, resp);
    }
}
