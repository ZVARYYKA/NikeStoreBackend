package dev.zvaryyka.nikestorebackend.services;

import dev.zvaryyka.nikestorebackend.dto.OrderDTO;
import dev.zvaryyka.nikestorebackend.models.Bucket;
import dev.zvaryyka.nikestorebackend.models.Order;
import dev.zvaryyka.nikestorebackend.models.OrderDetails;
import dev.zvaryyka.nikestorebackend.models.Person;
import dev.zvaryyka.nikestorebackend.repositories.BucketsRepository;
import dev.zvaryyka.nikestorebackend.repositories.OrderDetailsRepository;
import dev.zvaryyka.nikestorebackend.repositories.OrderRepository;
import dev.zvaryyka.nikestorebackend.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final BucketsRepository bucketRepository;
    private final PeopleRepository peopleRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, BucketsRepository bucketsRepository, PeopleRepository peopleRepository, OrderDetailsRepository orderDetailsRepository) {
        this.orderRepository = orderRepository;
        this.bucketRepository = bucketsRepository;
        this.peopleRepository = peopleRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    private Order convertToOrder(OrderDTO orderDTO) {
        return new Order(
                peopleRepository.findById(orderDTO.getUserId()).get(),
                orderDTO.getAddress(),
                orderDTO.getAbout()

        );

    }
    public void addOrderAndAddInfoInOrderDetails(OrderDTO orderDTO) {
        Person user = peopleRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Bucket> buckets = bucketRepository.findByUser(user);

        Order order = new Order();
        order.setUser(user);
        order.setAddress(orderDTO.getAddress());
        order.setAbout(orderDTO.getAbout());

        orderRepository.save(order);
        for (Bucket bucket : buckets) {
            bucket.setStatus(true);
            OrderDetails orderDetails = new OrderDetails(order, bucket);

            orderDetailsRepository.save(orderDetails);
        }


    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();


    }
}
