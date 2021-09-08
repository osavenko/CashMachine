package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.MenuItem;

import java.util.List;

public interface MenuDao extends GeneralDao<MenuItem,Integer>{
    public List<MenuItem> findRoleMenuItemsFromGroupByLocale(int roleId, int groupMenuId, int localeId) throws CashMachineException;
}
