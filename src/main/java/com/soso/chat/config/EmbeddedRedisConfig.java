package com.soso.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Redis
 * - data structrue
 * - remote에 위치, 프로세스로 존재, 메모리 기반의, 키-값 구조 데이터 관리 시스템, 비 관계형형 * - String, Set, Sorted Set, Hash, List 자료구조를 지원
 * - pub/sub 지원
 * - 서로 다른 서버에 접속해 있는 클라이언트가 채팅방을 통해 다른 서버의 클라이언트와
 *   메세지를 주고받을 수 있도록 함.
 *
 * 로컬 환경일경우 내장 레디스가 실행된다.
 */
@Profile("local")
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() {
        redisServer = new RedisServer(redisPort);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}