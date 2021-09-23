package com.epam.savenko.cashmachine.dao.jdbc.util;

public class ErrorMessage {

    private ErrorMessage() {
    }

    public static String getInsert(String tableName) {
        return "Error while insert " + tableName + " with name =";
    }

    public static String getDelete(String tableName) {
        return "Error while deleting " + tableName + " with id=";
    }

    public static String getUpdate(String tableName) {
        return "Error while updating " + tableName + " with id=";
    }

    public static String getReceiveAll(String tableName) {
        return "Error when receiving " + tableName;
    }

    public static String getReceiveById(String tableName) {
        return "Error when receiving " + tableName + " with id=";
    }

    public static String getReceiveByUserId(String tableName) {
        return "Error when receiving " + tableName + " with user_id=";
    }

    public static String getCheckUser() {
        return "Error when check password for login ";
    }

    public static String getCheckLocaleByName() {
        return "Error when check locale by name: ";
    }

    public static String getSetPassword() {
        return "Error when set password for user: ";
    }

    public static String getReceiveMenuItems() {
        return "Error when receiving menu items";
    }
}
