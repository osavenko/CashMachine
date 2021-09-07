<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cash" uri="/WEB-INF/tld/CashTags.tld" %>

<fmt:setLocale value="ru" scope="session"/>
<fmt:setBundle basename="locale"/>

    <fmt:message key="local.register.title" var="title"/>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <fmt:message key="local.register.title" var="ltitle"/>
    <h2>${ltitle}</h2>

    <fmt:message key="local.register.message" var="lmessage"/>
    <p>${lmessage}</p>
</div>
<form action="controller" method="post">
    <div class="container">
        <input type="hidden" name="command" value="register"/>

        <fmt:message key="local.register.user" var="luser"/>
        <label><b>${luser}</b></label>
        <input type="text" placeholder=${luser} name="login" required>

        <fmt:message key="local.register.password" var="lpassword"/>
        <label><b>${lpassword}</b></label>
        <input type="password" placeholder=${lpassword} name="password" required>

        <fmt:message key="local.register.fullname" var="lfullname"/>
        <label><b>${lfullname}</b></label>
        <input type="text" placeholder=${lfullname} name="fullname" required>

        <fmt:message key="local.register.role" var="lrole"/>
        <cash:SelectRole name="role" localeMessage="${lrole}"/>

        <fmt:message key="local.register.locale" var="llocale"/>
        <cash:SelectLocale name="locale" localeMessage="${llocale}"/>

        <fmt:message key="local.register.button" var="lbutton"/>
        <button type="submit">${lbutton}</button>
    </div>
</form>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
