package ru.itis.hateoas.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.itis.hateoas.models.Good;

import java.util.List;

@RepositoryRestResource
public interface GoodRepository extends PagingAndSortingRepository<Good, Long> {
    @RestResource(path = "byName", rel = "name")
    List<Good> findAllByName(String name);
}
