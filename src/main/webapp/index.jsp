
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <fmt:message key="local.welcome" var="welcome"/>
    <h2>${welcome}</h2>
</div>
<form action="controller" method="post">
    <div class="imgcontainer">
        <img src="images/cashmachine.png" alt="Cash machine" class="avatar">
    </div>
    <div class="container">
        <input type="hidden" name="command" value="login"/>

        <fieldset>
            <fmt:message key="local.login.label.user" var="luser"/>
            <label><b>${luser}</b></label>

            <fmt:message key="local.login.label.user.placeholder" var="uplaceholder"/>
            <input type="text" placeholder="${uplaceholder}" name="login" required>
        </fieldset>
        <fieldset>
            <fmt:message key="local.login.label.password" var="lpassword"/>
            <label><b>${lpassword}</b></label>
            <fmt:message key="local.login.label.password.placeholder" var="pplaceholder"/>
            <input type="password" placeholder="${pplaceholder}" name="password">
        </fieldset>
        <fmt:message key="local.login.button" var="lbutton"/>
        <button type="submit">${lbutton}</button>
    </div>
    <div class="container">
        <label>
            <fmt:message key="local.login.register.message" var="rmessage"/>
            <a href="register.jsp">${rmessage}</a>
        </label>
    </div>
</form>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>