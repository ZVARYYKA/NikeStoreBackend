package dev.zvaryyka.nikestorebackend.repositories;

import dev.zvaryyka.nikestorebackend.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Integer> {
}
