package enjoyeats.com.foodmenu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import enjoyeats.com.foodmenu.constants.Constants;
import enjoyeats.com.foodmenu.dto.CategorieDTO;
import enjoyeats.com.foodmenu.dto.ItemUpdateDTO;
import enjoyeats.com.foodmenu.dto.MenuItemsDTO;
import enjoyeats.com.foodmenu.dto.ResponseDTO;
import enjoyeats.com.foodmenu.model.Categories;
import enjoyeats.com.foodmenu.model.Ingredients;
import enjoyeats.com.foodmenu.service.CategoriesService;
import enjoyeats.com.foodmenu.service.IngredientsService;
import enjoyeats.com.foodmenu.service.MenuItemsService;

@RestController
public class Controller {

    @Autowired
    CategoriesService categoriesService;
    @Autowired
    MenuItemsService menuItemsService;
    @Autowired
    IngredientsService ingredientsService;

    /* Create APIs */
    @PostMapping("/addCategory")
    public ResponseEntity<ResponseDTO> addCategory(@RequestBody Categories category) {

        ResponseDTO responseDTO = categoriesService.saveCategories(category);

        if (!responseDTO.getMessage().equals(Constants.CREATED)) {
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/addIngredient")
    public ResponseEntity<ResponseDTO> addIngredient(@RequestBody Ingredients ingredients) {

        ResponseDTO responseDTO = ingredientsService.saveIngredient(ingredients);

        if (responseDTO.getMessage().equals(Constants.NOT_CREATED)) {
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/addIngredients")
    public ResponseEntity<List<Ingredients>> addIngredients(@RequestBody List<Ingredients> ingredients) {

        List<Ingredients> ingredientList = ingredientsService.saveAllIngredients(ingredients);

        return new ResponseEntity<>(ingredientList, HttpStatus.CREATED);
    }

    /* Fetch APIs */
    @GetMapping("/getItem/{id}")
    public ResponseEntity<CategorieDTO> getItem(@PathVariable long id) {
        CategorieDTO categorieDTO = categoriesService.getSelectedItem(id);
        if (categorieDTO.getCategoryName() == null) {
            return new ResponseEntity<>(categorieDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categorieDTO, HttpStatus.FOUND);
    }

    @GetMapping("/getSelectedDish/{id}")
    public ResponseEntity<MenuItemsDTO> getSelectedDish(@PathVariable long id) {
        MenuItemsDTO menuItemsDTO = menuItemsService.getSelectedDish(id);
        if (menuItemsDTO.getItemName() == null) {
            return new ResponseEntity<>(menuItemsDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menuItemsDTO, HttpStatus.FOUND);
    }

    @GetMapping("/getItems")
    public ResponseEntity<List<CategorieDTO>> getAllItems() {
        List<CategorieDTO> categoriesList = categoriesService.getAllItems();
        return new ResponseEntity<>(categoriesList, HttpStatus.FOUND);
    }

    @GetMapping("/getIngredients")
    public ResponseEntity<List<Ingredients>> getAllIngredients() {
        List<Ingredients> ingredients = ingredientsService.getAllIngredients();
        return new ResponseEntity<>(ingredients, HttpStatus.FOUND);
    }

    @GetMapping("/getIngredient/{id}")
    public ResponseEntity<Ingredients> getIngredient(@PathVariable long id) {
        Ingredients ingredients = ingredientsService.getIngredient(id);
        return new ResponseEntity<>(ingredients, HttpStatus.FOUND);
    }

    /* Delete APIs */
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {

        String status = categoriesService.deleteCategory(id);

        if (status.equals(Constants.NO_DATA_FOUND)) {
            return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCategories")
    public ResponseEntity<String> deleteCategories() {
        String status = categoriesService.deleteAllCategories();
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable long id) {

        String status = menuItemsService.deleteItem(id);

        if (status.equals(Constants.NO_DATA_FOUND)) {
            return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @DeleteMapping("/deleteIngredient/{id}")
    public ResponseEntity<ResponseDTO> deleteIngredient(@PathVariable long id) {

        ResponseDTO responseDTO = ingredientsService.deleteIngredients(id);

        if (responseDTO.getMessage().equals(Constants.NO_DATA_FOUND)) {
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
        if (responseDTO.getMessage().equals(Constants.NOT_DELETED)) {
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteIngredients")
    public ResponseEntity<String> deleteAllIngredient() {
        String message = ingredientsService.deleteAllIngredients();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    /* Update APIs */
    @PatchMapping("/updateCategory/{id}")
    public ResponseEntity<ResponseDTO> updateCategory(@PathVariable long id, @RequestBody Categories categories) {

        ResponseDTO responseDTO = categoriesService.updateCategory(id, categories);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PatchMapping("/updateItem/{id}")
    public ResponseEntity<MenuItemsDTO> updateItem(@PathVariable long id, @RequestBody ItemUpdateDTO items) {

        MenuItemsDTO menuItems = menuItemsService.updateItem(id, items);
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    @PatchMapping("/updateIngredient/{id}")
    public ResponseEntity<ResponseDTO> updateIngredient(@PathVariable long id, @RequestBody Ingredients ingredients) {
        ResponseDTO responseDTO = ingredientsService.updateIngredients(id, ingredients);
        if (responseDTO.getMessage().equals(Constants.NO_DATA_FOUND)) {
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
