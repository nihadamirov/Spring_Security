package edu.company.controller;

import edu.company.dto.ChangePassword;
import edu.company.dto.ResponseMessage;
import edu.company.service.ForgotPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@Slf4j
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    //send mail for email verification
    @PostMapping("/verify/{email}")
    public ResponseEntity<ResponseMessage> verifyMail(@PathVariable String email) throws Exception {
        forgotPasswordService.verifyEmail(email);
        ResponseMessage message = new ResponseMessage("Email sent for verification!");
        return ResponseEntity.ok(message);
    }

    @PostMapping("/verify{otp}/{email}")
    public ResponseEntity<ResponseMessage> verifyOtp(@PathVariable String otp, @PathVariable String email) throws Exception {
        forgotPasswordService.verifyOtp(otp, email);
        ResponseMessage message = new ResponseMessage("OTP sent for verification!");
        return ResponseEntity.ok(message);
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<ResponseMessage> changePasswordHandler(@RequestBody ChangePassword changePassword,
                                                                 @PathVariable String email) {

        forgotPasswordService.changePassword(changePassword, email);
        ResponseMessage message = new ResponseMessage("Password changed!");
        return ResponseEntity.ok(message);
    }
}