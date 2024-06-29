package enjoyeats.com.foodmenu.service;

import enjoyeats.com.foodmenu.constants.Constants;
import enjoyeats.com.foodmenu.dto.ResponseDTO;
import enjoyeats.com.foodmenu.model.Ingredients;
import enjoyeats.com.foodmenu.repo.IngredientsRepo;
import enjoyeats.com.foodmenu.repo.MenuItemIngredientsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventPublicationInterceptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsService {

    @Autowired
    IngredientsRepo ingredientsRepo;
    @Autowired
    MenuItemIngredientsRepo menuItemIngredientsRepo;

            public ResponseDTO saveIngredients(Ingredients ingredients){
                ResponseDTO responseDTO=new ResponseDTO();

             Optional<Ingredients> ingredient= ingredientsRepo.findByIngredientName(ingredients.getIngredientName());
             if(ingredient.isPresent())
             {
                 responseDTO.setId(ingredient.get().getIngredientId());
                 responseDTO.setMessage(Constants.CREATED);
                 return  responseDTO;
             }


                Ingredients savedIngredients= ingredientsRepo.save(ingredients);

                if(ingredientsRepo.existsById(savedIngredients.getIngredientId())){
                    responseDTO.setMessage(Constants.NOT_CREATED);
                }

                responseDTO.setId(savedIngredients.getIngredientId());
                responseDTO.setMessage(Constants.CREATED);
                return responseDTO;
            }

            // update
    public  ResponseDTO updateIngredients(long id,Ingredients ingredients){

                Optional<Ingredients> ingredient= ingredientsRepo.findById(id);
                 ResponseDTO responseDTO=new ResponseDTO();
                  responseDTO.setId(id);
                 if(ingredient.isEmpty()){
                     responseDTO.setMessage(Constants.NO_DATA_FOUND);
                     return responseDTO;
                 }

                if(ingredients.getIngredientName()!=null){
                  ingredient.get().setIngredientName(ingredients.getIngredientName());
                }
                ingredientsRepo.save(ingredient.get());
                responseDTO.setMessage(Constants.DATA_UPDATED);
              return  responseDTO;
    }



    // get
      public Ingredients getIngredient(long id){
               Ingredients ingredients= ingredientsRepo.findById(id).orElseThrow(()->new RuntimeException("No data found"));
                return ingredients;
      }


    public List<Ingredients> getAllIngredients()
    {
        List<Ingredients> ingredients= ingredientsRepo.findAll();
        return ingredients;
    }

    // Delete
            public  ResponseDTO deleteIngredients(long id){

                ResponseDTO responseDTO=new ResponseDTO();
                responseDTO.setId(id);

                if(!ingredientsRepo.existsById(id)){
                    responseDTO.setMessage(Constants.NO_DATA_FOUND);
                    return  responseDTO;
                }

                ingredientsRepo.deleteById(id);
                menuItemIngredientsRepo.deleteByIngreId(id);

                if(ingredientsRepo.existsById(id)){
                    responseDTO.setMessage(Constants.NOT_DELETED);
                }
                else {
                    responseDTO.setMessage(Constants.DELETED);
                }

                return responseDTO;
            }


            public  String  deleteAllIngredients()
            {
                ingredientsRepo.deleteAll();
                menuItemIngredientsRepo.deleteAll();
                return Constants.DELETED;
            }

}
