package ru.itis.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hateoas.models.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
}
