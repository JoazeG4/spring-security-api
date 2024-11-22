package Security.SpringSecurity.controller;

import Security.SpringSecurity.config.TokenService;
import Security.SpringSecurity.repository.UserRepository;
import Security.SpringSecurity.user.User;
import Security.SpringSecurity.user.dto.AuthenticationResponseDto;
import Security.SpringSecurity.user.dto.LoginResponseDto;
import Security.SpringSecurity.user.dto.RegisterResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationResponseDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterResponseDto data){
        if (userRepository.findByUsername(data.username()) != null) return ResponseEntity.badRequest().build();
        String encryptPassword = new BCryptPasswordEncoder().encode(data.password());
        userRepository.save(new User(data.username(), encryptPassword, data.role()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable String username){
        if (userRepository.findByUsername(username) == null) return ResponseEntity.notFound().build();
        userRepository.deleteByUsername(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<Object> getUserAll(){
        if (userRepository.findAll().isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUser(@PathVariable String username){
        if (userRepository.findByUsername(username) == null) return ResponseEntity.notFound().build();
        userRepository.deleteByUsername(username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username){
        if (userRepository.findByUsername(username) == null) return ResponseEntity.notFound().build();
        userRepository.deleteByUsername(username);
        return ResponseEntity.ok().build();
    }
}
