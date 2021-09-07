<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cash" uri="/WEB-INF/tld/CashTags.tld" %>

<fmt:setLocale value="ru" scope="session"/>
<fmt:setBundle basename="locale"/>

<fmt:message key="local.login.title" var="title"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<h1>OK</h1>

<fmt:message key="local.lang" var="labelText"/>
<cash:CashLocale shortName="ru" message="${labelText}"/>
<span>После локали</span>
<a href="add_product.jsp">Добавить продукт</a>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
