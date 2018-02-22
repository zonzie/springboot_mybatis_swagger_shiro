package com.example.demo.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Created by zonzie on 2018/2/22.
 */
@Component
//@Configuration
public class RedisSessionDao extends AbstractSessionDAO {
    // Session超时时间，单位为毫秒
    private long expireTime = 120000;

    private RedisTemplate redisTemplate;

    public RedisSessionDao() {
        super();
    }

    public RedisSessionDao(long expireTime, RedisTemplate redisTemplate) {
        super();
        this.expireTime = expireTime;
        this.redisTemplate = redisTemplate;
    }

    @Override // 更新session
    public void update(Session session) throws UnknownSessionException {
//        System.out.println("===============update================");
        if (session == null || session.getId() == null) {
            return;
        }
        session.setTimeout(expireTime);

        redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override // 删除session
    public void delete(Session session) {
//        System.out.println("===============delete================");
        if (null == session) {
            return;
        }
        redisTemplate.opsForValue().getOperations().delete(session.getId());
    }

        @Override// 获取活跃的session，可以用来统计在线人数，如果要实现这个功能，可以在将session加入redis时指定一个session前缀，统计的时候则使用keys("session-prefix*")的方式来模糊查找redis中所有的session集合
    public Collection<Session> getActiveSessions() {
//        System.out.println("==============getActiveSessions=================");
        return redisTemplate.keys("*");
    }

    @Override// 加入session
    protected Serializable doCreate(Session session) {
//        System.out.println("===============doCreate================");
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);

        redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
        return sessionId;
    }

    @Override// 读取session
    protected Session doReadSession(Serializable sessionId) {
//        System.out.println("==============doReadSession=================");
        if (sessionId == null) {
            return null;
        }
        Object session = redisTemplate.opsForValue().get(sessionId);
        if(session == null) {
            return null;
        } else {
            return (SimpleSession) session;
        }
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
