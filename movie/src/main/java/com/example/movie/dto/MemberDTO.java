package com.example.movie.dto;

import com.example.movie.entity.MemberRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Long mid;

    @Email(message = "이메일 형식을 확인해 주세요")
    @NotBlank(message = "이메일은 필수 요소입니다")
    private String email;
    @NotBlank(message = "비밀번호는 필수 요소입니다")
    private String password;
    @NotBlank(message = "닉네임은 필수 요소입니다")
    private String nickname;
    private MemberRole memberRole;

}
