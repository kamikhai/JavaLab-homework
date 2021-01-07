package spring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerOrder {
    private Long id;

    private Buyer buyer;

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
