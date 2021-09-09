<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cash" uri="/WEB-INF/tld/CashTags.tld" %>

<fmt:setLocale value="ru" scope="session"/>
<fmt:setBundle basename="locale"/>

<fmt:message key="local.login.title" var="title"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<h2></h2>
<div>
    <fmt:message key="local.products.count" var="pCount"/>
    <span>${pCount}: ${sessionScope.get("productCount")}</span>
    <table>
        <tr>
            <fmt:message key="local.product.name" var="pName"/>
            <td>${pName}</td>
            <fmt:message key="local.product.weight" var="pWeight"/>
            <td>${pWeight}</td>
            <fmt:message key="local.product.quantity" var="pQuantity"/>
            <td>${pQuantity}</td>
            <fmt:message key="local.product.price" var="pPrice"/>
            <td>${pPrice}</td>
            <fmt:message key="local.product.description" var="pDescription"/>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td><c:out value="${product}"/></td>
                <td><c:out value=""></c:out></td>
                <td><c:out value=""></c:out></td>
                <td><c:out value=""></c:out></td>
                <td><c:out value=""></c:out></td>
            </tr>
        </c:forEach>
    </table>
    <fmt:message key="local.add.product.button" var="lAddProdyct"/>
    <a href="controller?command=addproductpage">${lAddProdyct}</a>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>

