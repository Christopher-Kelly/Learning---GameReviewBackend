package com.example.reactlogin.loginObject.User.Repository;

import com.example.reactlogin.loginObject.User.Entity.userData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userDataRepository extends JpaRepository<userData,Long> {
    Optional<userData> findByUsername(String username);}
