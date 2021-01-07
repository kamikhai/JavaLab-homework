package ru.itis.hateoas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.itis.hateoas.controllers.BuyerOrderController;
import ru.itis.hateoas.models.BuyerOrder;
import ru.itis.hateoas.models.OrderState;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BuyerOrderRepresentationProcessor implements RepresentationModelProcessor<EntityModel<BuyerOrder>> {

    @Override
    public EntityModel<BuyerOrder> process(EntityModel<BuyerOrder> model) {
        BuyerOrder order = model.getContent();
        if (order != null && order.getState().equals(OrderState.PROCESSING)) {
            model.add(linkTo(methodOn(BuyerOrderController.class)
                    .delivery(order.getId())).withRel("delivery"));
        }
        if (order != null && order.getState().equals(OrderState.DELIVERY)) {
            model.add(linkTo(methodOn(BuyerOrderController.class)
                    .atThePostOffice(order.getId())).withRel("atThePostOffice"));
        }

        if (order != null && order.getState().equals(OrderState.AT_THE_POST_OFFICE)) {
            model.add(linkTo(methodOn(BuyerOrderController.class)
                    .received(order.getId())).withRel("received"));
        }
        return model;
    }
}
