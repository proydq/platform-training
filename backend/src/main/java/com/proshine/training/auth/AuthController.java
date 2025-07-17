package com.proshine.training.auth;

import com.proshine.training.common.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        LoginResponse resp = authService.login(request.getUsername(), request.getPassword());
        if (resp != null) {
            return new ResponseEntity<>(200, "success", resp);
        }
        return new ResponseEntity<>(500, "fail", null);
    }
}

