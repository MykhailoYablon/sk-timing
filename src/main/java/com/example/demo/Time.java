package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Time {

    private String playerName;
    private Integer days;
    private Integer hours;
    private Integer minutes;
    private Integer seconds;
}
