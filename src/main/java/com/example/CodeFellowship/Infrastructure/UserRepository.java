package com.example.CodeFellowship.Infrastructure;

import com.example.CodeFellowship.domain.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ApplicationUser,Long> {

    public ApplicationUser findByUsername(String userName);

}
