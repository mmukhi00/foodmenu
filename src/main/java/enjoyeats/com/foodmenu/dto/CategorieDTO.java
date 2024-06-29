package enjoyeats.com.foodmenu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategorieDTO {

    private long id;
    private String categoryName;
    private List<MenuItemsDTO> menuItems;

}
