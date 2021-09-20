<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cash" uri="/WEB-INF/tld/CashTags.tld" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ attribute name="descriptions" required="true" %>

<div class="container">
    <c:forEach var="descr" items="${descriptions}">
        ${descr}
<%--
        <button type="button" class="btn btn-secondary" data-bs-toggle="tooltip" data-bs-placement="top"
                title="${description.getLocale()}">
                ${description.getText()}
        </button>
--%>
    </c:forEach>
</div>