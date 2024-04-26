package edu.company.controller;

import edu.company.dto.MailBody;
import edu.company.entities.ForgotPassword;
import edu.company.model.User;
import edu.company.repository.ForgotPasswordRepository;
import edu.company.repository.UserRepository;
import edu.company.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;


@RestController
@RequestMapping("forgetPassword")
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;

    public ForgotPasswordController(UserRepository userRepository, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    //for mail for email verification

    @PostMapping("/verifyMail{email}")
    public ResponseEntity<String> verifyMail(@PathVariable String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email"));

        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your Forgot Password request : " + otp)
                .subject("OTP for forgot Password request")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                .user(user)
                .build();

        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fp);

        return ResponseEntity.ok("Email sent for verification!");
    }

    private Integer otpGenerator() {
        Random rand = new Random();
        return rand.nextInt(100_000, 999_999);
    }

}
