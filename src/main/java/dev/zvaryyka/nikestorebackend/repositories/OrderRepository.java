package dev.zvaryyka.nikestorebackend.repositories;

import dev.zvaryyka.nikestorebackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

}
