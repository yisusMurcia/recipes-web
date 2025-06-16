-- Eliminar tablas existentes (útil para recrear el esquema en desarrollo)
DROP TABLE IF EXISTS user_favorite_recipes;
DROP TABLE IF EXISTS recipe_ingredients;
DROP TABLE IF EXISTS recipes;
DROP TABLE IF EXISTS users;

-- Crear la tabla de usuarios
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY, -- BIGSERIAL para un ID auto-incremental (simil a AUTO_INCREMENT en otros DBs)
    name VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL -- Almacena el rol del usuario (ej. 'USER', 'ADMIN')
);

-- Crear la tabla de recetas
CREATE TABLE recipes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    instructions TEXT, -- TEXT para almacenar instrucciones largas
    num_of_favs INT DEFAULT 0, -- Se inicializa en 0 por defecto
    food_types VARCHAR(255), -- Almacenará los FoodType como una cadena de texto (ej. "SALTY,SWEET")
    food_intentions VARCHAR(255), -- Almacenará los FoodIntention como una cadena de texto (ej. "HEALTHY,VEGAN")
    user_id BIGINT NOT NULL, -- Clave foránea al usuario propietario de la receta
    CONSTRAINT fk_owner
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE -- Si el usuario se elimina, sus recetas también
);

-- Crear la tabla de unión para los ingredientes de las recetas
-- Esto mapea la @ElementCollection de List<String> ingredients
CREATE TABLE recipe_ingredients (
    recipe_id BIGINT NOT NULL,
    ingredient VARCHAR(255) NOT NULL,
    CONSTRAINT fk_recipe_ingredient
        FOREIGN KEY (recipe_id)
        REFERENCES recipes (id)
        ON DELETE CASCADE,
    PRIMARY KEY (recipe_id, ingredient) -- Clave primaria compuesta para unicidad
);


-- Crear la tabla de unión para las recetas favoritas (Many-to-Many)
-- Esto mapea la relación @ManyToMany de User.favs
CREATE TABLE user_favorite_recipes (
    user_id BIGINT NOT NULL,
    recipe_id BIGINT NOT NULL,
    CONSTRAINT fk_user_fav
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE, -- Si el usuario se elimina, sus favoritos también se eliminan de esta tabla
    CONSTRAINT fk_recipe_fav
        FOREIGN KEY (recipe_id)
        REFERENCES recipes (id)
        ON DELETE CASCADE, -- Si la receta se elimina, se quita de los favoritos de los usuarios
    PRIMARY KEY (user_id, recipe_id) -- Clave primaria compuesta para unicidad
);