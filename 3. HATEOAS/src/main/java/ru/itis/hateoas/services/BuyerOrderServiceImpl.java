package ru.itis.hateoas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.hateoas.models.BuyerOrder;
import ru.itis.hateoas.repositories.BuyerOrderRepository;

@Service
public class BuyerOrderServiceImpl implements BuyerOrderService {

    @Autowired
    private BuyerOrderRepository buyerOrderRepository;


    @Override
    public BuyerOrder makeOrderStateDelivery(Long orderId) {
        BuyerOrder buyerOrder = buyerOrderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        buyerOrder.makeOrderStateDelivery();
        buyerOrderRepository.save(buyerOrder);
        return buyerOrder;
    }

    @Override
    public BuyerOrder makeOrderStateAtThePostOffice(Long orderId) {
        BuyerOrder buyerOrder = buyerOrderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        buyerOrder.makeOrderStateAtThePostOffice();
        buyerOrderRepository.save(buyerOrder);
        return buyerOrder;
    }

    @Override
    public BuyerOrder makeOrderStateReceived(Long orderId) {
        BuyerOrder buyerOrder = buyerOrderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        buyerOrder.makeOrderStateReceived();
        buyerOrderRepository.save(buyerOrder);
        return buyerOrder;
    }
}
