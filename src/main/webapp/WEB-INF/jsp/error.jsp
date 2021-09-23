<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>
</div>
<div class="container">
    <fmt:message key="local.error.page.notfound" var="pMessage"/>
    <p class="text-center"><strong>${pMessage}</strong></p>
    <fmt:message key="local.main.page" var="pMainPage"/>
    <a href="index.jsp">${pMainPage}</a>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
