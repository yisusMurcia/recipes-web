const createBtn = document.querySelector('#create-btn');
const nameField = document.querySelector('#name');
const ingredients = document.querySelector('#ingredients');
const instructions = document.querySelector('#instructions');
// Selecciona todos los checkboxes dentro del fieldset con la clase "food_type"
const foodTypeCheckboxes = document.querySelectorAll('.food_type input[type="checkbox"]:checked');
// Crea un array a partir de los nodos seleccionados y mapea sus valores
const selectedTypes = Array.from(foodTypeCheckboxes).map(checkbox => checkbox.value);
const foodIntentionCheckboxes = document.querySelectorAll('.food_intention input[type="checkbox"]:checked');
const selectedIntentions = Array.from(foodIntentionCheckboxes).map(checkbox => checkbox.value);

createBtn.addEventListener('click', (e) => {
    e.preventDefault();
    createRecipe();
});

const createRecipe = async () => {
    try {
        const response = await fetch("/api/recipe/create-recipe", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                name: nameField.value,
                ingredients: ingredients.value,
                instructions: instructions.value,
                foodType: selectedTypes,
                foodIntention: selectedIntentions
            })
        });
        if (response.ok) {
            alert("Receta creada correctamente");
            location.href = "index";
        } else {
            alert("Error al crear la receta");
        }
    } catch (e) {
        console.log(e);
    }
}