package ru.itis.dsl.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.dsl.models.BuyerOrder;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdersRepository extends MongoRepository<BuyerOrder, String>, QuerydslPredicateExecutor<BuyerOrder>{

}
