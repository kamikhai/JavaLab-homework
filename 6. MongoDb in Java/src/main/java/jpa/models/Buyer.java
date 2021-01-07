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
@Document(collection = "buyer")
public class Buyer {
    @Id
    private String _id;

    private String email;
    private String password;
    private String surname;
    private String name;
    @DBRef
    private AccountState state;

//    public void makeConfirmed(){
//        if (this.state.getName().equals("not_confirmed")) {
//            this.state = AccountState.builder().name("confirmed").build();
//        } else {
//            throw new IllegalStateException();
//        }
//    }
}
