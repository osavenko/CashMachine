
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <fmt:message key="local.add.check.message" var="message"/>
    <h2>${message}</h2>
</div>
<div class="container">
    <table class="table">
        <tr class="table-primary">
            <fmt:message key="local.product.name" var="pName"/>
            <td>${pName}</td>
            <fmt:message key="local.product.type" var="pWeight"/>
            <td>${pWeight}</td>
            <fmt:message key="local.product.quantity" var="pQuantity"/>
            <td>${pQuantity}</td>
            <fmt:message key="local.product.price" var="pPrice"/>
            <td>${pPrice}</td>
        </tr>
        <c:forEach var="product" items="${orderView.getProductInOrderViewList()}">
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
    </table>
    <fmt:message key="local.order.amount" var="pSummaMessage"/>
    <label class="input-group-addon"><b>${pSummaMessage}</b></label>
</div>
<div class="container">
    <div class="input-group">
        <fmt:message key="local.add.product.button" var="pAdd"/>
        <a class="btn btn-primary" role="button" href="#">${pAdd}</a>
        <fmt:message key="local.common.save" var="pSave"/>
        <a class="btn btn-outline-success" role="button" href="#">${pSave}</a>
        <fmt:message key="local.common.cancel" var="pCancel"/>
        <a class="btn btn-outline-warning" role="button" href="#">${pCancel}</a>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
