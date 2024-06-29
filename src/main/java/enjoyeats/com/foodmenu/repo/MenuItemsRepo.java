package enjoyeats.com.foodmenu.repo;

import enjoyeats.com.foodmenu.model.MenuItems;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemsRepo extends JpaRepository<MenuItems,Long>{
}
