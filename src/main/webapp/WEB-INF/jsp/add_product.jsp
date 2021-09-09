<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cash" uri="/WEB-INF/tld/CashTags.tld" %>

<fmt:setLocale value="ru" scope="session"/>
<fmt:setBundle basename="locale"/>

<fmt:message key="local.login.title" var="title"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <fmt:message key="local.add.product.message" var="message"/>
    <h2>${message}</h2>
</div>
<br>
<form action="controller" method="post">
    <div class="container">
        <input type="hidden" name="command" value="addProduct"/>

        <fmt:message key="local.product.name" var="pname"/>
        <label><b>${pname}</b></label>
        <input type="text" placeholder=${pname} name="name" required>

        <fmt:message key="local.product.brand" var="pbrand"/>
        <cash:SelectBrand name="brand" localeMessage="${pbrand}"/>

        <fmt:message key="local.product.weight" var="pweight"/>
        <label><b>${pweight}</b></label>
        <input type="checkbox" name="weight">
        <br>
        <fmt:message key="local.product.price" var="pprice"/>
        <label><b>${pprice}</b></label>
        <input type="text" placeholder=${pprice} name="price" required>

        <fmt:message key="local.product.quantity" var="pquantity"/>
        <label><b>${pquantity}</b></label>
        <input type="text" placeholder=${pquantity} name="quantity" required>

        <fmt:message key="local.product.description" var="pdescription"/>
        <label><b>${pdescription}</b></label>
        <fmt:message key="local.register.locale" var="llocale"/>
        <cash:SelectLocale name="locale" localeMessage="${llocale}"/>

        <textarea class="form-control"  rows="3"  name="description" required></textarea>
        <fmt:message key="local.add.product.button" var="lbutton"/>
        <button type="submit">${lbutton}</button>
        <fmt:message key="local.label.back" var="lBack"/>
        <a href="controller?command=main">${lBack}</a>
    </div>
</form>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
