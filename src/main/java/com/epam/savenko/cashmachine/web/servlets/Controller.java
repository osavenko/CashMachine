package com.epam.savenko.cashmachine.web.servlets;

import com.epam.savenko.cashmachine.web.servlets.command.Command;
import com.epam.savenko.cashmachine.web.servlets.command.CommandContainer;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "mainController", value = "/controller")
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1973506113535232497L;

    private static final Logger LOG = Logger.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");
        String commandName = req.getParameter("command");
        LOG.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        LOG.trace("Obtained command --> " + command);
        // execute command and get forward address
        String forward = command.execute(req, resp);
        LOG.trace("Forward address --> " + forward);

        LOG.debug("Controller finished, now go to forward address --> " + forward);
        // if the forward address is not null go to the address
        if (forward != null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher(forward);
            dispatcher.forward(req, resp);
        }
    }

}
