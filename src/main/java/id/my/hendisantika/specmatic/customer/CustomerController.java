package id.my.hendisantika.specmatic.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-specmatic
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 17/07/25
 * Time: 05.50
 * To change this template use File | Settings | File Templates.
 * REST controller for Customer-related operations.
 * Handles HTTP requests for CRUD operations on customers.
 */
@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer", description = "Customer management API")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Get all customers.
     *
     * @return list of all customers
     */
    @Operation(summary = "Get all customers", description = "Retrieves a list of all customers in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of customers",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)))
    })
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * Get a customer by ID.
     *
     * @param id the customer ID
     * @return the customer if found
     */
    @Operation(summary = "Get a customer by ID", description = "Retrieves a customer by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @Parameter(description = "ID of the customer to retrieve", required = true)
            @PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
    }

    /**
     * Create a new customer.
     *
     * @param customer the customer to create
     * @return the created customer
     */
    @Operation(summary = "Create a new customer", description = "Creates a new customer in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "400", description = "Customer with the same email already exists",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<Customer> createCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Customer object to be created",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Customer.class)))
            @RequestBody Customer customer) {
        Customer newCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    /**
     * Update an existing customer.
     *
     * @param id              the ID of the customer to update
     * @param customerDetails the updated customer details
     * @return the updated customer
     */
    @Operation(summary = "Update an existing customer", description = "Updates an existing customer's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Customer with the same email already exists",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @Parameter(description = "ID of the customer to update", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated customer object",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Customer.class)))
            @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    /**
     * Delete a customer by ID.
     *
     * @param id the ID of the customer to delete
     * @return no content response
     */
    @Operation(summary = "Delete a customer", description = "Deletes a customer by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID of the customer to delete", required = true)
            @PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Exception handler for CustomerNotFoundException.
     *
     * @param ex the exception
     * @return error response
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Operation(summary = "Handle customer not found", description = "Handles the case when a customer is not found")
    @ApiResponse(responseCode = "404", description = "Customer not found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Map.class)))
    public ResponseEntity<Map<String, String>> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for CustomerAlreadyExistsException.
     *
     * @param ex the exception
     * @return error response
     */
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Operation(summary = "Handle customer already exists", description = "Handles the case when a customer with the same email already exists")
    @ApiResponse(responseCode = "400", description = "Customer with the same email already exists",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Map.class)))
    public ResponseEntity<Map<String, String>> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}