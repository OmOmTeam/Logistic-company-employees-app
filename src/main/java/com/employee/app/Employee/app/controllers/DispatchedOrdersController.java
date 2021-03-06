package com.employee.app.Employee.app.controllers;

import com.employee.app.Employee.app.model.DispatchedOrder;
import com.employee.app.Employee.app.model.Order;
import com.employee.app.Employee.app.service.DataClusterCommunication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@PreAuthorize("hasRole('ROLE_ControlOperator')")
@Controller
public class DispatchedOrdersController {
    private final DataClusterCommunication ordersService;

    @Autowired
    public DispatchedOrdersController(DataClusterCommunication ordersService) {
        this.ordersService = ordersService;
    }


    @GetMapping("/dispatched_orders")
    public String getDispatchedOrders(Model dispathedOrders) throws IOException {
        List<DispatchedOrder> orders = ordersService.geDispatchedOrders();
        dispathedOrders.addAttribute("orders", orders);
        return "thymeleaf/orderList";
    }


    @GetMapping(value = "/dispatched_orders", params = {"id"})
    public String getOrderDetails(Model order, @RequestParam(value = "id") String id) throws IOException {
        DispatchedOrder dispatchedOrder =  ordersService.geDispatchedOrder(id);
        order.addAttribute("order", dispatchedOrder);
        if(dispatchedOrder.getOrderStatus().equals("Validated")
                || dispatchedOrder.getOrderStatus().equals("Delivering")) {
            return "thymeleaf/dispatchedOrderDetails";
        } else if(dispatchedOrder.getOrderStatus().equals("Waiting validation")) {
            return "thymeleaf/openDispatchedOrder";
        } else if(dispatchedOrder.getOrderStatus().equals("Delivered")) {
            return "thymeleaf/finalDispatchedOrder";
        } else if(dispatchedOrder.getOrderStatus().equals("OnValidating")) {
            return "thymeleaf/openDispatchedOrder";
        } else {
            return "thymeleaf/error";
        }
    }

    @PostMapping(value = "/dispatched_orders")
    public String submitOrderDetails(@ModelAttribute Order order ,
                                     @RequestParam(value="action", required=true) String action) throws IOException {
        if(action.equals("Submit")) {

            try {
                DataClusterCommunication.approveOrder(order);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/dispatched_orders";
    }
}
