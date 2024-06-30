package enjoyeats.com.foodmenu.model;

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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    @JoinColumn(name = "cat_id", referencedColumnName = "id")
    private List<MenuItems> menuItems;

}
