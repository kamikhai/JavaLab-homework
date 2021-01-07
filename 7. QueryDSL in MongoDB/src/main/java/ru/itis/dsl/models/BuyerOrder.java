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
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "buyerOrder")
public class BuyerOrder implements Serializable {
    @Id
    @MongoId
    private ObjectId _id;

    @DBRef
    private ObjectId name;
    @DBRef
    private List<ObjectId> goods;
    @DBRef
    private ObjectId shop;
    @DBRef
    private ObjectId state;
}
