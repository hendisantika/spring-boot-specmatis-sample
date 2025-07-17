package id.my.hendisantika.specmatic.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-specmatic
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 17/07/25
 * Time: 06.10
 * To change this template use File | Settings | File Templates.
 * Service class for Customer-related operations.
 * Handles business logic and interacts with the CustomerRepository.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Get all customers.
     *
     * @return list of all customers
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Get a customer by ID.
     *
     * @param id the customer ID
     * @return an Optional containing the customer if found, or empty if not found
     */
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    /**
     * Create a new customer.
     *
     * @param customer the customer to create
     * @return the created customer
     * @throws CustomerAlreadyExistsException if a customer with the same email already exists
     */
    public Customer createCustomer(Customer customer) {
        // Check if customer with the same email already exists
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new CustomerAlreadyExistsException("Customer with email " + customer.getEmail() + " already exists");
        }
        return customerRepository.save(customer);
    }

    /**
     * Update an existing customer.
     *
     * @param id              the ID of the customer to update
     * @param customerDetails the updated customer details
     * @return the updated customer
     * @throws CustomerNotFoundException      if the customer is not found
     * @throws CustomerAlreadyExistsException if updating would result in duplicate email
     */
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        // Check if email is being changed and if new email already exists
        if (!customer.getEmail().equals(customerDetails.getEmail()) &&
                customerRepository.existsByEmail(customerDetails.getEmail())) {
            throw new CustomerAlreadyExistsException("Customer with email " + customerDetails.getEmail() + " already exists");
        }

        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());

        return customerRepository.save(customer);
    }

    /**
     * Delete a customer by ID.
     *
     * @param id the ID of the customer to delete
     * @throws CustomerNotFoundException if the customer is not found
     */
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}