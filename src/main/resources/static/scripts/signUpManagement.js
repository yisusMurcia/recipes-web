const adminPasswordField = document.getElementById('adminPassword');
const selectFields = document.getElementById('userRol');
const usernameField = document.getElementById('username');
const passwordField = document.getElementById('password');

const signUpButton = document.getElementById('sign-up-btn');

selectFields.addEventListener('change', function() {
    adminPasswordField.disabled = selectFields.value !== 'admin';
});

signUpButton.addEventListener('click', (e)=> {
    e.preventDefault();
    signUp();
})

const signUp = async () => {
    const response = await fetch("api/user/create-user", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: usernameField.value,
            password: passwordField.value,
            userRol: selectFields.value,
            adminPassword: adminPasswordField.value
        })
    })


    if (response.ok) {
        const user = await response.json().then(user=>{
            sessionStorage.user = user.username; // Store the username in session storage
            location.href = 'index'; // Redirect to index page after successful sign-up
        });

    } else {
        const errorData = await response.json().then(errorData => alert(errorData));
    }
}
