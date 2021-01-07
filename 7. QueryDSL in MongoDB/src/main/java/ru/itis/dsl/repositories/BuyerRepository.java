package ru.itis.dsl.repositories;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.itis.dsl.models.*;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BuyerRepository extends MongoRepository<Buyer, ObjectId>{
//    public Optional<Buyer> findById(String id);
}
