package com.evertix.tutofastapi.repository;


import com.evertix.tutofastapi.model.Role;
import com.evertix.tutofastapi.model.User;
import com.evertix.tutofastapi.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username,String email);
    List<User> getAllByRole(Role role);



    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    /*Page<User> findAllByRoles(Set<Role> roles, Pageable pageable);

     */
    @Query(value = "select table1.* from users table1 inner join user_roles table2 on table1.id=table2.user_id inner join roles table3 on table2.role_id=table3.id where table3.name=?1"
            ,nativeQuery = true)
    List<User> getAllUserByRole(String role);
}
