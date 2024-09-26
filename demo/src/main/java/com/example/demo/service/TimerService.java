package com.example.demo.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("session")
@Service
public class TimerService {
    private long startTime;
    private long endTime;

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public long stopTimer() {
        endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
