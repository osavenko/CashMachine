package com.epam.savenko.cashmachine.web.servlets.command;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Logger LOG = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<>();

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("main", new MainPageCommand());
        commands.put("register", new RegisterCommand());
        commands.put("productslist", new ProductsListCommand());
        commands.put("orderslist", new OrdersListCommand());
        commands.put("cancelcheck", new CancelCheckCommand());
        commands.put("addcheck", new AddCheckCommand());
        commands.put("choiceproduct", new RedirectCommand());
        commands.put("addProduct", new AddProductCommand());
        commands.put("addproductpage", new AddProductPageCommand());
        commands.put("savecheck", new SaveCheckCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("xreport", new XReportCommand());
        commands.put("zreport", new ZReportCommand());

        LOG.debug("Command container was successfully initialized");
        LOG.trace("Number of commands --> " + commands.size());
    }
    /**
     * Returns command object with the given name.
     *
     * @param commandName
     *            Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }

}
