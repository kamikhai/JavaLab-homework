package ru.itis.hateoas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.hateoas.models.AccountState;
import ru.itis.hateoas.models.Buyer;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BuyerDto {
    private Long id;
    private String email;
    private String surname;
    private String name;
    private AccountState state;

    public static BuyerDto fromBuyer(Buyer buyer){
        return BuyerDto.builder()
                .email(buyer.getEmail())
                .id(buyer.getId())
                .name(buyer.getName())
                .surname(buyer.getSurname())
                .state(buyer.getState())
                .build();
    }
}
