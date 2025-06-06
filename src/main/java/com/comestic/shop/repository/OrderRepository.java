package com.comestic.shop.repository;

import com.comestic.shop.dto.BranchRevenueDTO;
import com.comestic.shop.model.Order;
import com.comestic.shop.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findByBranch_BranchIdAndStatusAndOrderCodeContainingAndOrderDateBetween(Long branchId, OrderStatus status, String orderCode, Date startDate, Date endDate, Pageable pageable);
    Page<Order> findByBranch_BranchIdAndStatusAndOrderDateBetween(Long branchId, OrderStatus status, Date startDate, Date endDate, Pageable pageable);
    Page<Order> findByBranch_BranchIdAndOrderCodeContainingAndOrderDateBetween(Long branchId, String orderCode, Date startDate, Date endDate, Pageable pageable);
    Page<Order> findByStatusAndOrderCodeContainingAndOrderDateBetween(OrderStatus status, String orderCode, Date startDate, Date endDate, Pageable pageable);
    Page<Order> findByBranch_BranchIdAndOrderDateBetween(Long branchId, Date startDate, Date endDate, Pageable pageable);
    Page<Order> findByStatusAndOrderDateBetween(OrderStatus status, Date startDate, Date endDate, Pageable pageable);
    Page<Order> findByOrderCodeContainingAndOrderDateBetween(String orderCode, Date startDate, Date endDate, Pageable pageable);
    Page<Order> findByOrderDateBetween(Date startDate, Date endDate, Pageable pageable);

    // Các phương thức cũ đã có:
    Page<Order> findByBranch_BranchIdAndStatusAndOrderCodeContaining(Long branchId, OrderStatus status, String orderCode, Pageable pageable);
    Page<Order> findByBranch_BranchIdAndStatus(Long branchId, OrderStatus status, Pageable pageable);
    Page<Order> findByBranch_BranchIdAndOrderCodeContaining(Long branchId, String orderCode, Pageable pageable);
    Page<Order> findByStatusAndOrderCodeContaining(OrderStatus status, String orderCode, Pageable pageable);
    Page<Order> findByBranch_BranchId(Long branchId, Pageable pageable);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    Page<Order> findByOrderCodeContaining(String orderCode, Pageable pageable);
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
    // List<Order> findByCustomerId(int customerId);
    Order findByOrderCode(String orderCode);

    List<Order> findByBranch_BranchId(Long branchId);



    // Kết hợp các bộ lọc

    @Query("SELECT o FROM Order o ORDER BY o.orderDate DESC")
    Page<Order> findAll(Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.customer.customerID = :customerID ORDER BY o.orderDate DESC")
    Page<Order> findByCustomer_CustomerID(int customerID, Pageable pageable);


    //
    @Query("SELECT new com.comestic.shop.dto.BranchRevenueDTO(o.branch.branchName, SUM(o.totalAmount)) "
            + "FROM Order o "
            + "WHERE o.status = :status "
            + "GROUP BY o.branch.branchName "
            + "ORDER BY SUM(o.totalAmount) DESC")
    List<BranchRevenueDTO> findBranchRevenueByStatus(@Param("status") OrderStatus status);


    // Thêm phương thức mới
    @Query("SELECT new com.comestic.shop.dto.BranchRevenueDTO(o.branch.branchName, SUM(o.totalAmount)) "
            + "FROM Order o "
            + "WHERE o.status = :status AND o.orderDate BETWEEN :startDate AND :endDate "
            + "GROUP BY o.branch.branchName "
            + "ORDER BY SUM(o.totalAmount) DESC")
    List<BranchRevenueDTO> findBranchRevenueByStatusAndDates(@Param("status") OrderStatus status,
                                                             @Param("startDate") Date startDate,
                                                             @Param("endDate") Date endDate);
}
