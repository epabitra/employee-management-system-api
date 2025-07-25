package com.nihar.repository;

import com.nihar.dto.DepartmentEmployeeCountDTO;
import com.nihar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.active = true")
    long countActiveEmployees();
    
    @Query("SELECT new com.nihar.dto.DepartmentEmployeeCountDTO(u.department, COUNT(u)) " +
    	       "FROM User u WHERE u.active = true GROUP BY u.department")
    	List<DepartmentEmployeeCountDTO> getEmployeeCountByDepartment();
    
    
    @Query("SELECT u FROM User u WHERE FUNCTION('MONTH', u.dateOfBirth) = :month AND FUNCTION('DAY', u.dateOfBirth) = :day")
    List<User> findUsersWithBirthdayToday(@Param("month") int month, @Param("day") int day);


}
