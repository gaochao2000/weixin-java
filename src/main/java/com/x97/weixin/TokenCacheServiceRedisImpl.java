package com.x97.weixin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 微信 Token 缓存 Redis 实现类
 */
public class TokenCacheServiceRedisImpl implements TokenCacheService {
    private static Log log = LogFactory.getLog(WeixinTokenService.class);
    private JedisPool jedisPool;
    private static String accessTokenKey = "ACCESS_TOKEN";
    private static String jsapiTokenKey = "JSAPI_TOKEN";
    private int dbIndex = 1;


    /**
     * 构造函数
     *
     * @param ip   redis 服务器 ip
     * @param port redis 服务器 端口
     * @param db   存储信息的 redis 数据库编号
     */
    public TokenCacheServiceRedisImpl(String ip, Integer port, int db) {
        jedisPool = new JedisPool(new JedisPoolConfig(), ip, port);
        this.dbIndex = db;
    }

    public boolean saveAccessToken(String accessToken, int expiryTime) {
        Jedis jedis = null;
        boolean error = true;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dbIndex);
            jedis.setex(accessTokenKey.getBytes(), expiryTime - 120, accessToken.getBytes());
            error = false;
        } catch (Exception e) {
            log.warn(e);
            return false;
        } finally {
            returnConnection(jedis, error);
        }
        return true;
    }


    public boolean saveJSAPIToken(String jsapiToken, int expiryTime) {
        Jedis jedis = null;
        boolean error = true;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dbIndex);
            jedis.setex(jsapiTokenKey.getBytes(), expiryTime - 120, jsapiToken.getBytes());
            error = false;
        } catch (Exception e) {
            log.warn(e);
            return false;
        } finally {
            returnConnection(jedis, error);
        }
        return true;
    }


    public String getAccessToken() {
        Jedis jedis = null;
        boolean error = true;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dbIndex);
            byte[] result = jedis.get(accessTokenKey.getBytes());
            error = false;
            if (result == null) {
                log.trace("can not find cache with " + accessTokenKey);
            } else {
                log.trace("find cache success with " + accessTokenKey);
                return new String(result);
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            returnConnection(jedis, error);
        }
        return null;
    }


    public String getJSAPIToken() {
        Jedis jedis = null;
        boolean error = true;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dbIndex);
            byte[] result = jedis.get(jsapiTokenKey.getBytes());
            error = false;
            if (result == null) {
                log.trace("can not find caceh with " + jsapiTokenKey);
            } else {
                log.trace("find cache success with " + jsapiTokenKey);
                return new String(result);
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            returnConnection(jedis, error);
        }
        return null;
    }

    private void returnConnection(Jedis jedis, Boolean error) {
        if (error) {
            jedisPool.returnBrokenResource(jedis);
        } else {
            jedisPool.returnResource(jedis);
        }
    }

}
