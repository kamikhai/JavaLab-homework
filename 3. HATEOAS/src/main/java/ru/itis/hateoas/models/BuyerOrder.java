package ru.itis.hateoas.models;

import lombok.*;
import ru.itis.hateoas.dto.BuyerDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Buyer buyer;

    @ManyToMany
    private List<Good> goods;

    private OrderState state;


    public void makeOrderStateDelivery(){
        if (this.state.equals(OrderState.PROCESSING)) {
            this.state = OrderState.DELIVERY;
        } else {
            throw new IllegalStateException();
        }
    }

    public void makeOrderStateAtThePostOffice(){
        if (this.state.equals(OrderState.DELIVERY)) {
            this.state = OrderState.AT_THE_POST_OFFICE;
        } else {
            throw new IllegalStateException();
        }
    }

    public void makeOrderStateReceived(){
        if (this.state.equals(OrderState.AT_THE_POST_OFFICE)) {
            this.state = OrderState.RECEIVED;
        } else {
            throw new IllegalStateException();
        }
    }
}
