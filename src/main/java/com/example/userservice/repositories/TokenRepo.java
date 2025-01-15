package com.example.userservice.repositories;

import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    @Override
    Token save(Token token);

    Optional<Token> findByValueAndDeleted(String token,Boolean deleted); // it will search for given token which match (token,deleted)

    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(String token, Boolean deleted, Date expiryAt);
}
