package enjoyeats.com.foodmenu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import enjoyeats.com.foodmenu.constants.Constants;
import enjoyeats.com.foodmenu.dto.CategorieDTO;
import enjoyeats.com.foodmenu.dto.MenuItemsDTO;
import enjoyeats.com.foodmenu.dto.ResponseDTO;
import enjoyeats.com.foodmenu.model.Categories;
import enjoyeats.com.foodmenu.model.Ingredients;
import enjoyeats.com.foodmenu.model.MenuItemIngredients;
import enjoyeats.com.foodmenu.model.MenuItems;
import enjoyeats.com.foodmenu.repo.CategoriesRepo;
import enjoyeats.com.foodmenu.repo.IngredientsRepo;
import enjoyeats.com.foodmenu.repo.MenuItemsRepo;

@Service
public class CategoriesService {

    @Autowired
    CategoriesRepo categoriesRepo;
    @Autowired
    MenuItemsRepo menuItemsRepo;
    @Autowired
    IngredientsRepo ingredientsRepo;

    // Save category with it's menu items
    public ResponseDTO saveCategories(Categories category) {
        Categories savedData;
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Categories> result = categoriesRepo.findByCategoryName(category.getCategoryName());

        // save only category
        if (category.getMenuItems() == null) {
            if (result.isPresent()) {
                responseDTO.setId(result.get().getCategoryId());
            } else {
                savedData = categoriesRepo.save(category);
                if (categoriesRepo.existsById(savedData.getCategoryId())) {
                    responseDTO.setId(savedData.getCategoryId());
                }
            }
            responseDTO.setMessage(Constants.CREATED);
            return responseDTO;
        }

        List<MenuItems> menuItems = category.getMenuItems();

        // map item with ingredients ids
        menuItems.stream().forEach(item -> {

            List<Long> ingre = item.getIngredients();
            List<MenuItemIngredients> menuingredients = item.getMenuingredients();
            Optional<List<Ingredients>> ingreIds = ingredientsRepo.findById(ingre);

            List<Long> ingredientsIds = ingreIds.get().stream().map(value -> value.getIngredientId()).collect(Collectors.toList());
            for (Long value : ingredientsIds) {
                MenuItemIngredients i = new MenuItemIngredients();
                i.setIngredientId(value);
                menuingredients.add(i);
            }

        });

        if (result.isPresent()) {
            menuItems.stream().forEach(value -> result.get().getMenuItems().add(value));
            savedData = categoriesRepo.save(result.get());
        } else {
            savedData = categoriesRepo.save(category);
        }

        if (categoriesRepo.existsById(savedData.getCategoryId())) {
            responseDTO.setId(savedData.getCategoryId());
            responseDTO.setMessage(Constants.CREATED);
        }

        return responseDTO;

    }

    // Get selected Category
    public CategorieDTO getSelectedItem(long id) {
        {
            CategorieDTO categorieDTO = new CategorieDTO();

            Optional<Categories> items = categoriesRepo.findById(id);
            if (items.isEmpty()) {
                return categorieDTO;
            }

            List<MenuItemsDTO> menuItemsDTOS = new ArrayList<>();

            List<MenuItems> menuItems = items.get().getMenuItems();
            menuItems.stream().forEach(item
                    -> {
                List<MenuItemIngredients> menuingredients = item.getMenuingredients();
                List<Long> ids = menuingredients.stream().map(value -> value.getIngredientId()).collect(Collectors.toList());
                Optional<List<Ingredients>> ingreIds = ingredientsRepo.findById(ids);
                List<String> ingredientsNames = ingreIds.get().stream().map(value -> value.getIngredientName()).collect(Collectors.toList());

                MenuItemsDTO menuItemsDTO = new MenuItemsDTO();
                menuItemsDTO.setId(item.getItemId());
                menuItemsDTO.setItemName(item.getItemName());
                menuItemsDTO.setIngredientName(ingredientsNames);
                menuItemsDTO.setDescription(item.getDescription());
                menuItemsDTO.setPrice(item.getPrice());

                menuItemsDTOS.add(menuItemsDTO);

            });

            categorieDTO.setId(items.get().getCategoryId());
            categorieDTO.setCategoryName(items.get().getCategoryName());
            categorieDTO.setMenuItems(menuItemsDTOS);

            return categorieDTO;
        }

    }

    //Get all items   
    public List<CategorieDTO> getAllItems() {
        List<Categories> categoriesList = categoriesRepo.findAll();

        List<CategorieDTO> result = new ArrayList<>();

        categoriesList.stream().forEach(items -> {
            List<MenuItemsDTO> menuItemsDTOS = new ArrayList<>();
            CategorieDTO categorieDTO = new CategorieDTO();
            List<MenuItems> menuItems = items.getMenuItems();
            menuItems.stream().forEach(item
                    -> {

                List<MenuItemIngredients> menuingredients = item.getMenuingredients();
                List<Long> ids = menuingredients.stream().map(value -> value.getIngredientId()).collect(Collectors.toList());
                Optional<List<Ingredients>> ingreIds = ingredientsRepo.findById(ids);
                List<String> ingredientsNames = ingreIds.get().stream().map(value -> value.getIngredientName()).collect(Collectors.toList());

                MenuItemsDTO menuItemsDTO = new MenuItemsDTO();
                menuItemsDTO.setId(item.getItemId());
                menuItemsDTO.setItemName(item.getItemName());
                menuItemsDTO.setIngredientName(ingredientsNames);
                menuItemsDTO.setDescription(item.getDescription());
                menuItemsDTO.setPrice(item.getPrice());

                menuItemsDTOS.add(menuItemsDTO);

            });

            categorieDTO.setId(items.getCategoryId());
            categorieDTO.setCategoryName(items.getCategoryName());
            categorieDTO.setMenuItems(menuItemsDTOS);

            result.add(categorieDTO);
        });

        return result;
    }

    //Delete category
    public String deleteCategory(long id) {

        if (!categoriesRepo.existsById(id)) {
            return Constants.NO_DATA_FOUND;
        }
        categoriesRepo.deleteById(id);
        return Constants.DELETED;
    }

    //Delete all category
    public String deleteAllCategories() {
        categoriesRepo.deleteAll();
        return Constants.DELETED;
    }

    //Update category
    public ResponseDTO updateCategory(long id, Categories categories) {

        Categories category = categoriesRepo.findById(id).orElseThrow(() -> new RuntimeException(Constants.NO_DATA_FOUND));
        ResponseDTO responseDTO = new ResponseDTO();

        category.setCategoryName(categories.getCategoryName());

        categoriesRepo.save(category);
        responseDTO.setId(category.getCategoryId());
        responseDTO.setMessage(Constants.DATA_UPDATED);
        return responseDTO;
    }

}
