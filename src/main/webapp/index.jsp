
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <fmt:message key="local.welcome" var="welcome"/>
    <h2>${welcome}</h2>
</div>
<fmt:message key="local.error.login" var="errLogin"/>
<fmt:message key="local.error.login" var="errPassword"/>
<form action="controller" method="post" onsubmit="return validateLoginForm('${errLogin}', '${errPassword}')">
    <div class="imgcontainer">
        <img src="images/cashmachine.png" alt="Cash machine" class="avatar">
    </div>
    <div class="container">
        <input type="hidden" name="command" value="login"/>

        <fieldset>
            <fmt:message key="local.login.label.user" var="luser"/>
            <label><b>${luser}</b></label>

            <fmt:message key="local.login.label.user.placeholder" var="uplaceholder"/>
            <input id="lg" class="form-control" type="text" placeholder="${uplaceholder}" name="login" required>
            <div id="spanLogin" class="invalid-feedback">More example invalid feedback text</div>
        </fieldset>
        <fieldset>
            <fmt:message key="local.login.label.password" var="lpassword"/>
            <label><b>${lpassword}</b></label>
            <fmt:message key="local.login.label.password.placeholder" var="pplaceholder"/>
            <input id="pwd" class="form-control" type="password" placeholder="${pplaceholder}" name="password">
            <div id="spanPassword" class="invalid-feedback">More example invalid feedback text</div>
        </fieldset>
        <fmt:message key="local.login.button" var="lbutton"/>
        <button class="btn" type="submit">${lbutton}</button>
    </div>
    <div class="container">
        <label>
            <fmt:message key="local.login.register.message" var="rmessage"/>
            <a href="register.jsp">${rmessage}</a>
        </label>
    </div>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>