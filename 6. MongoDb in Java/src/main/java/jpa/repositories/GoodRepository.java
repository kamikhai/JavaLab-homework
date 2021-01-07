package jpa.repositories;

import jpa.models.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface GoodRepository extends MongoRepository<Good, Long> {
    @RestResource(path = "byNameOrPrice", rel = "byNameOrPrice")
    @Query(value = "{$or: [ {name: ?0}, {cost: {$lt: ?1}}]}")
    List<Good> findByNameAndCostBefore(@Param("name") String name, @Param("cost") int cost);
}
