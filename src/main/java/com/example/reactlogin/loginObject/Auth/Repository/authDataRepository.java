package com.example.reactlogin.loginObject.Auth.Repository;

import com.example.reactlogin.loginObject.Auth.Entity.authData;
import com.example.reactlogin.loginObject.User.Entity.userData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface authDataRepository extends JpaRepository<authData,Long> {}