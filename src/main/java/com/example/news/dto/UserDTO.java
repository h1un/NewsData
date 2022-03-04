package com.example.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private Long userIdx;
    private String userId;
    private String userPassword;
    private String userName;
    private LocalDateTime createDate;
}
