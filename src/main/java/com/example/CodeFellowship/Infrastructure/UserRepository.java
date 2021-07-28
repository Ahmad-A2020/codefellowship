package com.example.CodeFellowship.Infrastructure;

import com.example.CodeFellowship.domain.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser,Long> {

    public ApplicationUser findByUsername(String userName);

}
