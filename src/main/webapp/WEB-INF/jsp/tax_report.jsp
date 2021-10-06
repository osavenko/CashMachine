<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>
</div>
<div class="container">
    <p class="text-center">${reportStoreName}</p>
    <p class="text-center">${reportAddress}</p>
    <p class="text-center">${reportIPN}</p>

    <c:if test="${reportType=='x'}">
        <fmt:message key="local.report.xtype" var="pType"/>
    </c:if>
    <c:if test="${reportType=='z'}">
        <fmt:message key="local.report.ztype" var="pType"/>
    </c:if>

    <br/>
    <p class="text-center"><strong>${pType}</strong></p>

    <table class="report">
        <tr>
            <td class="text-left" colspan="2">
                <fmt:message key="local.report.info.sell" var="pSell"/>
                <p class="text-left"><strong>${pSell}</strong></p>
            </td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.check.count" var="pCount"/>
            <td class="text-left">${pCount}</td>
            <td class="text-right">${ordersCount}</td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.check.cash" var="pCash"/>
            <td class="text-left">${pCash}</td>
            <td class="text-right">${ordersSumCash}</td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.check.card" var="pCard"/>
            <td class="text-left">${pCard}</td>
            <td class="text-right">${ordersSumCard}</td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.total.tax" var="pTotalTax"/>
            <td class="text-left">${pTotalTax}</td>
            <td class="text-right">${orderTax}</td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.total" var="pTotal"/>
            <td class="text-left">${pTotal}</td>
            <td class="text-right">${orderTotal}</td>
        </tr>
        <tr>
            <td colspan="2"><br/></td>
        </tr>
        <tr>
            <td class="text-left" colspan="2">
                <fmt:message key="local.report.info.return" var="pReturn"/>
                <p class="text-left"><strong>${pReturn}</strong></p>
            </td>
        </tr>
        <tr>
            <td class="text-left">${pCount}</td>
            <td class="text-right">0</td>
        </tr>
        <tr>
            <td class="text-left">${pCash}</td>
            <td class="text-right">0.00</td>
        </tr>
        <tr>
            <td class="text-left">${pCard}</td>
            <td class="text-right">0.00</td>
        </tr>
        <tr>
            <td class="text-left">${pTotalTax}</td>
            <td class="text-right">0.00</td>
        </tr>
        <tr>
            <td class="text-left">${pTotal}</td>
            <td class="text-right">0.00</td>
        </tr>
        <tr>
            <td colspan="2"><br/></td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.official.payment" var="pPayment"/>
            <td class="text-left">${pPayment}</td>
            <td class="text-right">0.00</td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.official.issuance" var="pIssuance"/>
            <td class="text-left">${pIssuance}</td>
            <td class="text-right">0.00</td>
        </tr>
        <tr>
            <td colspan="2"><br/></td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.cashier" var="pIssuance"/>
            <td>${pIssuance}</td>
            <td>
                <c:forEach var="userFullName" items="${ordersUsers}">
                    ${userFullName}<br/>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td colspan="2"><br/></td>
        </tr>
        <tr>
            <td colspan="2"><br/></td>
        </tr>
        <fmt:message key="local.report.info.date" var="pDate"/>
        <fmt:message key="local.report.info.time" var="pTime"/>
        <tr>
            <td><span class="text-left">${pDate}:${reportDate}</span></td>
            <td><span class="text-left">${pTime}:${reportTime}</span></td>
        </tr>
        <fmt:message key="local.cash.register" var="pCashRegisterNumber"/>
        <tr>
            <td><span class="text-left">${pCashRegisterNumber} ${reportCashRegisterNumber}</span></td>
            <td></td>
        </tr>
    </table>
</div>

<c:if test="${reportType=='z'}">
    <div class="container">
        <form action="controller" method="post">
            <input type="hidden" name="command" value="fixreport"/>
            <fmt:message key="local.button.fix" var="lbutton"/>
            <button class="btn btn-primary" type="submit">${lbutton}</button>
        </form>
    </div>
</c:if>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
