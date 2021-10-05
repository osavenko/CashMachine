<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <fmt:message key="local.add.product.message" var="message"/>
    <h2>${message}</h2>
</div>
<fmt:message key="local.error.product" var="errProduct"/>
<fmt:message key="local.error.description" var="errDescription"/>
<form action="controller" method="post" onsubmit="return validateProductForm('${errProduct}','${errDescription}')">
    <div class="container">
        <input type="hidden" name="command" value="addProduct"/>
        <c:if test="${id>0}">
            <input type="hidden" name="productUpdate" value="${id}"/>
        </c:if>
        <c:if test="${id>0}">
            <fmt:message key="local.order.number" var="pnamber"/>
            <label for="id_name"><b>${pnamber}${id}</b></label>
        </c:if>
        <fieldset>
            <fmt:message key="local.product.name" var="pname"/>
            <label for="id_name"><b>${pname}</b></label>

            <input id="id_name" class="form-control" type="text" placeholder="${pname}" name="productName"
                   onchange="validateProductName('${errProduct}')"
            <c:if test="${id>0}">
                   value="${product.getName()}"
            </c:if>
                   required>
            <div id="spanProductName" class="invalid-feedback"></div>
        </fieldset>
        <fieldset>
            <fmt:message key="local.product.brand" var="pbrand"/>
            <c:if test="${id>0}">
                <cash:SelectBrand name="brand" defaultId="${product.getBrandId()}" localeMessage="${pbrand}"/>
            </c:if>
            <c:if test="${id==0}">
                <cash:SelectBrand name="brand" localeMessage="${pbrand}"/>
            </c:if>
        </fieldset>
        <fieldset>
            <fmt:message key="local.product.type" var="pType"/>
            <label class="input-group-addon">${pType}</label>
            <div class="form-check">
                <c:choose>
                    <c:when test="${id>0}">
                        <c:choose>
                            <c:when test="${product.isWeight()}">
                                <input class="form-check-input" type="radio" name="typeRadios" id="RadiosW" value="0"
                                       checked>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-input" type="radio" name="typeRadios" id="RadiosW" value="0">
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <input class="form-check-input" type="radio" name="typeRadios" id="RadiosW" value="0">
                    </c:otherwise>
                </c:choose>

                <fmt:message key="local.product.type.weight" var="pTypeW"/>
                <label class="form-check-label" for="RadiosW">
                    ${pTypeW}
                </label>
            </div>
            <div class="form-check">
                <c:choose>
                    <c:when test="${id>0}">
                        <c:choose>
                            <c:when test="${product.isWeight()==false}">
                                <input class="form-check-input" type="radio" name="typeRadios" id="RadiosW" value="1"
                                       checked>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-input" type="radio" name="typeRadios" id="RadiosW" value="1">
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <input class="form-check-input" type="radio" name="typeRadios" id="RadiosP" value="1" checked>
                    </c:otherwise>
                </c:choose>

                <fmt:message key="local.product.type.piece" var="pTypeP"/>
                <label class="form-check-label" for="RadiosP">
                    ${pTypeP}
                </label>
            </div>
        </fieldset>
        <fieldset>
            <fmt:message key="local.product.price" var="pprice"/>
            <label for="id_price"><b>${pprice}</b></label>
            <c:choose>
                <c:when test="${id>0}">
                    <c:set var="price" scope="page" value="${product.getPrice()}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="price" scope="page" value="0.01"/>
                </c:otherwise>
            </c:choose>
            <input id="id_price" type="number" min="0.01" step="0.01" value="${price}" class="form-control"
                   placeholder="${pprice}" name="price" required>
        </fieldset>
        <fieldset>
            <fmt:message key="local.product.quantity" var="pquantity"/>
            <label for="id_quantity" class="input-group-addon"><b>${pquantity}</b></label>
            <c:choose>
                <c:when test="${id>0}">
                    <c:choose>
                        <c:when test="${product.isWeight()==true}">
                            <c:set var="quantity" scope="page" value="${product.getQuantity()/1000}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="quantity" scope="page" value="${product.getQuantity()}"/>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:set var="quantity" scope="page" value="0.000"/>
                </c:otherwise>
            </c:choose>
            <input id="id_quantity" type="number" min="0.001" step="0.001" value="${quantity}" class="form-control"
                   placeholder="${pquantity}" name="quantity" required>
        </fieldset>
        <fieldset>
            <fmt:message key="local.product.description" var="pdescription"/>
            <label for="id_description"><b>${pdescription}</b></label>
            <fmt:message key="local.register.locale" var="llocale"/>
            <cash:SelectLocale name="locale" localeMessage="${llocale}" defaultLocale="${defaultLocale}"/>

            <textarea id="id_description" class="form-control" rows="3" name="description"
                      onchange="validateProductDescription('${errDescription}')" required></textarea>
            <div id="spanProductDescription" class="invalid-feedback"></div>
        </fieldset>
        <fmt:message key="local.add.product.button" var="lbutton"/>
        <button class="btn" type="submit">${lbutton}</button>
        <fmt:message key="local.label.back" var="lBack"/>
        <a href="controller?command=main">${lBack}</a>
    </div>
</form>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
