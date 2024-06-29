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
public class ItemUpdateDTO {

    private String itemName;
    private String description;
    private  String price;
    private List<String> ingredientName;
    private Boolean updateExisting;
}
