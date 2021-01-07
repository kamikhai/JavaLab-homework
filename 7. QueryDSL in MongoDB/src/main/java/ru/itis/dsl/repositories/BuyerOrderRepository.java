package ru.itis.dsl.repositories;


import ru.itis.dsl.models.*;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BuyerOrderRepository extends MongoRepository<BuyerOrder, String> {
}
