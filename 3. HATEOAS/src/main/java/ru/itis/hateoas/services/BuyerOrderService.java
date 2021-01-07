package ru.itis.hateoas.services;

import ru.itis.hateoas.models.BuyerOrder;

public interface BuyerOrderService {
    BuyerOrder makeOrderStateDelivery(Long orderId);
    BuyerOrder makeOrderStateAtThePostOffice(Long orderId);
    BuyerOrder makeOrderStateReceived(Long orderId);
}
