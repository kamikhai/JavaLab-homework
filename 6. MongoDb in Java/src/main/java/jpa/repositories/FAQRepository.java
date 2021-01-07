package jpa.repositories;

import jpa.models.*;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FAQRepository extends MongoRepository<FAQ, Long> {
}
