// src/main/resources/static/scripts/login.js
const loginBtn = document.querySelector('#login-btn');
const usernameFiled = document.querySelector('#username');
const passwordFiled = document.querySelector('#password');

loginBtn.addEventListener('click', (e) => {
    e.preventDefault();
    validateUser();
});

const validateUser = async () => {
    try {
        const response = await fetch("/api/user/validate-user", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: usernameFiled.value,
                password: passwordFiled.value,
                userRol: null,
                adminPassword: null
            })
        })
        if (response.ok) {
            const user = await response.json().then(user=>
            {
                sessionStorage.user = user;
                location.href = "index";
            });
        } else {
            alert("Usuario o contraseña incorrecto");
        }
    } catch (e) {
        console.log(e)
    }
};