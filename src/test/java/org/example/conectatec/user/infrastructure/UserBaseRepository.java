package org.example.conectatec.user.infrastructure;

import jakarta.transaction.Transactional;
import org.example.conectatec.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Transactional
@Repository
public interface UserBaseRepository <T extends User> extends JpaRepository<T, Long> {

    Optional<T> findByEmail(String email);
}
