package ru.itis.hateoas.services;

import ru.itis.hateoas.dto.BuyerDto;
import ru.itis.hateoas.models.Buyer;
import ru.itis.hateoas.models.BuyerOrder;

public interface BuyerService {
    BuyerDto makeConfirmed(Long orderId);
}
