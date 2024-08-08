package com.springbootacademy.batch7newpos.controller;

import com.springbootacademy.batch7newpos.dto.CustomerDTO;
import com.springbootacademy.batch7newpos.dto.request.CustomerUpdateDTO;
import com.springbootacademy.batch7newpos.service.CustomerService;
import com.springbootacademy.batch7newpos.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public String saveCustomer(@RequestBody CustomerDTO customerDTO){
       // CustomerServiceIMPL customerServiceIMPL = new CustomerServiceIMPL();
       // customerServiceIMPL.saveCustomer(customerDTO);
        customerService.saveCustomer(customerDTO);
        return "Saved!";
    }

    @PutMapping("/update")
    public String updateCustomer(@RequestBody CustomerUpdateDTO customerUpdateDTO){
        String message = customerService.updateCustomer(customerUpdateDTO);
        return message;
    }

    @GetMapping(
            path = "/get-by-id",
            params = "id"
    )
    public CustomerDTO getCustomerById(@RequestParam(value = "id") int customerId){
        System.out.println("print value "+customerId);
        CustomerDTO customerDTO =customerService.getCustomerById(customerId);
        return customerDTO;
    }

//    @GetMapping(
//            path = "/get-all-customers"
//    )
//    public List<CustomerDTO> getAllCustomers(){
//        List<CustomerDTO> allCustomers = customerService.getAllCustomers();
//        return allCustomers;
//    }

    @GetMapping(
            path = "/get-all-customers"
    )
    public ResponseEntity<StandardResponse> getAllCustomers(){
        List<CustomerDTO> allCustomers = customerService.getAllCustomers();
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200,"Success",allCustomers),
                HttpStatus.OK
        );
    }


    @DeleteMapping(
           path = "/delete-customers/{id}"
   )
    public String deleteCustomer(@PathVariable(value = "id") int customerId){
        String deleted = customerService.deleteCustomer(customerId);
        return deleted;
   }

   @GetMapping(
           path = "/get-all-customers-by-active-state/{status}"
   )
    public List<CustomerDTO> getAllCustomersByActiveState(@PathVariable(value = "status") boolean activeState){
        List<CustomerDTO> allCustomers = customerService.getAllCustomersByActiveState(activeState);
        return allCustomers;
   }

}
