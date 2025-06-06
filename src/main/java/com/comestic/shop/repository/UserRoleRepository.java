package com.comestic.shop.repository;

import com.comestic.shop.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
     List<UserRole> findByCustomer_CustomerID(int customerId);
     List<UserRole> findByRole_RoleID(int roleId);
}
