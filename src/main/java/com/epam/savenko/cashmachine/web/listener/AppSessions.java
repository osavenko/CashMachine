package com.epam.savenko.cashmachine.web.listener;

import org.apache.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class AppSessions implements HttpSessionListener{
    private static final Logger LOG = Logger.getLogger(AppSessions.class);

    public AppSessions() {
    }


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LOG.debug("Created session: "+se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        LOG.debug("Destroy session: "+se.getSession().getId());
    }
}
