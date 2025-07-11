const newRecipeBtn = document.getElementById("new-recipe");
newRecipeBtn.addEventListener("click", () => {
    userLogged? createRecipe(): alertNotLogging();
})

const recipeContainer = document.getElementById("recipe-container");
const header = document.querySelector("h1");
const allRecipesBtn = document.getElementById("all");
const myRecipesBtn = document.getElementById("my-recipes");

allRecipesBtn.addEventListener("click", async () => {
    chargeRecipes();
})

myRecipesBtn.addEventListener("click", async () => {
    const recipes = await getMyRecipes();
    createRecipes(recipes);
})

let username = sessionStorage.user;
const userLogged = username !== undefined && username!== "";

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

const createRecipes = (recipes) => {
    recipeContainer.innerHTML = ""; // Clear previous recipes
    recipes.forEach(recipe => {
        const recipeCard = createRecipeCard(recipe);
        recipeContainer.appendChild(recipeCard);
    });
}

const createRecipeCard = (recipe) => {
    const card = document.createElement("div");
    card.className = "recipe bg-white p-6 rounded-lg shadow-md flex flex-col";

    // Título
    const title = document.createElement("p");
    title.className = "recipe_title text-2xl font-semibold text-gray-800 mb-3";
    title.innerHTML = `<b>${recipe.title}</b>`;
    card.appendChild(title);

    // Ingredientes
    const ingredientsTitle = document.createElement("p");
    ingredientsTitle.className = "text-lg font-medium text-gray-700 mb-2";
    ingredientsTitle.innerHTML = "<b>Ingredientes:</b>";
    card.appendChild(ingredientsTitle);

    const ingredientsList = document.createElement("ul");
    ingredientsList.className = "recipe_ingredients list-disc list-inside text-gray-600 mb-4 flex-grow";
    (recipe.ingredients.split(",") || []).forEach(ing => {
        const li = document.createElement("li");
        li.textContent = ing;
        ingredientsList.appendChild(li);
    });
    card.appendChild(ingredientsList);

    // Etiquetas
    const tagsTitle = document.createElement("p");
    tagsTitle.className = "text-lg font-medium text-gray-700 mb-2";
    tagsTitle.innerHTML = "<b>Etiquetas:</b>";
    card.appendChild(tagsTitle);

    const tagsList = document.createElement("ul");
    tagsList.className = "recipe_tags flex flex-wrap gap-2 mb-4";
    // FoodTypes
    (recipe.foodTypes || []).forEach(type => {
        const li = document.createElement("li");
        li.className = "bg-blue-200 text-blue-800 text-sm font-semibold px-3 py-1 rounded-full";
        li.textContent = type;
        tagsList.appendChild(li);
    });
    // FoodIntentions
    (recipe.foodIntentions || []).forEach(intent => {
        const li = document.createElement("li");
        li.className = "bg-green-200 text-green-800 text-sm font-semibold px-3 py-1 rounded-full";
        li.textContent = intent;
        tagsList.appendChild(li);
    });
    card.appendChild(tagsList);

    // Instrucciones
    const instructionsTitle = document.createElement("p");
    instructionsTitle.className = "text-lg font-medium text-gray-700 mb-2";
    instructionsTitle.innerHTML = "<b>Instrucciones:</b>";
    card.appendChild(instructionsTitle);

    const instructions = document.createElement("p");
    instructions.className = "recipe_instructions text-gray-600 leading-relaxed";
    instructions.textContent = recipe.instructions;
    card.appendChild(instructions);

    // Botón editar
    const editBtn = document.createElement("button");
    editBtn.id = `edit-recipe${recipe.id}`;
    editBtn.innerHTML = `<i class="fas fa-edit"></i>`;
    card.appendChild(editBtn);

    // Listener del botón editar
    editBtn.addEventListener("click", () => {
        // Aquí puedes redirigir o abrir modal usando el id de la receta
        window.location.href = `recipe/${recipe.id}`;
    });

    return card;
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

const getMyRecipes = async () => {
    const userId = await getUserId();
    try {
        const response = await fetch(`api/recipes/user-recipes/${userId}`);
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

const chargeRecipes = async () => {
    const recipes = await getAllRecipes();
    if (recipes) {
        createRecipes(recipes);
    } else {
        alert("No se pudieron cargar las recetas");
    }
}

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

chargeRecipes();
