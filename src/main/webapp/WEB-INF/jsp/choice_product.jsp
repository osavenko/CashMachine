<%@ include file="/WEB-INF/jspf/head.jspf" %>
<div class="container">
    <br/>
    <form class="form-inline" action="" method="get">
        <div class="input-group">
            <input type="hidden" name="command" value="choiceproduct"/>
            <input name="search" type="text" class="form-control" placeholder="Search">
            <div id="spanSearch" class="invalid-feedback"></div>
            <button class="btn btn-default" type="submit">
                <i class="bi bi-search"></i>
            </button>
        </div>
    </form>
</div>
<div class="container">
    <fmt:message key="local.products.count" var="pCount"/>
    <span>${pCount}: ${productCount}</span>
    <table class="table">
        <tr class="table-primary">
            <fmt:message key="local.order.number" var="pCode"/>
            <td>${pName}</td>
            <fmt:message key="local.product.name" var="pName"/>
            <td>${pName}</td>
            <fmt:message key="local.product.type" var="pWeight"/>
            <td>${pWeight}</td>
            <fmt:message key="local.product.quantity" var="pQuantity"/>
            <td>${pQuantity}</td>
            <fmt:message key="local.product.quantity.inorder" var="pQuantityInOrder"/>
            <td>${pQuantityInOrder}</td>
            <fmt:message key="local.product.price" var="pPrice"/>
            <td>${pPrice}</td>
            <fmt:message key="local.product.description" var="pDescription"/>
            <td></td>
        </tr>

        <c:forEach var="entry" items="${products}">
            <tr>
                <td><c:out value="${entry.key.getId()}"/></td>
                <td><c:out value="${entry.key.getName()}"/></td>
                <c:if test="${entry.key.isWeight()==true}">
                    <fmt:message key="local.product.weight.weight" var="pWeightMessage"/>
                    <td><c:out value="${pWeightMessage}"/></td>
                </c:if>
                <c:if test="${entry.key.isWeight()==false}">
                    <fmt:message key="local.product.weight.quantitative" var="pWeightMessage"/>
                    <td><c:out value="${pWeightMessage}"/></td>
                </c:if>
                <c:if test="${entry.key.isWeight()==true}">
                    <td><c:out value="${entry.key.getQuantity()/1000}"/></td>
                    <td><c:out value="${entry.value/1000}"/></td>
                </c:if>
                <c:if test="${entry.key.isWeight()==false}">
                    <td><c:out value="${entry.key.getQuantity()}"/></td>
                    <td><c:out value="${entry.value}"/></td>
                </c:if>
                <td><c:out value="${entry.key.getPrice()}"/></td>
                <td><c:out value=""/>
                    <form class="row" action="" method="post">
                        <input type="hidden" name="command" value="addToTovList"/>
                        <input type="hidden" name="currPrice" value="${entry.key.getPrice()}"/>
                        <input type="hidden" name="isWeight" value="${entry.key.isWeight()}"/>
                        <div class="col">
                            <c:if test="${entry.key.isWeight()==true}">
                                <input class="form-control" type="number" name="tov${entry.key.getId()}"
                                       min="${-entry.value/1000}"
                                       step="0.001"
                                       max="${entry.key.getQuantity()/1000}" value="0.000" required/>
                            </c:if>
                            <c:if test="${entry.key.isWeight()==false}">
                                <input class="form-control" type="number" name="tov${entry.key.getId()}"
                                       min="${-entry.value}"
                                       step="1"
                                       max="${entry.key.getQuantity()}" value="0" required/>
                            </c:if>
                        </div>
                        <div class="col">
                            <button class="btn btn-outline-secondary" type="submit">+/-</button>
                        </div>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class="container">
    <fmt:message key="local.common.add" var="pAdd"/>
    <a href="controller?command=addcheck">${pAdd}</a>
    <nav class="container" aria-label="Page navigation">
        <ul class="pagination">
            <c:forEach begin="1" end="${pages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active"><a class="page-link">
                                ${i}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link"
                                                 href="${currUrl}&currentPage=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </nav>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>