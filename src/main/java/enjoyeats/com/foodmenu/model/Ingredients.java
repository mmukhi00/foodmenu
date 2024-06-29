package enjoyeats.com.foodmenu.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ingredients")
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long ingredientId;

    @Column(name = "ingre_name")
    private String ingredientName;

}
