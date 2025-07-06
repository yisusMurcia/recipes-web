package ing.yisus.recipesweb.repository;

import ing.yisus.recipesweb.persistence.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    List<RecipeEntity> findAllByUserId(Long userId);
}
