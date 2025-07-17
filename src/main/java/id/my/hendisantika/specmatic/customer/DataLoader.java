package id.my.hendisantika.specmatic.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-specmatic
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 17/07/25
 * Time: 06.20
 * To change this template use File | Settings | File Templates.
 * Data loader to initialize the database with sample data.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    @Autowired
    public DataLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        // Load sample data only if the database is empty
        if (customerRepository.count() == 0) {
            loadSampleData();
        }
    }

    private void loadSampleData() {
        System.out.println("Loading sample data...");

        Customer customer1 = new Customer("John Doe", "john.doe@example.com", "1234567890");
        Customer customer2 = new Customer("Jane Smith", "jane.smith@example.com", "0987654321");
        Customer customer3 = new Customer("Bob Johnson", "bob.johnson@example.com", "5555555555");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        System.out.println("Sample data loaded successfully!");
    }
}