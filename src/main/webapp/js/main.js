let regLogin = /^[0-9a-zA-Zа-яА-ЯЄєіІїЇёЁ]{4,20}$/;
let regFullName = /^[0-9a-zA-Zа-яА-ЯЄєіІїЇёЁ\s]{4,50}$/;
let regProductName = /^[0-9a-zA-Zа-яА-ЯЄєіІїЇёЁ\-\s]{4,100}$/;
let regProductDescription = /^[0-9a-zA-Zа-яА-ЯЄєіІїЇёЁ\s]{4,200}$/;
let regPassword = /^[0-9a-zA-Z!@#$%]{1,}$/;


let login = document.querySelector('#lg');
let fullName = document.querySelector('#fName');
let productName = document.querySelector('#id_name');
let productDescription = document.querySelector('#id_description');
let password = document.querySelector('#pwd');
let rPassword = document.querySelector('#rpwd');
let spanLogin = document.querySelector('#spanLogin');
let spanFullName = document.querySelector('#spanFullName');
let spanProductName = document.querySelector('#spanProductName');
let spanProductDescription = document.querySelector('#spanProductDescription');
let spanPassword = document.querySelector('#spanPassword');

function checkPassword(err) {
    if (password.value != rPassword.value) {
        notValid(password, spanPassword, err);
        console.log('LOG IS BAD');
    } else {
        valid(password);
        console.log('LOG IS OK');
    }
}

function validateRegisterForm(errLogin, errPassword, errFulName) {
    let result = true;
    result = validateLogin(errLogin);
    result = validatePassword(errPassword);
    result = validateFullName(errFulName);
    return result;
}

function validateLoginForm(errLogin, errPassword) {
    let result = true;
    result = validateLogin(errLogin);
    result = validatePassword(errPassword);
    return result;
}
function validateProductForm(errProduct, errDescription){
    let result = true;
    result = validateProductName(errProduct);
    result = validateProductDescription(errDescription);
    return result;
}
function validateFullName(errName) {
    if (validate(regFullName, fullName)) {
        valid(fullName, spanFullName);
        console.log('Login Ok')
    } else {
        notValid(fullName, spanFullName, errName);
        console.log('Login BAD')
        return false;
    }
}
function validateProductName(errProduct){
    if (validate(regProductName, productName)) {
        valid(productName, spanProductName);
        console.log('Product name is Ok')
    } else {
        notValid(productName,spanProductName, errProduct);
        console.log('Product name is BAD')
        return false;
    }
}

function validateProductDescription(errDescription){
    if (validate(regProductDescription, productDescription)) {
        valid(productDescription, spanProductDescription);
        console.log('Product description is Ok')
    } else {
        notValid(productDescription,spanProductDescription, errDescription);
        console.log('Product description is BAD')
        return false;
    }
}

function validateLogin(errLogin) {
    if (validate(regLogin, login)) {
        valid(login, spanLogin);
        console.log('Login Ok')
    } else {
        notValid(login, spanLogin, errLogin);
        console.log('Login BAD')
        return false;
    }
}

function validatePassword(errPassword) {
    if (validate(regPassword, password)) {
        valid(password, spanPassword);
        console.log('Password Ok')
    } else {
        notValid(password, spanPassword, errPassword);
        console.log('Password BAD')
        return false;
    }
}


function validate(regex, inp) {
    return regex.test(inp.value);
}

function valid(inp) {
    inp.classList.remove('is-invalid');
    inp.classList.add('is-valid');
}

function notValid(inp, span, message) {
    inp.classList.add('is-invalid');
    span.innerHTML = message;
}