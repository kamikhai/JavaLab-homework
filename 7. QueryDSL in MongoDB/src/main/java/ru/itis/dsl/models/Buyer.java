package ru.itis.dsl.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "buyer")
public class Buyer implements Serializable {
    @Id
    @MongoId
    private ObjectId _id;

    private String email;
    private String name;
    private String password;
    private String surname;
    @DBRef
    private ObjectId state;

//    public void makeConfirmed(){
//        if (this.state.getName().equals("not_confirmed")) {
//            this.state = AccountState.builder().name("confirmed").build();
//        } else {
//            throw new IllegalStateException();
//        }
//    }
}
