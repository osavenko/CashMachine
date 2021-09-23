package com.epam.savenko.cashmachine.web;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WebUtil {
    public static int getNumberStartPage(HttpServletRequest req, HttpSession session, Logger LOG) {
        try {
            return Integer.parseInt(req.getParameter("currentPage"));
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
