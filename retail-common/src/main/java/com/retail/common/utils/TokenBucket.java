package com.retail.common.utils;

import java.util.concurrent.TimeUnit;

public class TokenBucket {
    private int maxTokens; // 桶的容量
    private long lastRefillTimestamp; // 上次填充时间

    private double tokens; // 当前令牌数量
    private double refillRate; // 每秒填充速度

    public TokenBucket(int maxTokens, double refillRate) {
        this.maxTokens = maxTokens;
        this.tokens = maxTokens;
        this.refillRate = refillRate;
        this.lastRefillTimestamp = System.nanoTime();
    }

    // 从桶中取走指定数量的令牌，如果不够则返回false
    public synchronized boolean tryConsume(int tokens) {
        refill();
        if (this.tokens >= tokens) {
            this.tokens -= tokens;
            return true;
        } else {
            return false;
        }
    }

    // 填充令牌桶
    private void refill() {
        long now = System.nanoTime();
        if (now > lastRefillTimestamp) {
            double nanosSinceLastRefill = now - lastRefillTimestamp;
            double newTokens = nanosSinceLastRefill * refillRate / TimeUnit.SECONDS.toNanos(1);
            tokens = Math.min(tokens + newTokens, maxTokens);
            lastRefillTimestamp = now;
        }
    }
}
