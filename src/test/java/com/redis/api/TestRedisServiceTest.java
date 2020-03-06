package com.redis.api;

import com.redis.api.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisServiceTest {

    @Autowired
    private TestService testService;

    @Test
    public void testRedisService(){
        testService.testRedisService();
    }

    @Test
    public void testRedisLockService() throws InterruptedException {
        testService.testRedisLockService();
    }

    @Test
    public void testRedisLockServiceTwo() throws InterruptedException {
        testService.testRedisLockService();
    }

}
