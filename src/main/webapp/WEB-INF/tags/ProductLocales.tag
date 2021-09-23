<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cash" uri="/WEB-INF/tld/CashTags.tld" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ attribute name="descriptions" required="true" %>

<div class="container">
    <c:forEach var="descr" items="${descriptions}">
        ${descr}
    </c:forEach>
</div>