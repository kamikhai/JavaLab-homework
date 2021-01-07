package ru.itis.hateoas.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.itis.hateoas.models.Buyer;
import ru.itis.hateoas.models.BuyerOrder;
import ru.itis.hateoas.models.Good;
import ru.itis.hateoas.models.OrderState;

public interface BuyerOrderRepository extends JpaRepository<BuyerOrder, Long>{
}
