<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <fmt:message key="local.login.bad.message" var="message"/>
    <a href="index.jsp">${message}</a>
</div>
<div class="container">
    <span>${errorMessage}</span>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
