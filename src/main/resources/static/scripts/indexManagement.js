const newRecipeBtn = document.getElementById("new-recipe");
newRecipeBtn.addEventListener("click", () => {
    userLogged? createRecipe(): alertNotLogging();
})


const header = document.querySelector("h1");
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
