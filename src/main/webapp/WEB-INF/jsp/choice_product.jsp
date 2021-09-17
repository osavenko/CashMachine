<%@ include file="/WEB-INF/jspf/head.jspf" %>
<div class="container">
    <span>${pCount}: ${sessionScope.get("productCount")}</span>
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
            <fmt:message key="local.product.description" var="pDescription"/>
            <td ></td>
        </tr>

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
                <td><c:out value=""/>
                    <form class="row" action="" method="post">
                        <input type="hidden" name="command" value="addToTovList"/>
                        <input type="hidden" name="currPrice" value="${product.getPrice()}"/>
                        <input type="hidden" name="isWeight" value="${product.isWeight()}"/>
                        <div class="col">
                            <c:if test="${product.isWeight()==true}">
                                <input class="form-control" type="number" name="tov${product.getId()}" min="0.000"
                                       step="0.001"
                                       max="${product.getQuantity()/1000}" value="0.000" required/>
                            </c:if>
                            <c:if test="${product.isWeight()==false}">
                                <input class="form-control" type="number" name="tov${product.getId()}" min="0"
                                       step="1"
                                       max="${product.getQuantity()}" value="0" required/>
                            </c:if>
                        </div>
                        <div class="col">
                            <button class="btn btn-outline-secondary" type="submit">+</button>
                        </div>
                    </form>
                </td>
            </tr>
        </c:forEach>

    </table>
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