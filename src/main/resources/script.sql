    -- Script SQL para la base de datos recipes_web_bd (PostgreSQL)

    -- Deshabilitar la verificación de claves foráneas temporalmente si se recrea la base de datos,
    -- para evitar errores de dependencia al eliminar tablas.
    -- SET session_replication_role = replica; -- Descomentar en un entorno de pruebas si se necesita borrar y recrear

    -- Crear la tabla de usuarios (UserEntity)
    CREATE TABLE users (
                           id BIGSERIAL PRIMARY KEY, -- Identificador único del usuario (clave primaria)
                           name VARCHAR(255) NOT NULL UNIQUE, -- Nombre de usuario, debe ser único
                           password VARCHAR(255) NOT NULL, -- Contraseña del usuario
                           role VARCHAR(50) NOT NULL -- Rol del usuario (ej. 'USER', 'ADMIN'), mapeado desde UserRole enum
    );

    -- Crear la tabla de recetas (RecipeEntity)
    CREATE TABLE recipes (
                             id BIGSERIAL PRIMARY KEY, -- Identificador único de la receta (clave primaria)
                             title VARCHAR(255) NOT NULL, -- Título de la receta
                             instructions TEXT, -- Instrucciones detalladas de preparación
                             num_of_favs INT DEFAULT 0, -- Contador de favoritos
                             ingredients TEXT, -- Los ingredientes ahora se almacenan como un TEXT/VARCHAR largo (ej. "sal,pimienta,cebolla")
                             food_types VARCHAR(255), -- Lista de FoodType como cadena separada por comas
                             food_intentions VARCHAR(255), -- Lista de FoodIntention como cadena separada por comas
                             user_id BIGINT NOT NULL, -- Clave foránea al usuario propietario (RecipeEntity.owner -> UserEntity)
                             CONSTRAINT fk_recipes_owner -- Nombre de la restricción de clave foránea
                                 FOREIGN KEY (user_id)
                                     REFERENCES users (id) -- Hace referencia a la columna 'id' de la tabla 'users'
                                     ON DELETE CASCADE -- Si el usuario propietario se elimina, sus recetas también se eliminan
    );

    -- La tabla 'ingredients' y 'recipe_ingredients' ya no son necesarias aquí.

    -- Crear la tabla de unión para las recetas favoritas de los usuarios (Many-to-Many)
    -- Esta tabla mapea la relación UserEntity.favs <-> RecipeEntity
    CREATE TABLE user_favorite_recipes (
                                           user_id BIGINT NOT NULL, -- Clave foránea al usuario que marcó la receta como favorita
                                           recipe_id BIGINT NOT NULL, -- Clave foránea a la receta que fue marcada como favorita
                                           CONSTRAINT fk_user_favorite_users -- Nombre de la restricción para la FK de usuario
                                               FOREIGN KEY (user_id)
                                                   REFERENCES users (id)
                                                   ON DELETE CASCADE, -- Si el usuario se elimina, sus recetas favoritas también se eliminan de esta tabla
                                           CONSTRAINT fk_user_favorite_recipes -- Nombre de la restricción para la FK de receta
                                               FOREIGN KEY (recipe_id)
                                                   REFERENCES recipes (id)
                                                   ON DELETE CASCADE, -- Si la receta se elimina, se quita de los favoritos de los usuarios
                                           PRIMARY KEY (user_id, recipe_id) -- Clave primaria compuesta para asegurar que un usuario no pueda marcar la misma receta como favorita dos veces
    );

    -- Habilitar la verificación de claves foráneas si se deshabilitó al principio
    -- SET session_replication_role = origin; -- Descomentar en un entorno de pruebas si se deshabilitó