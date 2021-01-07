package ru.itis.dsl.repositories;

import org.bson.types.ObjectId;
import ru.itis.dsl.models.*;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GoodRepository extends MongoRepository<Good, ObjectId> {
//    @RestResource(path = "byNameOrPrice", rel = "byNameOrPrice")
//    @Query(value = "{$or: [ {name: ?0}, {cost: {$lt: ?1}}]}")
//    List<Good> findByNameAndCostBefore(@Param("name") String name, @Param("cost") int cost);
}
