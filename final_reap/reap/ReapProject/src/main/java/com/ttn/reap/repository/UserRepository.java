package com.ttn.reap.repository;

import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAndPassword(String email, String password);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByToken(String token);

    User findById(Long id);

    List<User> findAllByIsActiveTrueAndIdIsNot(Long id);

    List<User> findAllByIdIsNot(Long id);

}
