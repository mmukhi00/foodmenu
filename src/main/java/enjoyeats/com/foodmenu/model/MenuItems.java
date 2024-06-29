package enjoyeats.com.foodmenu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "menuitems")
public class MenuItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private  String price;

    @Transient
    private List<Long> ingredients=new ArrayList<>();

    @OneToMany(targetEntity = MenuItemIngredients.class, cascade = CascadeType.ALL)
    @JoinColumn(name="menu_id",referencedColumnName = "item_id")
    private List<MenuItemIngredients> menuingredients=new ArrayList<>() ;


}
