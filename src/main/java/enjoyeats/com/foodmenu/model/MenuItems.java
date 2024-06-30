package enjoyeats.com.foodmenu.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@Table(name = "menuitems")
@NoArgsConstructor
public class MenuItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long itemId;

    @Column(name = "item_name")
    @NonNull
    private String itemName;

    @Column(name = "description")
    @NonNull
    private String description;

    @Column(name = "price")
    @NonNull
    private String price;

    @Transient
    private List<Long> ingredients = new ArrayList<>();

    @OneToMany(targetEntity = MenuItemIngredients.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id", referencedColumnName = "item_id")
    private List<MenuItemIngredients> menuingredients = new ArrayList<>();

}
