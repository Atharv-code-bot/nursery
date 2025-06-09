package com.sakshi.nursery.service;

import com.sakshi.nursery.dto.AdminOrderResponseDTO;
import com.sakshi.nursery.dto.BookingDTO;
import com.sakshi.nursery.dto.CustomerOrderResponseDTO;
import com.sakshi.nursery.model.*;
import com.sakshi.nursery.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
@Service
@Transactional
public class OrderService {
    @PersistenceContext
    private EntityManager entityManager;
    private static final int MIN = 9921;
    private static final int MAX = 999999;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private OrderItemRepository orderItemRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public CustomerOrderResponseDTO CustomerOrderPlant(Long userId, Long productId, BookingDTO bookingDTO) {
        // 1. Get user
        User user = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        // 2. Get user's cart
        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // 3. Get cart item for given product
        CartItem cartItem = cartItemRepository
                .findByCart_IdAndProduct_Id(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        // 4. Fetch product from DB using productId
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 5. Calculate total price
        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        // 6. Create or reuse order
        Order order = orderRepo.findByUserId(userId).orElse(null);
        if (order == null) {
            order = new Order();
            order.setId(generateRandomSixDigitId());
            order.setUser(user);

            order = orderRepo.save(order); // persist to get order ID
        }

        // 7. Create OrderItem
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(cartItem.getQuantity());
        item.setPrice(product.getPrice());
        item.setStatus(OrderStatus.PENDING);
        item.setTotalPrice(total);
        item.setBookingDate(new java.sql.Date(System.currentTimeMillis()));
        item.setAddress(bookingDTO.getAddress());
        item.setDeliveryDate(bookingDTO.getDeliveryDate());
        item.setPaymentStatus(bookingDTO.getPaymentStatus());
        orderItemRepo.save(item);
        cartItemRepository.deleteByCartIdAndProductId(cart.getId(), productId);
        CustomerOrderResponseDTO dto = new CustomerOrderResponseDTO(product.getName(), cartItem.getQuantity(), item.getStatus(), item.getTotalPrice(), item.getDeliveryDate(), item.getAddress(), item.getPaymentStatus(), order.getId());
        return dto;
    }
    public List<CustomerOrderResponseDTO> getOrdersByUserID(long userId) {
        Order orders = orderRepo.findByUserId(userId).orElse(null);
        if (orders == null) throw new RuntimeException("No orders found for user");
        List<CustomerOrderResponseDTO> responseList = new ArrayList<>();
        List<OrderItem> orderItems = orderItemRepo.findByOrderId(orders.getId());
        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            CustomerOrderResponseDTO dto = new CustomerOrderResponseDTO(product.getName(), item.getQuantity(), item.getStatus(), item.getTotalPrice(), item.getDeliveryDate(), item.getAddress(), item.getPaymentStatus(), orders.getId());
            responseList.add(dto);
        }
        return responseList;
    }
    public List<AdminOrderResponseDTO> getAllOrders() {
        List<AdminOrderResponseDTO> responseList = new ArrayList<>();

        List<Order> allOrders = orderRepo.findAll();

        for (Order order : allOrders) {
            String customerName = order.getUser().getName(); // Adjust based on your User entity

            List<OrderItem> items = orderItemRepo.findByOrderId(order.getId());

            for (OrderItem item : items) {
                AdminOrderResponseDTO dto = new AdminOrderResponseDTO(
                        item.getAddress(),
                        item.getBookingDate(), // Booking date
                        item.getDeliveryDate(),
                        item.getPaymentStatus(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getStatus(),
                        item.getTotalPrice(),
                        customerName,
                        item.getProduct().getName()
                );

                responseList.add(dto);
            }
        }

        return responseList;
    }
    public void updateSingleOrderItemByCustomerNameAndAddress(AdminOrderResponseDTO dto, int userId, Long productId) {
        // Find user by name
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new RuntimeException("user Not found");

        Product product = productRepository.findByName(dto.getProductName()).orElseThrow(() -> new RuntimeException("not Found"));


        // Find orders for this user
        Order orders = orderRepo.findByUserId(user.getId()).orElse(null);

        if (orders == null) {
            throw new RuntimeException("No orders found for user: " + dto.getCustomerName());
        }
        OrderItem orderItem = orderItemRepo.findByOrder_IdAndProduct_Id(orders.getId(), productId).orElseThrow(() -> new RuntimeException("not found"));
        orderItem.setDeliveryDate(dto.getDeliveryDate());
        orderItem.setPaymentStatus(dto.getPaymentStatus());
        System.out.println(orderItem.getPaymentStatus());
        orderItem.setStatus(dto.getStatus());
        orderItem.setPrice(dto.getPrice());
        orderItem.setOrder(new Order(orders.getId()));
        orderItem.setPrice(dto.getPrice());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setTotalPrice(dto.getTotalPrice());
        orderItem.setProduct(new Product(product.getId()));
        orderItemRepo.save(orderItem);

    }
    private String generateRandomSixDigitId() {
        Random random = new Random();
        int randomNum = random.nextInt(MAX - MIN + 1) + MIN;
        return String.format("%06d", randomNum);
    }
    public List<AdminOrderResponseDTO> GetUserOrderById(String orderId) {
        List<AdminOrderResponseDTO> responseList = new ArrayList<>();

        List<OrderItem> items = orderItemRepo.findByOrderId(orderId);

        for (OrderItem orderItem : items) {
            Order order = orderItem.getOrder(); // Adjust based on your User entity
            User user = userRepository.findById(order.getUser().getId());

            AdminOrderResponseDTO dto = new AdminOrderResponseDTO(
                    orderItem.getAddress(),
                    orderItem.getBookingDate(), // Booking date
                    orderItem.getDeliveryDate(),
                    orderItem.getPaymentStatus(),
                    orderItem.getPrice(),
                    orderItem.getQuantity(),
                    orderItem.getStatus(),
                    orderItem.getTotalPrice(),
                    user.getName(),
                    orderItem.getProduct().getName()
            );


            responseList.add(dto);

        }

        return responseList;

    }
    public List<AdminOrderResponseDTO> getOrdersByUserName(String username) {
        List<AdminOrderResponseDTO> responseList = new ArrayList<>();

        List<User> users = userRepository.findByName(username);


        for (User user : users) {
            entityManager.refresh(user);
            Order order = orderRepo.findByUserId(user.getId()).orElse(null);
            System.out.println(user.getId());
            if (order == null) continue;

            List<OrderItem> items = orderItemRepo.findByOrderId(order.getId());

            for (OrderItem item : items) {
                AdminOrderResponseDTO dto = new AdminOrderResponseDTO(
                        item.getAddress(),
                        item.getBookingDate(),
                        item.getDeliveryDate(),
                        item.getPaymentStatus(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getStatus(),
                        item.getTotalPrice(),
                        user.getName(),
                        item.getProduct().getName()
                );
                responseList.add(dto);
            }
        }
        return responseList;
    }

}

