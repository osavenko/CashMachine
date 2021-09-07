<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="ru" scope="session"/>
<fmt:setBundle basename="locale"/>

<fmt:message key="local.badpage.bad" var="title"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <fmt:message key="local.login.bad.message" var="message"/>
    <a href="index.jsp">${message}</a>
</div>
<div class="container">
    <span>${errorMessage}</span>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
