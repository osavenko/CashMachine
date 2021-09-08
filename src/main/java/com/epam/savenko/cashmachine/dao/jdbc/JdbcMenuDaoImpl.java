package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.*;
import com.epam.savenko.cashmachine.dao.jdbc.util.ErrorMessage;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.MenuItem;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public static final EntityMapper<MenuItem> mapRow = resultSet ->
            new MenuItem(resultSet.getInt(ID), resultSet.getString(NAME), resultSet.getString(URL), 0);

    private static final EntitiesMapper<MenuItem> mapMenuItemsRows = resultSet -> {
        List<MenuItem> menuItems = new ArrayList<>();
        while (resultSet.next()) {
            menuItems.add(mapRow.mapRow(resultSet));
        }
        return menuItems;
    };

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

    private List<MenuItem> getMenuItemsByStatement(PreparedStatement statement) throws SQLException {
        ResultSet rs = statement.executeQuery();
        List<MenuItem> menuItems = mapMenuItemsRows.mapList(rs);
        return menuItems;
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
