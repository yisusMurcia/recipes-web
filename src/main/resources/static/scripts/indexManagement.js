const newRecipeBtn = document.getElementById("new-recipe");
newRecipeBtn.addEventListener("click", () => {
    userLogged? createRecipe(): alertNotLogging();
})



const header = document.querySelector("h1");
user = sessionStorage.getItem("user");
const userLogged = user.username !== null && user.username !== "";

header.innerText+= `, ${userLogged? user.username: "invitado"}`;

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
        }
    } catch (error) {
        console.error("Error en la solicitud: ", error);
    }
};
