package jpa.repositories;

import jpa.models.*;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BuyerRepository extends MongoRepository<Buyer, String> {
    public Buyer findByEmail(String email);
    public Buyer findByName(String name);
}
