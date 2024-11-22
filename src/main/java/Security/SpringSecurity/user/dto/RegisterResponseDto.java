package Security.SpringSecurity.user.dto;

import Security.SpringSecurity.user.enums.UserRole;

public record RegisterResponseDto(String username, String password, UserRole role) {
}
