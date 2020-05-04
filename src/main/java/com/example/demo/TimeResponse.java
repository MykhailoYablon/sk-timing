package com.example.demo;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeResponse {
    private List<String> times;
}
