package javacode.server.springelectronriftstatistics.repository;

import javacode.server.springelectronriftstatistics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername (@Param("username") String username);
    Optional<User> findByPassword (@Param("password") String password);
    Optional<User> findByUsernameAndPassword (@Param("username") String username, @Param("password") String password);
    Optional<User> findById (@Param("ID") String uid);
}