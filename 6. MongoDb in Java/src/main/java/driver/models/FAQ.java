package driver.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FAQ {
    private Long id;

    private Buyer buyer;
    private String question;
    private String answer;
}
