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
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "coupon")
public class Coupon {
    @Id
    @MongoId
    private ObjectId _id;
    @DBRef
    private ObjectId shop;
    @DBRef
    private ObjectId buyer;

    private int discountPercentage;
}
