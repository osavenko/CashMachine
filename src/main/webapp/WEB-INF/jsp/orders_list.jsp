
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
                <fmt:message key="local.order.date.closed" var="pDate"/>
                <td>${pDate}</td>
                <fmt:message key="local.order.user" var="pAuthor"/>
                <td>${pAuthor}</td>
                <fmt:message key="local.order.amount" var="pAmount"/>
                <td>${pAmount}</td>
                <fmt:message key="local.order.pay" var="pPay"/>
                <td>${pPay}</td>
            </tr>

            <c:forEach var="order" items="${orderList}">
                <tr>
                    <td><c:out value="${order.getId()}"/></td>

                    <td><c:out value="${order.getOrderDateTime()}"/></td>

                    <c:if test="${order.isClosed()==true}">
                        <fmt:message key="local.order.closed.ok" var="pState"/>
                    </c:if>
                    <c:if test="${order.isClosed()==false}">
                        <fmt:message key="local.order.closed.not" var="pState"/>
                    </c:if>
                    <td><c:out value="${pState}"/></td>
                    <td><c:out value="${order.getClosedDateTime()}"/></td>
                    <td><c:out value="${allUsers.get(order.getUserId())}"/></td>
                    <td><c:out value="${order.getAmount()}"/></td>
                    <td>
                        <c:if test="${order.isCash()==true}">
                            Cash
                        </c:if>
                        <c:if test="${order.isCash()==false}">
                            Card
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <fmt:message key="local.common.add" var="pAdd"/>
        <a href="controller?command=addcheck">${pAdd}</a>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
