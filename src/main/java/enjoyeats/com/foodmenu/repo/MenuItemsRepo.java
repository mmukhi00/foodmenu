package enjoyeats.com.foodmenu.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import enjoyeats.com.foodmenu.model.MenuItems;

@Repository
public interface MenuItemsRepo extends JpaRepository<MenuItems, Long> {
}
