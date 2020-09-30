package com.wjiec.tinder.spring.security.dto;

import com.wjiec.tinder.spring.security.validation.ValidEmail;
import com.wjiec.tinder.spring.security.validation.ValidMatchedPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidMatchedPassword(message = "{registration.confirmPassword}")
public class UserDTO {

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 18, message = "{registration.username}")
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 18, message = "{registration.password}")
    private String password;

    @NotNull
    @NotEmpty
    private String confirmPassword;

    @NotNull
    @NotEmpty
    @ValidEmail(message = "{registration.email}")
    private String email;

}
