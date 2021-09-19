package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.*;
import com.epam.savenko.cashmachine.dao.jdbc.util.ErrorMessage;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.GroupMenuView;
import com.epam.savenko.cashmachine.model.MenuItem;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.savenko.cashmachine.dao.Fields.MenuItem.*;

public class JdbcMenuDaoImpl implements MenuDao {

    private static final Logger LOG = Logger.getLogger(JdbcMenuDaoImpl.class.getName());

    private static final String TABLE_NAME = "menu_item";

    private static final String SQL_SELECT_MENU_ITEMS =
            "SELECT im.id AS id, lim.name AS name, im.url AS url"
                    + " FROM public.access_menu_item AS amt"
                    + " JOIN public.item_menu AS im ON amt.item_menu_id=im.id"
                    + " JOIN public.locale_item_menu AS lim ON lim.item_menu_id=im.id"
                    + " WHERE amt.role_id=? AND lim.locale_id=? AND im.group_menu_id=?";

    public static final String SQL_SELECT_ALL_ROLE = "SELECT role_id FROM access_menu_item  GROUP BY role_id";
    public static final String SQL_SELECT_COMMAND_BY_ROLE =
            "SELECT im.url AS url FROM access_menu_item AS ta"
                    + " INNER JOIN item_menu im ON im.id = ta.item_menu_id"
                    + " WHERE ta.role_id=?";
    public static final String SQL_GROUP_MENU_BY_LOCALE_ID = "SELECT gm.id AS id, lgm.name AS name FROM group_menu gm\n" +
            "JOIN locale_group_menu lgm on gm.id = lgm.group_menu_id\n" +
            "WHERE lgm.locale_id=?\n"+
            "ORDER BY gm.id";


    public static final EntityMapper<MenuItem> mapRow = resultSet ->
            new MenuItem(resultSet.getInt(ID), resultSet.getString(NAME), resultSet.getString(URL), 0);

    private static final EntitiesMapper<MenuItem> mapMenuItemsRows = resultSet -> {
        List<MenuItem> menuItems = new ArrayList<>();
        while (resultSet.next()) {
            menuItems.add(mapRow.mapRow(resultSet));
        }
        return menuItems;
    };

    public static final EntityMapper<GroupMenuView> mapGroupRow = rs ->
            new GroupMenuView(rs.getInt("id"), rs.getString("name"));
    public static final EntitiesMapper<GroupMenuView> mapGroupRows  =rs -> {
        List<GroupMenuView> list = new ArrayList<>();
        while (rs.next()){
            list.add(mapGroupRow.mapRow(rs));
        }
        return list;
    };


    public static final String PATTERN_FIND_COMMAND = "command=(?<cmd>[a-z]+)";

    @Override
    public List<MenuItem> findRoleMenuItemsFromGroupByLocale(int roleId, int groupMenuId, int localeId) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_SELECT_MENU_ITEMS)) {
            statement.setInt(1, roleId);
            statement.setInt(2, localeId);
            statement.setInt(3, groupMenuId);
            return getMenuItemsByStatement(statement);
        } catch (SQLException | CashMachineException e) {
            LOG.error(ErrorMessage.getReceiveMenuItems(), e);
            throw new CashMachineException(ErrorMessage.getReceiveMenuItems(), e);
        }
    }

    @Override
    public List<String> findCommandByRole(int roleId) throws CashMachineException {
        List<String> commands = new ArrayList<>();
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_SELECT_COMMAND_BY_ROLE)) {
            statement.setInt(1, roleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String url = getCommandByPattern(resultSet.getString(1), PATTERN_FIND_COMMAND);
                    commands.add(url);
                }
            }
        } catch (SQLException | CashMachineException e) {
            LOG.error(ErrorMessage.getReceiveMenuItems(), e);
            throw new CashMachineException(ErrorMessage.getReceiveMenuItems(), e);
        }
        return commands;
    }

    @Override
    public List<Integer> findAllRolesInAccessMenuItem() throws CashMachineException {
        List<Integer> commands = new ArrayList<>();
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             Statement statement = con.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ROLE)) {
                while (resultSet.next()) {
                    commands.add(resultSet.getInt(1));
                }
            }
        } catch (SQLException | CashMachineException e) {
            LOG.error(ErrorMessage.getReceiveMenuItems(), e);
            throw new CashMachineException(ErrorMessage.getReceiveMenuItems(), e);
        }
        return commands;
    }

    @Override
    public List<GroupMenuView> findAllGroupMenuByLocale(int locale_id) throws CashMachineException {
        List<GroupMenuView> list = new ArrayList<>();
        try(Connection conn = ConnectionProvider.getInstance().getConnection();
        PreparedStatement statement = conn.prepareStatement(SQL_GROUP_MENU_BY_LOCALE_ID)) {
            statement.setInt(1, locale_id);
            try(ResultSet rs = statement.executeQuery()){
                list = mapGroupRows.mapList(rs);
            }
        } catch (SQLException e) {
            LOG.error("Error receive group menu", e);
            throw new CashMachineException("Error receive group menu", e);
        }
        return list;
    }

    private String getCommandByPattern(String text, String patternLng) {
        StringBuilder rezult = new StringBuilder();
        LOG.debug("Start check command by pattern");
        LOG.debug("Source: " + text);
        LOG.debug("Pattern: " + patternLng);
        Matcher matcher = Pattern.compile(patternLng).matcher(text);
        while (matcher.find()) {
            rezult.append(matcher.group("cmd"));
        }
        LOG.debug("Checked command: " + rezult);
        LOG.debug("End check command by pattern");
        return rezult.toString();
    }

    private List<MenuItem> getMenuItemsByStatement(PreparedStatement statement) throws SQLException {
        ResultSet rs = statement.executeQuery();
        return mapMenuItemsRows.mapList(rs);
    }

    @Override
    public Optional<MenuItem> findById(Integer integer) throws CashMachineException {
        return Optional.empty();
    }

    @Override
    public List<MenuItem> findAll() throws CashMachineException {
        return null;
    }

    @Override
    public Optional<MenuItem> insert(MenuItem entity) throws CashMachineException {
        return Optional.empty();
    }

    @Override
    public boolean update(MenuItem entity) throws CashMachineException {
        return false;
    }

    @Override
    public boolean delete(int id) throws CashMachineException {
        return false;
    }
}
