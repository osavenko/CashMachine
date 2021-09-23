<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>
</div>
<div class="container">
    <fmt:message key="local.products.count" var="pCount"/>
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
            <td>${pDescription}</td>
        </tr>

        <c:forEach var="entry" items="${products}">
            <tr>
                <td><c:out value="${entry.getProduct().getName()}"/></td>
                <c:if test="${entry.getProduct().isWeight()==true}">
                    <fmt:message key="local.product.weight.message" var="pWeightMessage"/>
                    <td><c:out value="${pWeightMessage}"/></td>
                </c:if>
                <c:if test="${entry.getProduct().isWeight()==false}">
                    <fmt:message key="local.product.weight.quantitative" var="pWeightMessage"/>
                    <td><c:out value="${pWeightMessage}"/></td>
                </c:if>
                <c:if test="${entry.getProduct().isWeight()==true}">
                    <td><c:out value="${entry.getProduct().getQuantity()/1000}"/></td>
                </c:if>
                <c:if test="${entry.getProduct().isWeight()==false}">
                    <td><c:out value="${entry.getProduct().getQuantity()}"/></td>
                </c:if>

                <td><c:out value="${entry.getProduct().getPrice()}"/></td>
                <td>
                    <c:out value="${entry.getLocaleDescription()}"/>
                </td>
            </tr>
        </c:forEach>

    </table>
    <fmt:message key="local.common.add" var="pAdd"/>
    <a href="controller?command=addproductpage">${pAdd}</a>
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
                                                 href="controller?command=productslist&currentPage=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </nav>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>