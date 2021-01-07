package jpa.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "buyerOrder")
public class BuyerOrder {
    @Id
    private Long _id;

    private Buyer buyer;
    @DBRef
    private List<Good> goods;

    private OrderState state;


    public void makeOrderStateDelivery() {
        if (this.state.equals(OrderState.PROCESSING)) {
            this.state = OrderState.DELIVERY;
        } else {
            throw new IllegalStateException();
        }
    }

    public void makeOrderStateAtThePostOffice() {
        if (this.state.equals(OrderState.DELIVERY)) {
            this.state = OrderState.AT_THE_POST_OFFICE;
        } else {
            throw new IllegalStateException();
        }
    }

    public void makeOrderStateReceived() {
        if (this.state.equals(OrderState.AT_THE_POST_OFFICE)) {
            this.state = OrderState.RECEIVED;
        } else {
            throw new IllegalStateException();
        }
    }
}
