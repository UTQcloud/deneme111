package com.yzmglstm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.yzmglstm.entities.Users;
import java.util.Optional;
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByMail(String mail);
}
