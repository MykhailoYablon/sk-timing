package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/time")
public class TimeController {

    private static final Integer SECONDS_PER_HOUR = 3600;
    private static final Integer SECONDS_PER_MINUTE = 60;

    @PostMapping
    public TimeResponse time(@RequestBody List<Time> timeList) {
        Map<Duration, String> playerMap = new HashMap<>();
        List<Duration> durations = new ArrayList<>();

        for (Time time : timeList) {
            Duration duration = Duration.ofDays(time.getDays())
                    .plusHours(time.getHours())
                    .plusMinutes(time.getMinutes())
                    .plusSeconds(time.getSeconds());
            durations.add(duration);
            playerMap.put(duration, time.getPlayerName());
        }


        durations = durations.stream().sorted().collect(Collectors.toList());

        Duration difference = durations.get(durations.size() - 1).minus(durations.get(0));
        LocalTime exitTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).plus(difference).plusMinutes(15);

        LocalTime reach = exitTime.plus(durations.get(durations.size() - 1));

        int t = 0;
        List<String> times = new ArrayList<>();
        for (int i = 0; i < durations.size(); i++) {
            Duration duration = durations.get(i);

            LocalTime time = reach.minus(duration).minusSeconds(t);
            String attack = "Attack number " + i + ", Exit Time: " + time
                    + ", Час армії: " + getTime(duration) + ", Player: " + playerMap.get(duration);
            System.out.println(attack);
            t += 3;
            times.add(attack);
        }

        return TimeResponse.builder().times(times).build();
    }

    private String getTime(Duration duration) {
        long effectiveTotalSecs = duration.getSeconds();
        long hours = effectiveTotalSecs / SECONDS_PER_HOUR;
        int minutes = (int) ((effectiveTotalSecs % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        int secs = (int) (effectiveTotalSecs % SECONDS_PER_MINUTE);
        return String.valueOf(hours) + ':' +
                minutes + ':' +
                secs;
    }
}
