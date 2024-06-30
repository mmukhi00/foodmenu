package enjoyeats.com.foodmenu.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import enjoyeats.com.foodmenu.model.Ingredients;

@Repository
public interface IngredientsRepo extends JpaRepository<Ingredients, Long> {

    Optional<Ingredients> findByIngredientName(String ingredientName);

    @Query(value = "select * from ingredients where ingre_name in (:types)", nativeQuery = true)
    Optional<List<Ingredients>> findByIngredName(@Param("types") List<String> ingredientName);

    @Query(value = "select * from ingredients where id in (:types)", nativeQuery = true)
    Optional<List<Ingredients>> findById(@Param("types") List<Long> ingredientId);

}
