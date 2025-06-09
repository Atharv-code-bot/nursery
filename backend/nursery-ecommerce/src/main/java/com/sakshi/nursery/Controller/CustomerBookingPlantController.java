package com.sakshi.nursery.Controller;
import com.sakshi.nursery.DTO.AdminOrderResponseDTO;
import com.sakshi.nursery.DTO.BookingDTO;
import com.sakshi.nursery.DTO.CustomerOrderResponseDTO;
import com.sakshi.nursery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/Customer/orders")
//@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerBookingPlantController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/bookOrder")  //BookOrder
    public ResponseEntity<CustomerOrderResponseDTO> placeOrder(@RequestParam long id, @RequestParam long productID, @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(orderService.CustomerOrderPlant(id, productID,bookingDTO));
    }

    @GetMapping("/customerAllBooking")      //CustomerAllBooking
    public List<CustomerOrderResponseDTO> getUserOrdersById(@RequestParam long userId) {
        return orderService.getOrdersByUserID(userId);
    }


}

