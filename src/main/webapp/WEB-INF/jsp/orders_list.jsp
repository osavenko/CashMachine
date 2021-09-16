
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

    <div>

        <fmt:message key="local.order.count" var="pCount"/>
        <span>${pCount}: ${sessionScope.get("ordersCount")}</span>

        <table class="table">
            <tr class="table-primary">
                <fmt:message key="local.order.number" var="pNumber"/>
                <td>${pNumber}</td>
                <fmt:message key="local.order.date" var="pDate"/>
                <td>${pDate}</td>
                <fmt:message key="local.order.closed" var="pMarkOfClosed"/>
                <td>${pMarkOfClosed}</td>
                <fmt:message key="local.order.user" var="pAuthor"/>
                <td>${pAuthor}</td>
                <fmt:message key="local.order.amount" var="pAmount"/>
                <td>${pAmount}</td>
            </tr>

<%--
            <c:forEach var="product" items="${products}">
                <tr>
                    <td><c:out value="${product.getName()}"/></td>
                    <c:if test="${product.isWeight()==true}">
                        <fmt:message key="local.product.weight.message" var="pWeightMessage"/>
                        <td><c:out value="${pWeightMessage}"/></td>
                    </c:if>
                    <c:if test="${product.isWeight()==false}">
                        <fmt:message key="local.product.weight.quantitative" var="pWeightMessage"/>
                        <td><c:out value="${pWeightMessage}"/></td>
                    </c:if>
                    <c:if test="${product.isWeight()==true}">
                        <td><c:out value="${product.getQuantity()/1000}"/></td>
                    </c:if>
                    <c:if test="${product.isWeight()==false}">
                        <td><c:out value="${product.getQuantity()}"/></td>
                    </c:if>

                    <td><c:out value="${product.getPrice()}"/></td>
                    <td><c:out value=""/></td>
                </tr>
            </c:forEach>
--%>

        </table>
        <fmt:message key="local.common.add" var="pAdd"/>
        <a href="controller?command=addcheck">${pAdd}</a>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
