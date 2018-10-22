package com.employee.app.Employee.app.service;


import com.employee.app.Employee.app.model.*;
import com.employee.app.Employee.app.service.helpers.RetrofitHelper;
import com.employee.app.Employee.app.service.interfaces.GetEmployeesList;
import com.employee.app.Employee.app.service.interfaces.OrdersToApproveRequest;
import com.employee.app.Employee.app.service.interfaces.UserByLoginRequest;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class DataClusterCommunication {

    /**
     * connects to the data cluster and gets information about user by login
     * @param login user's login
     * @return LoginResponse with information about the user
     * @throws IOException sometimes(((
     */
    public static LoginResponse userByLogin(String login) throws IOException {
        Call<UserByLoginRequest.UserInfo> call = RetrofitHelper.userByLogin(login);
        Response<UserByLoginRequest.UserInfo> response = call.execute();

        LoginResponse result = new LoginResponse(login, response.body().getPassword(),
                response.body().getRole(), response.body().getError());
        return result;
    }

    /**
     * returns all orders that must be approved
     * @return
     * @throws IOException
     */
    public static List<Order> getOrderToApprove() throws IOException {
        Call<OrdersToApproveRequest.OrdersToApproveResponse> call = RetrofitHelper.ordersToApprove();

        Response<OrdersToApproveRequest.OrdersToApproveResponse> response =
                call.execute();
        return response.body().getOrders();
    }

    /**
     * API request to data cluster for apporving some order
     * @param order to approve
     * @return
     * @throws IOException
     */
    public static RequestError approveOrder(Order order) throws IOException {
        Call<RequestError> call = RetrofitHelper.approveOrder(order);

        Response<RequestError> response =
                call.execute();
        return response.body();
    }

    /**
     * API request to data cluster for adding new employee into the database
     * @param employee to add to database
     * @return
     * @throws IOException
     */
    public static RequestError registerNewEmployee(Employee employee) throws IOException {
        Call<RequestError> call = RetrofitHelper.registerNewEmployee(employee);

        Response<RequestError> response =
                call.execute();
        return response.body();
    }

    /**
     * API request to data cluster for deleting user from the system
     * @param login of the user to delete
     * @return
     * @throws IOException
     */
    public static RequestError deleteEmployee(String login) throws IOException {
        Call<RequestError> call = RetrofitHelper.deleteEmployee(login);

        Response<RequestError> response =
                call.execute();
        return response.body();
    }

    /**
     * API request to data cluster for getting list of all employees in the system
     * @return object with error or list of employees
     * @throws IOException
     */
    public static GetEmployeesList.EmployeesList getEmployeesList() throws IOException {
        Call<GetEmployeesList.EmployeesList> call = RetrofitHelper.getEmployeesList();

        Response<GetEmployeesList.EmployeesList> response =
                call.execute();
        return response.body();
    }

    /**
     * API request to data cluster for changing access right of the employee
     * gets login of a user and new role of the user
     * @return
     * @throws IOException
     */
    public static RequestError changeEmployeesAccessRights(String login, String role) throws IOException {
        Call<RequestError> call = RetrofitHelper.changeEmployeesAccessRights(login, role);

        Response<RequestError> response =
                call.execute();
        return response.body();
    }

    public List<DispatchedOrder> geDispatchedOrders(){
        List<DispatchedOrder> a = new ArrayList<>();
        DispatchedOrder f = new DispatchedOrder();
        f.setId(1);
        f.setOrderStatus("dfss");
        OrdersToApproveRequest.Address ad = new OrdersToApproveRequest.Address();
        OrdersToApproveRequest.Sender sender =  new OrdersToApproveRequest.Sender();
        sender.setAddress(ad);
        OrdersToApproveRequest.Receiver receiver =  new OrdersToApproveRequest.Receiver();
        receiver.setAddress(ad);
        f.setSender(sender);
        f.setReceiver(receiver);
        f.setProfitValue(new BigDecimal(1));
        f.setProfitCurrency("USD");
        a.add(f);
        return a;

    }

    public DispatchedOrder geDispatchedOrder(String id){
        DispatchedOrder f = new DispatchedOrder();
        f.setId(1);
        f.setOrderStatus("Waiting validation");
        OrdersToApproveRequest.Address ad = new OrdersToApproveRequest.Address();
        OrdersToApproveRequest.Sender sender =  new OrdersToApproveRequest.Sender();
        sender.setAddress(ad);
        OrdersToApproveRequest.Receiver receiver =  new OrdersToApproveRequest.Receiver();
        receiver.setAddress(ad);
        f.setSender(sender);
        f.setReceiver(receiver);
        f.setProfitValue(new BigDecimal(1));
        f.setProfitCurrency("USD");
        return f;


    }
}