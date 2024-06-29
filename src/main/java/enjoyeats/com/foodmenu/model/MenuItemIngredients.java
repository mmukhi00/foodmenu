package enjoyeats.com.foodmenu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "menuitemingredients")
public class MenuItemIngredients {

   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.AUTO)
   @JsonIgnore
   private long menuItems;

   @Column(name="ingre_id")
   @JsonIgnore
   private long ingredientId;

//   @Column(insertable=false, updatable=false)
//   private String ingredientName;


}
