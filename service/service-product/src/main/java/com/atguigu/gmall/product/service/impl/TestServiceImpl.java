package com.atguigu.gmall.product.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.atguigu.gmall.product.service.TestService;
import jodd.util.StringUtil;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Chengxj
 * @create 2020-03-30 11:37
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;
    //数据累加
    @Override
    public synchronized void testLock(){

        //testRedis();
        //创建锁
        RLock lock = redissonClient.getLock("lock");
        //加锁
        lock.lock();
        //业务
        // 查询redis中的num值
        String value = stringRedisTemplate.opsForValue().get("num");
        // 没有该值return
        if (StringUtils.isBlank(value)) {
            return;
        }
        // 有值就转成成int
        int num = Integer.parseInt(value);
        // 把redis中的num值+1
        stringRedisTemplate.opsForValue().set("num", String.valueOf(++num));
        //解锁
        lock.unlock();

    }

    private void testRedis() {
        //设置一个uuid
        String uuid = UUID.randomUUID().toString();

        //加锁：setnx del
        //在执行set的时候，设置过期时间，这样就保证了命令的原子性
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid,2, TimeUnit.SECONDS);
        //没有锁，需要加锁
        if (lock){
            //查询redis中的num值
            String value = stringRedisTemplate.opsForValue().get("num");
            //判断value是否有数据
            if (StringUtil.isBlank(value)){
                //如果value是null
                return;
            }
            //有值就把value转化成int
            int num = Integer.parseInt(value);
            //把redis中的num +1
            stringRedisTemplate.opsForValue().set("num",String.valueOf(++num));
            // 声明script--lua 脚本
            /*
            if redis.call("get",KEYS[1]) == ARGV[1]
            then
                return redis.call("del",KEYS[1])
            else
                return 0
            end
             */
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            // 设置lua脚本返回的数据类型
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
            // 设置lua脚本返回类型为Long
            redisScript.setResultType(Long.class);
            redisScript.setScriptText(script);
            stringRedisTemplate.execute(redisScript, Arrays.asList("lock"),uuid);

            /*if (uuid.equals(stringRedisTemplate.opsForValue().get("lock"))){
                //打开锁  是谁的锁，谁删
                stringRedisTemplate.delete("lock");
            }*/

        }else {
            //其它线程需要等待
            try {
                Thread.sleep(1000);
                testLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
