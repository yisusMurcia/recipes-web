﻿# recipes-web
By Jesús Antonio Murcia and Silvana Martinez
Our API for the recipes web, here is some useful data:

## UserDto:
This object is returned when you login or register, here is its structure:
- id
- username
- password
- userRol

## RecipeDto
This object is returned when you get any sort of recipes, here is it structure:
- id
- title
- ingredients
- foodIntentions (list)
- foodTypes (list)
- userId (id del usuario creador de la receta)

## How create/delete/update the objects?
Here are some endpoints you can use:
### /api/user/create-user
Create a new user, this must have a different username, for make this you send a registerDto obj:
- userName
- password
- userRol
- adminPassword (only if you set the userRol as admin)

Also, the admin password is validated if this isn't correct to return a message, and it doesn't create the user

### api/user/login
It validates the user credentials, if it isn't correct, it returns a message, otherwise it returns a userDto

It must include a loginDto:
- username
- password

### api/user/{userId}/addFav/{recipeId}

It looks for the recipe with that id and also the user, then mark that as favorite

## Recipes (all methods admit pagination)

### api/recipes/all
It returns an array for all the recipes

### api/recipes/user-recipes/{userId}
Return an array of recipes made by the user with this id

## api/recipes/fav-recipes/{userId}
Return an array of recipes mark as favorite by the user with this id

### api/recipes/update
It gets a recipeDto and updates it, it returns the new updated recipe