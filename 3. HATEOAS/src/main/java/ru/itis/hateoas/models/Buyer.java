package ru.itis.hateoas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String surname;
    private String name;
    private AccountState state;

    @ManyToMany
    private List<Good> goodsInBasket;

    public void makeConfirmed(){
        if (this.state.equals(AccountState.NOT_CONFIRMED)) {
            this.state = AccountState.CONFIRMED;
        } else {
            throw new IllegalStateException();
        }
    }
}
