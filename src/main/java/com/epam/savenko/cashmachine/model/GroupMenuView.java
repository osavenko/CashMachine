package com.epam.savenko.cashmachine.model;

import java.io.Serializable;

public class GroupMenuView implements Serializable {

    private static final long serialVersionUID = -5881332431836322801L;

    private int id;
    private String name;

    public GroupMenuView() {
    }

    public GroupMenuView(int id, String name) {
        this.id = id;
        this.name = name;
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
}
