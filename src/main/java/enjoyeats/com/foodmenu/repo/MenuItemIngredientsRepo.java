package enjoyeats.com.foodmenu.repo;

import enjoyeats.com.foodmenu.model.MenuItemIngredients;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemIngredientsRepo extends JpaRepository<MenuItemIngredients,Long> {
    @Transactional
    @Modifying
    @Query(value ="delete from menuitemingredients where menu_id=:id",nativeQuery = true )
    void deleteByMenuId(long id);

    @Transactional
    @Modifying
    @Query(value ="delete from menuitemingredients where ingre_id=:id",nativeQuery = true )
    void deleteByIngreId(long id);


}
