const newRecipeBtn = document.getElementById("new-recipe");
newRecipeBtn.addEventListener("click", () => {
    userLogged? createRecipe(): alertNotLogging();
})

const recipeContainer = document.getElementById("recipe-container");
const header = document.querySelector("h1");
const allRecipesBtn = document.getElementById("all");

let username = sessionStorage.user;
const userLogged = username !== null && username!== "";

header.innerText+= `, ${userLogged? username: "invitado"}`;

const alertNotLogging = () => {
    alert("Debes estar logueado para crear una receta")
    window.location.href = "login";
};

//Llamar la creación de receta
const createRecipe = async () => {
    try {
        const response = await fetch("/api/recipes/nextRecipeId");
        if (response.ok) {
            const recipeId = await response.json().then(recipeId => sessionStorage.recipeId = recipeId);
            window.location.href = `recipe/` + recipeId;
        }
    } catch (error) {
        console.error("Error en la solicitud: ", error);
    }
};

const createRecieps = (recipes) => {
    recipeContainer.innerHTML = ""; // Clear previous recipes
    recipes.forEach(recipe => {
        const recipeCard = createRecipeCard(recipe);
        recipeContainer.appendChild(recipeCard);
    });
}

const createRecipeCard = (recipe) => {

}

const getAllRecipes = async () => {
    try {
        const response = await fetch("api/recipes/all");
        if (response.ok) {
            return await response.json();
        } else {
            console.error("Error al obtener las recetas");
            return null
        }
    } catch (error) {
        console.error("Error en la solicitud: ", error);
    }
}
