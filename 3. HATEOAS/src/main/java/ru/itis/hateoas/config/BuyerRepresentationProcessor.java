package ru.itis.hateoas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.itis.hateoas.controllers.BuyerController;
import ru.itis.hateoas.controllers.BuyerOrderController;
import ru.itis.hateoas.models.AccountState;
import ru.itis.hateoas.models.Buyer;
import ru.itis.hateoas.models.BuyerOrder;
import ru.itis.hateoas.models.OrderState;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BuyerRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Buyer>> {

    @Autowired
    private RepositoryEntityLinks links;

    @Override
    public EntityModel<Buyer> process(EntityModel<Buyer> model) {
        Buyer buyer = model.getContent();
        if (buyer != null && buyer.getState().equals(AccountState.NOT_CONFIRMED)) {
            model.add(linkTo(methodOn(BuyerController.class)
                    .confirm(buyer.getId())).withRel("confirm"));
        }
        return model;
    }
}
