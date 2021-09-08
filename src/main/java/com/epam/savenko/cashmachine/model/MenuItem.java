package com.epam.savenko.cashmachine.model;

import java.io.Serializable;
import java.util.Objects;


public class MenuItem implements Serializable {

    private static final long serialVersionUID = -8052717055668574796L;

    private int id;
    private String name;
    private String url;
    private int groupId;

    public MenuItem() {
    }

    public MenuItem(int id, String name, String url, int groupId) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuItem menuItem = (MenuItem) o;
        return id == menuItem.id && groupId == menuItem.groupId && name.equals(menuItem.name) && url.equals(menuItem.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url, groupId);
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", groupId=" + groupId +
                '}';
    }
}
