const createBtn = document.querySelector('#create-btn');
const nameField = document.querySelector('#name');
const ingredients = document.querySelector('#ingredients');
const instructions = document.querySelector('#instructions');

// Selecciona todos los checkboxes dentro del fieldset con la clase "food_type"
const foodTypeCheckboxes = document.querySelectorAll('.food_type input[type="checkbox"]:checked');
// Crea un array a partir de los nodos seleccionados y mapea sus valores


const foodIntentionCheckboxes = document.querySelectorAll('.food_intention input[type="checkbox"]:checked');

createBtn.addEventListener('click', async (e) => {
    recipeId = sessionStorage.recipeId;
    e.preventDefault();
    const userId = await getUserId();
    await createRecipe(userId);
});


let username = sessionStorage.user;

const getUserId = async () => {
    try {
        const response = await fetch(`/api/user/userId/${username}`);
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error("Error al obtener el ID del usuario");
        }
    } catch (error) {
        alert(`Error en la solicitud: ${error.message}`);
        location.href = "index";
    }
};

const createRecipe = async (userId) => {
    const selectedTypes = Array.from(foodTypeCheckboxes).map(checkbox => checkbox.value);
    const selectedIntentions = Array.from(foodIntentionCheckboxes).map(checkbox => checkbox.value);
    try {
        const response = await fetch("/api/recipes/update", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                id: recipeId,
                title: nameField.value,
                ingredients: ingredients.value,
                instructions: instructions.value,
                foodTypes: selectedTypes,
                foodIntentions: selectedIntentions,
                userId: userId
            })
        });
        if (response.ok) {
            alert("Receta creada correctamente");
            location.href = "../index";
        } else {
            alert("Error al crear la receta");
        }
    } catch (e) {
        console.log(e);
    }
}