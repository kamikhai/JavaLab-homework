package ru.itis.hateoas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.hateoas.services.BuyerOrderService;

@RepositoryRestController
public class BuyerOrderController {

    @Autowired
    private BuyerOrderService buyerOrderService;

    @RequestMapping(value = "/buyerOrders/{order-id}/delivery", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> delivery(@PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(
                EntityModel.of(buyerOrderService.makeOrderStateDelivery(orderId)));
    }

    @RequestMapping(value = "/buyerOrders/{order-id}/atThePostOffice", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> atThePostOffice(@PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(
                EntityModel.of(buyerOrderService.makeOrderStateAtThePostOffice(orderId)));
    }

    @RequestMapping(value = "/buyerOrders/{order-id}/received", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> received(@PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(
                EntityModel.of(buyerOrderService.makeOrderStateReceived(orderId)));
    }
}
