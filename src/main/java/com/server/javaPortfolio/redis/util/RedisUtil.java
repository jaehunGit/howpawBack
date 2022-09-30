package com.server.javaPortfolio.redis.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisUtil {
    private final StringRedisTemplate stringRedisTemplate;

    public String getData(String key){          // 키에 존재하는 벨류 가져오기
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setData(String key, String value){      // 키 벨류 저장
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    public void setDataExpire(String key,String value,long duration){       // 키 벨류 유효시간 저장
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key,value,expireDuration);
    }

    public void deleteData(String key){         // 키 삭제
        stringRedisTemplate.delete(key);
    }

    public boolean hasKey(String key) {  // 키가 존재하는지

        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }
}