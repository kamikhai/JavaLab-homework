package ru.itis.dsl.repositories;

import ru.itis.dsl.models.*;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopRepository extends MongoRepository<Shop, String> {

}
