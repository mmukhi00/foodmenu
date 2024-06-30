package enjoyeats.com.foodmenu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import enjoyeats.com.foodmenu.constants.Constants;
import enjoyeats.com.foodmenu.dto.ResponseDTO;
import enjoyeats.com.foodmenu.model.Ingredients;
import enjoyeats.com.foodmenu.repo.IngredientsRepo;
import enjoyeats.com.foodmenu.repo.MenuItemIngredientsRepo;

@Service
public class IngredientsService {

    @Autowired
    IngredientsRepo ingredientsRepo;
    @Autowired
    MenuItemIngredientsRepo menuItemIngredientsRepo;

    // Save ingredient
    public ResponseDTO saveIngredients(Ingredients ingredients) {
        ResponseDTO responseDTO = new ResponseDTO();

        Optional<Ingredients> ingredient = ingredientsRepo.findByIngredientName(ingredients.getIngredientName());
        if (ingredient.isPresent()) {
            responseDTO.setId(ingredient.get().getIngredientId());
            responseDTO.setMessage(Constants.CREATED);
            return responseDTO;
        }

        Ingredients savedIngredients = ingredientsRepo.save(ingredients);

        if (ingredientsRepo.existsById(savedIngredients.getIngredientId())) {
            responseDTO.setMessage(Constants.NOT_CREATED);
        }

        responseDTO.setId(savedIngredients.getIngredientId());
        responseDTO.setMessage(Constants.CREATED);
        return responseDTO;
    }

    // Update ingredient
    public ResponseDTO updateIngredients(long id, Ingredients ingredients) {

        Optional<Ingredients> ingredient = ingredientsRepo.findById(id);
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setId(id);

        if (ingredient.isEmpty()) {
            responseDTO.setMessage(Constants.NO_DATA_FOUND);
            return responseDTO;
        }
        
        ingredient.get().setIngredientName(ingredients.getIngredientName());

        ingredientsRepo.save(ingredient.get());
        responseDTO.setMessage(Constants.DATA_UPDATED);
        return responseDTO;
    }

    // Get ingredient
    public Ingredients getIngredient(long id) {
        Ingredients ingredients = ingredientsRepo.findById(id).orElseThrow(() -> new RuntimeException(Constants.NO_DATA_FOUND));
        return ingredients;
    }

    // Get list of ingredients
    public List<Ingredients> getAllIngredients() {
        List<Ingredients> ingredients = ingredientsRepo.findAll();
        return ingredients;
    }

    // Delete ingredient
    public ResponseDTO deleteIngredients(long id) {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setId(id);

        if (!ingredientsRepo.existsById(id)) {
            responseDTO.setMessage(Constants.NO_DATA_FOUND);
            return responseDTO;
        }

        ingredientsRepo.deleteById(id);
        menuItemIngredientsRepo.deleteByIngreId(id);

        if (ingredientsRepo.existsById(id)) {
            responseDTO.setMessage(Constants.NOT_DELETED);
        } else {
            responseDTO.setMessage(Constants.DELETED);
        }

        return responseDTO;
    }

    public String deleteAllIngredients() {
        ingredientsRepo.deleteAll();
        menuItemIngredientsRepo.deleteAll();
        return Constants.DELETED;
    }

}
