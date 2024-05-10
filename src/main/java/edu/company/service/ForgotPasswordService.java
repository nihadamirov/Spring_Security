package edu.company.service;

import edu.company.dto.ChangePassword;
import edu.company.dto.MailBody;
import edu.company.entities.ForgotPassword;
import edu.company.model.User;
import edu.company.repository.ForgotPasswordRepository;
import edu.company.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
public class ForgotPasswordService {

        private final UserRepository userRepository;
        private final ForgotPasswordRepository forgotPasswordRepository;
        private final EmailService emailService;
        private final PasswordEncoder passwordEncoder;

        public ForgotPasswordService(UserRepository userRepository, ForgotPasswordRepository forgotPasswordRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.forgotPasswordRepository = forgotPasswordRepository;
            this.emailService = emailService;
            this.passwordEncoder = passwordEncoder;
        }

        public User verifyEmail(String email) throws Exception {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email"));

            String otp = otpGenerator();
            MailBody mailBody = MailBody.builder()
                    .to(email)
                    .text("This is the OTP for your Forgot Password request : " + otp)
                    .subject("OTP for forgot Password request")
                    .build();

            ForgotPassword fp = ForgotPassword.builder()
                    .otp(Integer.valueOf(otp))
                    .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                    .user(user)
                    .build();

            emailService.sendSimpleMessage(mailBody);
            forgotPasswordRepository.save(fp);

            return user;
        }

        public ForgotPassword verifyOtp(String otp, String email) throws Exception {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email"));

            ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(Integer.valueOf(otp), user)
                    .orElseThrow(() -> new RuntimeException("Invalid OTP for email: " + email));

            if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
                forgotPasswordRepository.deleteById(fp.getForgotPasswordId());
                throw new RuntimeException("OTP has expired!");
            }

            return fp;
        }

        public void changePassword(ChangePassword changePassword, String email) {
            if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
                throw new RuntimeException("Please enter the password again!");
            }

            String encodedPassword = passwordEncoder.encode(changePassword.password());
            userRepository.updatePassword(email, encodedPassword);
        }

        private String otpGenerator() {
            Random rand = new Random();
            return String.valueOf(rand.nextInt(100_000, 999_999));
        }

}
