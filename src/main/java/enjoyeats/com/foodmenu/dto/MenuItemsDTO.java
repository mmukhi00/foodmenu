package enjoyeats.com.foodmenu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuItemsDTO {
    private long id;
    private String itemName;
    private String description;
    private  String price;
    private List<String> ingredientName;
//    private List<Long> ingredientIds;
}
