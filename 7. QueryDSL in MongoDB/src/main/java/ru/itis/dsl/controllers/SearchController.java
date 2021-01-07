package ru.itis.dsl.controllers;

import com.querydsl.core.types.Predicate;
import org.bson.types.ObjectId;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import ru.itis.dsl.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dsl.models.BuyerOrder;
import ru.itis.dsl.repositories.BuyerRepository;
import ru.itis.dsl.repositories.GoodRepository;
import ru.itis.dsl.repositories.OrdersRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
public class SearchController {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private GoodRepository goodRepository;

    @GetMapping("/orders/search")
    public ResponseEntity<List<OrderDto>> searchByPredicate(@QuerydslPredicate(root = BuyerOrder.class) Predicate predicate) {
        StreamSupport.stream(ordersRepository.findAll(predicate).spliterator(), true).forEach(order -> System.out.println("aaaaaa" + order.getName()));
        return ResponseEntity.ok(
                StreamSupport.stream(ordersRepository.findAll(predicate).spliterator(), true)
                        .map(order ->
                                OrderDto.builder()
                        .id(order.get_id().toString())
                        .buyerName(buyerRepository.findById(order.getName()).get().getName())
                        .goodsNames(order.getGoods().stream().map(good -> goodRepository.findById(good).get().getName()).collect(Collectors.toList()))
                        .build()).collect(Collectors.toList()));
    }
}
