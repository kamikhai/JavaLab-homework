package ru.itis.hateoas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.hateoas.dto.BuyerDto;
import ru.itis.hateoas.models.Buyer;
import ru.itis.hateoas.models.BuyerOrder;
import ru.itis.hateoas.repositories.BuyerRepository;

@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private BuyerRepository buyerRepository;

    @Override
    public BuyerDto makeConfirmed(Long orderId) {
        Buyer buyer = buyerRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        buyer.makeConfirmed();
        buyerRepository.save(buyer);
        return BuyerDto.fromBuyer(buyer);
    }
}
