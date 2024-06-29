package enjoyeats.com.foodmenu;

import enjoyeats.com.foodmenu.constants.Constants;
import enjoyeats.com.foodmenu.dto.CategorieDTO;
import enjoyeats.com.foodmenu.dto.ItemUpdateDTO;
import enjoyeats.com.foodmenu.dto.MenuItemsDTO;
import enjoyeats.com.foodmenu.dto.ResponseDTO;
import enjoyeats.com.foodmenu.model.Categories;
import enjoyeats.com.foodmenu.model.Ingredients;
import enjoyeats.com.foodmenu.repo.CategoriesRepo;
import enjoyeats.com.foodmenu.repo.IngredientsRepo;
import enjoyeats.com.foodmenu.repo.MenuItemsRepo;
import enjoyeats.com.foodmenu.service.CategoriesService;
import enjoyeats.com.foodmenu.service.IngredientsService;
import enjoyeats.com.foodmenu.service.MenuItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {


    @Autowired
    CategoriesRepo categoriesRepo;
    @Autowired
    MenuItemsRepo menuItemsRepo;
    @Autowired
    IngredientsRepo ingredientsRepo;

    @Autowired
    CategoriesService categoriesService;
    @Autowired
    MenuItemsService menuItemsService;
   @Autowired
    IngredientsService ingredientsService;



     /* Insert APIS */

    @PostMapping("/addCategory")
    public ResponseEntity<ResponseDTO> addCategory(@RequestBody Categories category)
    {

        ResponseDTO responseDTO= categoriesService.saveCategories(category);

       if(!responseDTO.getMessage().equals(Constants.CREATED)){
           return  new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
       }
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.CREATED);
    }


    @PostMapping("/addIngredients")
    public ResponseEntity<ResponseDTO> addIngredients(@RequestBody Ingredients ingredients){

        ResponseDTO responseDTO= ingredientsService.saveIngredients(ingredients);

        if (responseDTO.getMessage().equals(Constants.NOT_CREATED)){
            return  new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.CREATED);
    }



      /* Fetch APIS */

    @GetMapping("/getItem/{id}")
    public ResponseEntity<CategorieDTO> getItem(@PathVariable long id)
    {
     CategorieDTO categorieDTO= categoriesService.getSelectedItem(id);
         if(categorieDTO.getCategoryName()==null){
             return new ResponseEntity<>(categorieDTO,HttpStatus.NOT_FOUND);
         }
          return new ResponseEntity<>(categorieDTO,HttpStatus.FOUND);
    }

    @GetMapping("/getItems")
    public ResponseEntity<List<CategorieDTO>> getAllItems() {
       List<CategorieDTO> categoriesList= categoriesService.getAllItems();
       return new ResponseEntity<>(categoriesList,HttpStatus.FOUND);
    }


    @GetMapping("/getIngredients")
    public ResponseEntity<List<Ingredients>> getAllIngredients() {
        List<Ingredients> ingredients= ingredientsService.getAllIngredients();
        return new ResponseEntity<>(ingredients,HttpStatus.FOUND);
    }

    @GetMapping("/getIngredient/{id}")
    public ResponseEntity<Ingredients> getIngredient(@PathVariable long id) {
        Ingredients ingredients= ingredientsService.getIngredient(id);
        return new ResponseEntity<>(ingredients,HttpStatus.FOUND);
    }

     /* Delete APIS */

//    Delete all category records
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){

       String status=categoriesService.deleteCategory(id);

       if(status.equals(Constants.NO_DATA_FOUND)){
           return new ResponseEntity<>(status,HttpStatus.NOT_FOUND);
       }
        return new ResponseEntity<>(status,HttpStatus.OK);
    }


//   Delete Items from category
    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<String>  deleteItem(@PathVariable long id){

        String status= menuItemsService.deleteItem(id);

        if(status.equals(Constants.NO_DATA_FOUND)){
            return new ResponseEntity<>(status,HttpStatus.NOT_FOUND);
        }

        return  new ResponseEntity<>(status,HttpStatus.OK);
    }


    @DeleteMapping("/deleteIngredient/{id}")
    public ResponseEntity<ResponseDTO>  deleteIngredient(@PathVariable long id){

        ResponseDTO responseDTO= ingredientsService.deleteIngredients(id);

        if(responseDTO.getMessage().equals(Constants.NO_DATA_FOUND)) {
            return  new ResponseEntity<>(responseDTO,HttpStatus.NOT_FOUND);
        }
        if(responseDTO.getMessage().equals(Constants.NOT_DELETED)) {
            return  new ResponseEntity<>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }


    @DeleteMapping("/deleteIngredients")
    public ResponseEntity<String> deleteAllIngredient()
    {
         String message=ingredientsService.deleteAllIngredients();
        return new ResponseEntity<String>(message,HttpStatus.OK);
    }


    /* Update APIS */

    @PatchMapping("/updateCategory/{id}")
    public ResponseEntity<ResponseDTO> updateCategory(@PathVariable long id, @RequestBody Categories categories){

        ResponseDTO responseDTO =categoriesService.updateCategory(id,categories);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }


    @PatchMapping("/updateItem/{id}")
    public ResponseEntity<MenuItemsDTO> updateItem(@PathVariable long id, @RequestBody ItemUpdateDTO items){

        MenuItemsDTO menuItems =menuItemsService.updateItem(id,items);
        return new ResponseEntity<>(menuItems,HttpStatus.OK);
    }

    @PatchMapping("/updateIngredient/{id}")
    public ResponseEntity<ResponseDTO> updateIngredient(@PathVariable long id, @RequestBody Ingredients ingredients)
    {
        ResponseDTO responseDTO= ingredientsService.updateIngredients(id,ingredients);
        if( responseDTO.getMessage().equals(Constants.NO_DATA_FOUND)){
            return  new ResponseEntity<>(responseDTO,HttpStatus.NOT_FOUND);
    }

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
}
