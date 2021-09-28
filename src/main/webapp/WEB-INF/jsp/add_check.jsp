<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <fmt:message key="local.add.check.message" var="message"/>
    <h2>${message}</h2>
</div>
<div class="container">

    <p class="text-left"><span>&#8470;:&#32;&#32;&#32;&#32;</span>${orderView.order.getId()}</p>
    <fmt:message key="local.order.date" var="pOrderCreated"/>
    <p class="text-left">${pOrderCreated}<span>: </span>${orderView.order.getOrderDateTime()}</p>

    <form action="" method="post">
        <input type="hidden" name="changePay" value="checkpay"/>
        <span>
            <fmt:message key="local.order.select.pay" var="pPay"/>
            ${pPay}
        </span>
        <br/>
        <div class="form-check">
            <c:if test="${orderView.order.isCash()==true}">
                <input class="form-check-input" type="radio" name="payment" id="idCash" value="cash" checked>
            </c:if>
            <c:if test="${orderView.order.isCash()==false}">
                <input class="form-check-input" type="radio" name="payment" id="idCash" value="cash">
            </c:if>
            <fmt:message key="local.report.info.check.cash" var="pCash"/>
            <label class="form-check-label" for="idCash">${pCash}</label>
        </div>
        <div class="form-check">
            <c:if test="${orderView.order.isCash()==true}">
                <input class="form-check-input" type="radio" name="payment" id="idCard" value="card">
            </c:if>
            <c:if test="${orderView.order.isCash()==false}">
                <input class="form-check-input" type="radio" name="payment" id="idCard" value="card" checked>
            </c:if>
            <fmt:message key="local.report.info.check.card" var="pCard"/>
            <label class="form-check-label" for="idCard">${pCard}</label>
        </div>
        <div>
            <fmt:message key="local.button.fix" var="pFix"/>
            <button type="submit" class="btn btn-primary">${pFix}</button>
        </div>
    </form>
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
            <c:if test="${newCheck==false}">
                <td>
                </td>
            </c:if>
        </tr>
        <c:forEach var="entry" items="${orderView.getProductInOrderViewList()}">
            <tr>
                <td><c:out value="${entry.getName()}"/></td>
                <c:if test="${entry.isWeight()==true}">
                    <fmt:message key="local.product.weight.message" var="pWeightMessage"/>
                    <td><c:out value="${pWeightMessage}"/></td>
                </c:if>
                <c:if test="${entry.isWeight()==false}">
                    <fmt:message key="local.product.weight.quantitative" var="pWeightMessage"/>
                    <td><c:out value="${pWeightMessage}"/></td>
                </c:if>
                <c:if test="${entry.isWeight()==true}">
                    <td><c:out value="${entry.getQuantity()/1000}"/></td>
                </c:if>
                <c:if test="${entry.isWeight()==false}">
                    <td><c:out value="${entry.getQuantity()}"/></td>
                </c:if>

                <td><c:out value="${entry.getPrice()}"/></td>

                <c:if test="${newCheck==false}">
                    <td>
                        <form method="post" action="controller">
                            <input type="hidden" name="delete" value="${entry.getId()}"/>
                            <input type="hidden" name="command" value="deletepio"/>
                            <button class="btn btn-outline-secondary" type="submit"><i class="bi bi-trash"></i></i>
                            </button>
                        </form>
                    </td>
                </c:if>

            </tr>
        </c:forEach>
    </table>
    <fmt:message key="local.order.amount" var="pSummaMessage"/>
    <label class="input-group-addon"><b>${pSummaMessage}</b></label>
</div>
<div class="container">
    <div class="input-group">
        <c:if test="${newCheck==true}">
            <fmt:message key="local.add.product.button" var="pAdd"/>
            <a class="btn btn-primary" role="button" href="controller?command=choiceproduct">${pAdd}</a>
        </c:if>
        <fmt:message key="local.common.save" var="pSave"/>
        <a class="btn btn-outline-success" role="button" href="controller?command=savecheck" href="#">${pSave}</a>
        <fmt:message key="local.common.cancel" var="pCancel"/>
        <a class="btn btn-outline-warning" role="button" href="controller?command=cancelcheck">${pCancel}</a>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
