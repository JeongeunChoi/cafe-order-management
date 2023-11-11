package com.example.jecoffee.order.repository;

import com.example.jecoffee.customer.entity.Email;
import com.example.jecoffee.global.exception.DataException;
import com.example.jecoffee.global.exception.DataExceptionCode;
import com.example.jecoffee.order.entity.Order;
import com.example.jecoffee.order.entity.OrderItem;
import com.example.jecoffee.order.entity.OrderStatus;
import com.example.jecoffee.product.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.jecoffee.global.JdbcUtils.toUUID;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(OrderJdbcRepository.class);

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Order insert(Order order) {
        jdbcTemplate.update("INSERT INTO orders(order_id, email, address, postcode, order_status, created_at, updated_at) " +
                        "VALUES (UUID_TO_BIN(:orderId), :email, :address, :postcode, :orderStatus, :createdAt, :updatedAt)",
                toOrderParamMap(order));
        order.getOrderItems()
                .forEach(item ->
                        jdbcTemplate.update("INSERT INTO order_items(order_id, product_id, category, price, quantity, created_at, updated_at) " +
                                        "VALUES (UUID_TO_BIN(:orderId), UUID_TO_BIN(:productId), :category, :price, :quantity, :createdAt, :updatedAt)",
                                toOrderItemParamMap(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt(), item)));
        return order;
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query("select * from orders", new OrderRowMapper(jdbcTemplate));
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM orders WHERE order_id = UUID_TO_BIN(:orderId)",
                        Collections.singletonMap("orderId", orderId.toString().getBytes()), new OrderRowMapper(jdbcTemplate))
        );
    }

    @Override
    public void updateOrderStatus(UUID orderId, OrderStatus status) {
        int update = jdbcTemplate.update(
                "UPDATE orders SET order_status = :orderStatus, updated_at = :updatedAt " +
                        "WHERE order_id = UUID_TO_BIN(:orderId)"
                , new HashMap<>() {{
                    put("orderId", orderId.toString().getBytes());
                    put("orderStatus", status.toString());
                    put("updatedAt", LocalDateTime.now());
                }}
        );
        if (update != 1) {
            logger.error(DataExceptionCode.FAIL_TO_UPDATE.getMessage());
            throw new DataException(DataExceptionCode.FAIL_TO_UPDATE);
        }
    }

    private Map<String, Object> toOrderParamMap(Order order) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderId", order.getOrderId().toString().getBytes());
        paramMap.put("email", order.getEmail().getAddress());
        paramMap.put("address", order.getAddress());
        paramMap.put("postcode", order.getPostcode());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("createdAt", order.getCreatedAt());
        paramMap.put("updatedAt", order.getUpdatedAt());
        return paramMap;
    }

    private Map<String, Object> toOrderItemParamMap(UUID orderId, LocalDateTime createdAt, LocalDateTime updatedAt, OrderItem item) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderId", orderId.toString().getBytes());
        paramMap.put("productId", item.getProductId().toString().getBytes());
        paramMap.put("category", item.getCategory().toString());
        paramMap.put("price", item.getPrice());
        paramMap.put("quantity", item.getQuantity());
        paramMap.put("createdAt", createdAt);
        paramMap.put("updatedAt", updatedAt);
        return paramMap;
    }

    private static final RowMapper<OrderItem> orderItemRowMapper = (resultSet, i) -> {
        UUID orderId = toUUID(resultSet.getBytes("order_id"));
        UUID productId = toUUID(resultSet.getBytes("product_id"));
        Category category = Category.valueOf(resultSet.getString("category"));
        long price = resultSet.getLong("price");
        int quantity = resultSet.getInt("quantity");

        return new OrderItem(orderId, productId, category, price, quantity);
    };

    private static class OrderRowMapper implements RowMapper<Order> {
        private final NamedParameterJdbcTemplate jdbcTemplate;

        private OrderRowMapper(NamedParameterJdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UUID orderId = toUUID(resultSet.getBytes("order_id"));
            Email email = new Email(resultSet.getString("email"));
            String address = resultSet.getString("address");
            String postcode = resultSet.getString("postcode");
            List<OrderItem> orderItems = getOrderItemsForOrder(orderId);
            OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();

            return new Order(orderId, email, address, postcode, orderItems, orderStatus, createdAt, updatedAt);
        }

        public List<OrderItem> getOrderItemsForOrder(UUID orderId) {
            String sql = "SELECT * FROM order_items WHERE order_id = UUID_TO_BIN(:orderId)";
            return jdbcTemplate.query(sql,
                    Collections.singletonMap("orderId", orderId.toString().getBytes()), orderItemRowMapper);
        }
    }
}
