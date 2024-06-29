package enjoyeats.com.foodmenu.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "categories")
@NoArgsConstructor
public class Categories {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long categoryId;

    @Column(name = "category_name")
    @NonNull
    private String categoryName;

    @OneToMany(targetEntity = MenuItems.class, cascade = CascadeType.ALL)
    @JoinColumn(name="cat_id",referencedColumnName = "id")
    private List<MenuItems> menuItems;


}
