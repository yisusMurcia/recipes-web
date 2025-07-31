package ing.yisus.recipesweb.repository;

import ing.yisus.recipesweb.persistence.RecipeEntity;
import ing.yisus.recipesweb.persistence.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    Page<RecipeEntity> findAllByUserId(Long userId, Pageable pageable);

    @Query("SELECT r FROM UserEntity user JOIN user.favs r")
    Page<RecipeEntity> findFavsByUserId(@Param("user")UserEntity userEntity, Pageable pageable);

    UserEntity user(UserEntity user);

    @Query(value = """
    SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
    FROM user_favorite_recipes
    WHERE recipe_id = :recipeId AND user_id = :userId
""", nativeQuery = true)
    boolean existsByRecipeIdAndUserId(@Param("recipeId") Long recipeId, @Param("userId") Long userId);

}
