package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.GroupMenuView;
import com.epam.savenko.cashmachine.model.MenuItem;

import java.util.List;

public interface MenuDao extends GeneralDao<MenuItem, Integer> {
    List<MenuItem> findRoleMenuItemsFromGroupByLocale(int roleId, int groupMenuId, int localeId) throws CashMachineException;

    List<String> findCommandByRole(int roleId) throws CashMachineException;

    List<Integer> findAllRolesInAccessMenuItem() throws CashMachineException;

    List<GroupMenuView> findAllGroupMenuByLocale(int locale_id) throws CashMachineException;
    boolean checkAccessToRoleId(int roleId, int menuId) throws CashMachineException;
}
