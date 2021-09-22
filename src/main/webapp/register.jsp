<%@ include file="/WEB-INF/jspf/head.jspf" %>

<div class="container">
    <fmt:message key="local.register.title" var="ltitle"/>
    <h2>${ltitle}</h2>

    <fmt:message key="local.register.message" var="lmessage"/>
    <p>${lmessage}</p>
</div>
<fmt:message key="local.error.login" var="errLogin"/>
<fmt:message key="local.error.password" var="errPassword"/>
<fmt:message key="local.error.fullname" var="errFullName"/>
<form action="controller" method="post"
      onsubmit="return validateRegisterForm('${errLogin}', '${errPassword}', '${errFullName}')">
    <div class="container">
        <input type="hidden" name="command" value="register"/>

        <fieldset>
            <fmt:message key="local.login.label.user" var="luser"/>
            <label><b>${luser}</b></label>

            <fmt:message key="local.login.label.user.placeholder" var="uplaceholder"/>
            <input id="lg" class="form-control" type="text" placeholder="${uplaceholder}" name="login"
                   onchange="validateLogin('${errLogin}')" required>
            <div id="spanLogin" class="invalid-feedback"></div>
        </fieldset>


        <fieldset>
            <fmt:message key="local.register.password" var="lpassword"/>
            <fmt:message key="local.error.password.repeat" var="errPasswordRepeat"/>
            <label><b>${lpassword}</b></label>
            <input id="pwd" class="form-control" type="password" placeholder="${lpassword}" name="password"
                   onchange="checkPassword('${errPasswordRepeat}')" required>
            <div id="spanPassword" class="invalid-feedback"></div>
        </fieldset>
        <fieldset>
            <fmt:message key="local.register.password.repeat" var="lrPassword"/>
            <label><b>${lrPassword}</b></label>
            <input id="rpwd" class="form-control" type="password" placeholder="${lrPassword}" name="passwordRepeat"
                   onchange="checkPassword('${errPasswordRepeat}')" required>
        </fieldset>
        <fieldset>
            <fmt:message key="local.register.fullname" var="lfullname"/>
            <label><b>${lfullname}</b></label>
            <input id="fName" class="form-control" type="text" placeholder="${lfullname}" name="fullname"
                   onchange="validateFullName('${errFullName}')" required>
            <div id="spanFullName" class="invalid-feedback"></div>
        </fieldset>

        <div>
            <fmt:message key="local.register.role" var="lrole"/>
            <cash:SelectRole name="role" localeMessage="${lrole}"/>
        </div>

        <div>
            <fmt:message key="local.register.locale" var="llocale"/>
            <cash:SelectLocale name="locale" localeMessage="${llocale}"/>
        </div>
        <div>
            <fmt:message key="local.register.button" var="lbutton"/>
            <button class="btn btn-primary" type="submit">${lbutton}</button>
        </div>
    </div>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
