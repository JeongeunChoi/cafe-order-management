package com.example.jecoffee.customer.repository;

import com.example.jecoffee.customer.entity.Customer;
import com.example.jecoffee.customer.entity.Email;
import com.example.jecoffee.global.exception.DataException;
import com.example.jecoffee.global.exception.DataExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.example.jecoffee.global.JdbcUtils.toUUID;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Customer insert(Customer customer) {
        int update = jdbcTemplate.update("INSERT INTO customers(customer_id, email)" +
                " VALUES(UUID_TO_BIN(:customerId), :email)", toParamMap(customer));
        if (update != 1) {
            logger.error(DataExceptionCode.FAIL_TO_INSERT.getMessage());
            throw new DataException(DataExceptionCode.FAIL_TO_INSERT);
        }
        return customer;
    }

    @Override
    public Optional<Customer> findByEmail(Email email) {
        List<Customer> customers = jdbcTemplate.query(
                "SELECT * FROM customers WHERE email = :email",
                Collections.singletonMap("email", email.getAddress()),
                customerRowMapper
        );

        return customers.isEmpty() ? Optional.empty() : Optional.of(customers.get(0));
    }

    private static Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customer.getCustomerId().toString().getBytes());
        paramMap.put("email", customer.getEmail().getAddress());

        return paramMap;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String email = resultSet.getString("email");

        return Customer.createWithCustomerIdAndEmail(customerId, new Email(email));
    };
}
