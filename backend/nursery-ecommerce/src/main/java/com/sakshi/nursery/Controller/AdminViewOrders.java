package com.sakshi.nursery.Controller;
import com.sakshi.nursery.DTO.AdminOrderResponseDTO;
import com.sakshi.nursery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/admin/orders")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminViewOrders {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/OrderByUsername",produces = "application/json")  // Find Order BY UserName
    public List<AdminOrderResponseDTO> getUserOrdersById(@RequestParam String username) {
        return orderService.getOrdersByUserName(username);
    }
    @GetMapping("/AllOrders")
    public ResponseEntity<List<AdminOrderResponseDTO>> getAllOrdersForAdmin() {
        List<AdminOrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/orderByOrderId")
    public ResponseEntity<List<AdminOrderResponseDTO>> GetUserOrderByID(@RequestParam String OrderId) {
        List<AdminOrderResponseDTO> orders = orderService.GetUserOrderById(OrderId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/update")
    public String updateOrdered(@RequestBody AdminOrderResponseDTO adminOrderResponseDTO,@RequestParam int id,@RequestParam  Long productid){
         orderService.updateSingleOrderItemByCustomerNameAndAddress(adminOrderResponseDTO,id,productid);
         return "Updated Succesfully";
    }


}


