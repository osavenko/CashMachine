package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.LocaleProduct;
import com.epam.savenko.cashmachine.model.view.ProductDescriptionView;

import java.util.List;
import java.util.Optional;

public interface LocaleProductDao extends GeneralDao<LocaleProduct, Integer> {
    Optional<LocaleProduct> findDescriptionProductByLocale(int idProduct, int localeId) throws CashMachineException;

    List<ProductDescriptionView> getAllDescriptionViewForProductById(int idProduct) throws CashMachineException;
}
