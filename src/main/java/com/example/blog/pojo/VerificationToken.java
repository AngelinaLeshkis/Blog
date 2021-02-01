package com.example.blog.pojo;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@RedisHash(value = "USER", timeToLive = 86400000L)
public class VerificationToken implements Serializable {

    private Long id;
    private Date timeOfCreation;

    private static final Long expiredTime = 86400000L;

    public VerificationToken() {
    }

    public VerificationToken(Long id, Date timeOfCreation) {
        this.id = id;
        this.timeOfCreation = timeOfCreation;
    }

    public Date getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(Date timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static Long getExpiredTime() {
        return expiredTime;
    }
}
