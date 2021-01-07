package driver.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {
    private Long id;

    private Shop shop;

    private List<Buyer> buyer;

    private int discountPercentage;
}
