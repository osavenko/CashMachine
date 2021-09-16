package com.epam.savenko.cashmachine.web.servlets;

public class RoutePath {
    private String path;
    private RouteType routeType;

    public RoutePath() {
    }

    public RoutePath(String path, RouteType routeType) {
        this.path = path;
        this.routeType = routeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    @Override
    public String toString() {
        return path;
    }
}
