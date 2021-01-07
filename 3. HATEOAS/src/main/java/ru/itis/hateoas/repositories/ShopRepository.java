package ru.itis.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hateoas.models.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
