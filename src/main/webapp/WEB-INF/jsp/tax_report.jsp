<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>
</div>
<div class="container">
<%--
    public static final String STORE_NAME = "reportStoreName";
    public static final String ADDRESS= "reportAddress";
    public static final String IPN= "reportIPN";
--%>


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
    <p class="text-center">${pType}</p>

    <table class="report">
        <tr>
            <td class="text-left" colspan="2">
                <fmt:message key="local.report.info.sell" var="pSell"/>
                <span class="text-left">${pSell}</span>
            </td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.check.count" var="pCount"/>
            <td class="text-left">${pCount}</td>
            <td class="text-right">2</td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.check.cash" var="pCash"/>
            <td class="text-left">${pCash}</td>
            <td class="text-right">2.00</td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.check.card" var="pCard"/>
            <td class="text-left">${pCard}</td>
            <td class="text-right">3.00</td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.total.tax" var="pTotalTax"/>
            <td class="text-left">${pTotalTax}</td>
            <td class="text-right">4.00</td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.total" var="pTotal"/>
            <td class="text-left">${pTotal}</td>
            <td class="text-right">5.00</td>
        </tr>
        <tr><td colspan="2"><br/></td></tr>
        <tr>
            <td class="text-left" colspan="2">
                <fmt:message key="local.report.info.return" var="pReturn"/>
                <span class="text-left">${pSell}</span>
            </td>
        </tr>
        <tr>
            <td class="text-left">${pCount}</td>
            <td class="text-right">2</td>
        </tr>
        <tr>
            <td class="text-left">${pCash}</td>
            <td class="text-right">2.00</td>
        </tr>
        <tr>
            <td class="text-left">${pCard}</td>
            <td class="text-right">3.00</td>
        </tr>
        <tr>
            <td class="text-left">${pTotalTax}</td>
            <td class="text-right">4.00</td>
        </tr>
        <tr>
            <td class="text-left">${pTotal}</td>
            <td class="text-right">5.00</td>
        </tr>
        <tr><td colspan="2"><br/></td></tr>
        <tr>
            <fmt:message key="local.report.info.official.payment" var="pPayment"/>
            <td class="text-left">${pPayment}</td>
            <td class="text-right">0.21</td>
        </tr>
        <tr>
            <fmt:message key="local.report.info.official.issuance" var="pIssuance"/>
            <td class="text-left">${pIssuance}</td>
            <td class="text-right">0.22</td>
        </tr>
        <tr><td colspan="2"></td></tr>
    </table>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
