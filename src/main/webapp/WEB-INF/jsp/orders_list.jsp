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
                        <td>
                            <i class="bi bi-file-earmark-check"></i>
                        </td>
                    </c:if>
                    <c:if test="${order.isClosed()==false}">
                        <td>
                            <i class="bi bi-file-earmark"></i>
                        </td>
                    </c:if>
                    <td><c:out value="${order.getClosedDateTime()}"/></td>
                    <td><c:out value="${allUsers.get(order.getUserId())}"/></td>
                    <td><c:out value="${order.getAmount()}"/></td>
                    <td>
                        <c:if test="${order.isCash()==true}">
                            <i class="bi bi-cash-coin"></i>
                        </c:if>
                        <c:if test="${order.isCash()==false}">
                            <i class="bi bi-credit-card-2-front"></i>
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
