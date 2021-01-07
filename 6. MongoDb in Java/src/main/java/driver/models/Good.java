package driver.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Good {
    private String _id;
    private String name;
    private Shop seller;
    private Integer cost;

}
