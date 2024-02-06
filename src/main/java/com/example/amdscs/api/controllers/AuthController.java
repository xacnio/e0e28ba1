package com.example.amdscs.api.controllers;


import com.example.amdscs.dto.requests.LoginRequest;
import com.example.amdscs.dto.responses.ErrorRes;
import com.example.amdscs.dto.responses.LoginResponse;
import com.example.amdscs.exceptions.ApiError;
import com.example.amdscs.models.User;
import com.example.amdscs.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private JwtUtils jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    @Operation(summary = "Login with email & password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful created authorization token", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid username or password", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))
            })
    })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password));
            String email = authentication.getName();
            User user = new User(email,"");
            List<String> roles = new ArrayList<>();
            for(var authority : authentication.getAuthorities()) {
                roles.add(authority.getAuthority());
            }
            String token = jwtUtil.createToken(user, roles);
            LoginResponse loginRes = new LoginResponse(token);

            return ResponseEntity.ok(loginRes);
        } catch (BadCredentialsException e){
            throw new ApiError(HttpStatus.BAD_REQUEST, "Invalid username or password");
        } catch (Exception e){
            throw new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
