package ru.itis.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hateoas.models.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
