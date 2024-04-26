package edu.company.repository;

import edu.company.entities.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
}
