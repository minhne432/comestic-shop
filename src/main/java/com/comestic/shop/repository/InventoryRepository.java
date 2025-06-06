package com.comestic.shop.repository;

import com.comestic.shop.model.Branch;
import com.comestic.shop.model.Inventory;
import com.comestic.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
    // List<Inventory> findByBranchId(Long branchId);
    Inventory findByBranchAndProduct(Branch branch, Product product);
    List<Inventory> findByProduct(Product product);

    @Query("SELECT SUM(i.quantity) FROM Inventory i WHERE i.product = :product")
    Integer sumQuantityByProduct(Product product);

}
