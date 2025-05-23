package com.example.accountservices.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class StatisticDTO {
    private Long id;

    @NonNull
    private String message;

    @NonNull
    private Date createdDate;
}
