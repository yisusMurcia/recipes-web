const newRecipeBtn = document.getElementById("new-recipe");
newRecipeBtn.addEventListener("click", () => {
    userLogged? createRecipe(): alertNotLogging();
})

const header = document.querySelector("header");
const userLogged = !header.textContent.includes(null);

const alertNotLogging = () => {
    alert("Debes estar logueado para crear una receta")
    window.location.href = "login";
};

//Llamar la creación de receta
const createRecipe = async () => {
    try {
        const response = await fetch("/api/recipes/nextRecipeId");
        if (response.ok) {
            const recipeId = await response.json();
            window.location.href = `recipe/` + recipeId;
        } else {
            console.error("Error al obtener el ID de la receta", response.status);
        }
    } catch (error) {
        console.error("Error en la solicitud: ", error);
    }
};
