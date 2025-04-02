package org.saas.user.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "Kullanıcı adı boş olamaz")
        String username,

        @NotBlank(message = "Email boş olamaz")
        @Email(message = "Geçerli bir email adresi girin")
        String email,

        @NotBlank(message = "Isim boş olamaz")
        String fullName,

        @NotBlank(message = "Şifre boş olamaz")
        @Size(min = 6, message = "Şifre en az 6 karakter olmalı")
        String password,

        Long roleId

) {}