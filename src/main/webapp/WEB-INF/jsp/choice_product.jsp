<%@ include file="/WEB-INF/jspf/head.jspf" %>
<div class="container">
    <br/>
    <form action="" method="post">
        <div class="input-group">
            <input name="search" type="text" class="form-control" placeholder="Search">
            <div class="input-group-btn">
                <button class="btn btn-default" type="submit">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                    </svg>
                </button>
            </div>
        </div>
    </form>
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
                    <fmt:message key="local.product.weight.message" var="pWeightMessage"/>
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
                                <input class="form-control" type="number" name="tov${entry.key.getId()}" min="${-entry.value/1000}"
                                       step="0.001"
                                       max="${entry.key.getQuantity()/1000}" value="0.000" required/>
                            </c:if>
                            <c:if test="${entry.key.isWeight()==false}">
                                <input class="form-control" type="number" name="tov${entry.key.getId()}" min="${-entry.value}"
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