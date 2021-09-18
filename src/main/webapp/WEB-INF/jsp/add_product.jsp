<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <fmt:message key="local.add.product.message" var="message"/>
    <h2>${message}</h2>
</div>
<fmt:message key="local.error.product" var="errProduct"/>
<form action="controller" method="post" onsubmit="return validateProductForm('${errProduct}')">
    <div class="container">
        <input type="hidden" name="command" value="addProduct"/>
        <fieldset>
            <fmt:message key="local.product.name" var="pname"/>
            <label for="id_name"><b>${pname}</b></label>

            <input id="id_name" class="form-control" type="text" placeholder="${pname}" name="productName"
                   onchange="validateProductName('${errProduct}')" required>
            <div id="spanProductName" class="invalid-feedback"></div>
        </fieldset>
        <fieldset>
            <fmt:message key="local.product.brand" var="pbrand"/>
            <cash:SelectBrand name="brand" localeMessage="${pbrand}"/>
        </fieldset>
        <fieldset>
            <fmt:message key="local.product.type" var="pType"/>
            <label class="input-group-addon">${pType}</label>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="typeRadios" id="RadiosW" value="weight">
                <fmt:message key="local.product.type.weight" var="pTypeW"/>
                <label class="form-check-label" for="RadiosW">
                    ${pTypeW}
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="typeRadios" id="RadiosP" value="piece" checked>
                <fmt:message key="local.product.type.piece" var="pTypeP"/>
                <label class="form-check-label" for="RadiosP">
                    ${pTypeP}
                </label>
            </div>
        </fieldset>
        <fieldset>
            <fmt:message key="local.product.price" var="pprice"/>
            <label for="id_price"><b>${pprice}</b></label>
            <input id="id_price" type="number" min="0.00" step="0.1" value="0.00" class="form-control"
                   placeholder="${pprice}" name="price" required>
        </fieldset>
        <fieldset>
            <fmt:message key="local.product.quantity" var="pquantity"/>
            <label for="id_quantity" class="input-group-addon"><b>${pquantity}</b></label>
            <input id="id_quantity" type="number" min="0.000" step="1" value="0.000" class="form-control"
                   placeholder="${pquantity}" name="quantity" required>
        </fieldset>
        <fieldset>
            <fmt:message key="local.product.description" var="pdescription"/>
            <label for="id_description"><b>${pdescription}</b></label>
            <fmt:message key="local.register.locale" var="llocale"/>
            <cash:SelectLocale name="locale" localeMessage="${llocale}"/>

            <textarea id="id_description" class="form-control" rows="3" name="description" required></textarea>
            <fmt:message key="local.add.product.button" var="lbutton"/>
        </fieldset>
        <button class="btn" type="submit">${lbutton}</button>
        <fmt:message key="local.label.back" var="lBack"/>
        <a href="controller?command=main">${lBack}</a>
    </div>
</form>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
