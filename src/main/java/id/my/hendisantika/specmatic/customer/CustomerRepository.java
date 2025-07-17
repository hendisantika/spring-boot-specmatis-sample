package id.my.hendisantika.specmatic.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-specmatic
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 17/07/25
 * Time: 05.52
 * To change this template use File | Settings | File Templates.
 * Repository interface for Customer entity.
 * Provides CRUD operations for the Customer entity.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find a customer by email.
     *
     * @param email the email to search for
     * @return an Optional containing the customer if found, or empty if not found
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Check if a customer exists with the given email.
     *
     * @param email the email to check
     * @return true if a customer exists with the email, false otherwise
     */
    boolean existsByEmail(String email);
}