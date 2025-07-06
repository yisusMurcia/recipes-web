const adminPasswordField = document.getElementById('adminPassword');
const selectFields = document.getElementById('userRol');

selectFields.addEventListener('change', function() {
    if (selectFields.value === 'admin') {
        adminPasswordField.disabled = false;
    } else {
        adminPasswordField.disabled = true;
    }
});