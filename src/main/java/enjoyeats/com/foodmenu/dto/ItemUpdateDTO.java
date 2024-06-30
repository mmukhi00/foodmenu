package enjoyeats.com.foodmenu.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemUpdateDTO {

    private String itemName;
    private String description;
    private String price;
    private List<Long> ingredientIds;
    private Boolean updateExisting;
}
