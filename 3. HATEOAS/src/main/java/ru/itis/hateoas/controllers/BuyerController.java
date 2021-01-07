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
import ru.itis.hateoas.services.BuyerService;

@RepositoryRestController
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    @RequestMapping(value = "/buyers/{buyer-id}/confirm", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> confirm(@PathVariable("buyer-id") Long buyerId) {
        return ResponseEntity.ok(
                EntityModel.of(buyerService.makeConfirmed(buyerId)));
    }
}
