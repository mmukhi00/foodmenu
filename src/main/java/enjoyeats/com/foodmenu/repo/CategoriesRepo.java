package enjoyeats.com.foodmenu.repo;

import enjoyeats.com.foodmenu.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoriesRepo extends JpaRepository<Categories,Long> {

   Optional<Categories> findByCategoryName(String categoryName);
}
