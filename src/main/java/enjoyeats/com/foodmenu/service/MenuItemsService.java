package enjoyeats.com.foodmenu.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import enjoyeats.com.foodmenu.constants.Constants;
import enjoyeats.com.foodmenu.dto.ItemUpdateDTO;
import enjoyeats.com.foodmenu.dto.MenuItemsDTO;
import enjoyeats.com.foodmenu.model.Ingredients;
import enjoyeats.com.foodmenu.model.MenuItemIngredients;
import enjoyeats.com.foodmenu.model.MenuItems;
import enjoyeats.com.foodmenu.repo.IngredientsRepo;
import enjoyeats.com.foodmenu.repo.MenuItemIngredientsRepo;
import enjoyeats.com.foodmenu.repo.MenuItemsRepo;

@Service
public class MenuItemsService {

    @Autowired
    MenuItemsRepo menuItemsRepo;
    @Autowired
    IngredientsRepo ingredientsRepo;
    @Autowired
    MenuItemIngredientsRepo menuItemIngredientsRepo;

    public MenuItemsDTO updateItem(long id, ItemUpdateDTO item) throws RuntimeException {
        MenuItemsDTO menuItemsDTO = new MenuItemsDTO();

        MenuItems menuItem = menuItemsRepo.findById(id).orElseThrow(() -> new RuntimeException(" Id Not Found"));

        if (item.getItemName() != null) {
            menuItem.setItemName(item.getItemName());
        }

        if (item.getPrice() != null) {
            menuItem.setPrice(item.getPrice());
        }

        if (item.getDescription() != null) {
            menuItem.setDescription(item.getDescription());
        }

        if (item.getUpdateExisting() != null && item.getIngredientIds() != null && !item.getIngredientIds().isEmpty()) {
            Optional<List<Ingredients>> ingredients = ingredientsRepo.findById(item.getIngredientIds());

            // clear the list of existing ingredients
            if (!item.getUpdateExisting()) {
                menuItemIngredientsRepo.deleteByMenuId(menuItem.getItemId());
            }

            for (Ingredients ingredient : ingredients.get()) {
                MenuItemIngredients menuItemIngredients = new MenuItemIngredients();
                menuItemIngredients.setIngredientId(ingredient.getIngredientId());
                menuItem.getMenuingredients().add(menuItemIngredients);
            }

        }

        MenuItems updatedMenuItems = menuItemsRepo.save(menuItem);

        List<MenuItemIngredients> menuingredients = updatedMenuItems.getMenuingredients();
        List<Long> ids = menuingredients.stream().map(value -> value.getIngredientId()).collect(Collectors.toList());
        Optional<List<Ingredients>> ingreIds = ingredientsRepo.findById(ids);
        List<String> ingredientsNames = ingreIds.get().stream().map(value -> value.getIngredientName()).collect(Collectors.toList());

        menuItemsDTO.setId(updatedMenuItems.getItemId());
        menuItemsDTO.setItemName(updatedMenuItems.getItemName());
        menuItemsDTO.setIngredientName(ingredientsNames);
        menuItemsDTO.setDescription(updatedMenuItems.getDescription());
        menuItemsDTO.setPrice(updatedMenuItems.getPrice());

        return menuItemsDTO;

    }

    public String deleteItem(long id) {

        if (!menuItemsRepo.existsById(id)) {
            return Constants.NO_DATA_FOUND;
        }
        menuItemsRepo.deleteById(id);
        return Constants.DELETED;

    }

    // Get selected item
    public MenuItemsDTO getSelectedDish(long id) {
        {
            MenuItemsDTO menuItemsDTO = new MenuItemsDTO();

            Optional<MenuItems> item = menuItemsRepo.findById(id);
            if (item.isEmpty()) {
                return menuItemsDTO;
            }

            List<MenuItemIngredients> menuingredients = item.get().getMenuingredients();
            List<Long> ids = menuingredients.stream().map(value -> value.getIngredientId()).collect(Collectors.toList());
            Optional<List<Ingredients>> ingreIds = ingredientsRepo.findById(ids);
            List<String> ingredientsNames = ingreIds.get().stream().map(value -> value.getIngredientName()).collect(Collectors.toList());

            menuItemsDTO.setId(item.get().getItemId());
            menuItemsDTO.setItemName(item.get().getItemName());
            menuItemsDTO.setIngredientName(ingredientsNames);
            menuItemsDTO.setDescription(item.get().getDescription());
            menuItemsDTO.setPrice(item.get().getPrice());

            return menuItemsDTO;
        }

    }
}
