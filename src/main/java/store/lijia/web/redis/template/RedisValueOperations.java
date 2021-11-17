package store.lijia.web.redis.template;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/12 上午10:47
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
@AutoConfigureAfter(RedisBaseTemplate.class)
public class RedisValueOperations<K,V>{
    protected ValueOperations<K,V> valueOperations;

    public RedisValueOperations(RedisBaseTemplate<K,V> redisBaseTemplate){
        this.valueOperations=redisBaseTemplate.opsForValue();
    }

    /**
     * Set {@code value} for {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @see <a href="http://redis.io/commands/set">Redis Documentation: SET</a>
     */
    public void set(K key, V value){
        valueOperations.set(key,value);
    }

    /**
     * Set the {@code value} and expiration {@code timeout} for {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @param timeout
     * @param unit must not be {@literal null}.
     * @see <a href="http://redis.io/commands/setex">Redis Documentation: SETEX</a>
     */
    public void set(K key, V value, long timeout, TimeUnit unit){
        valueOperations.set(key,value,timeout,unit);
    }

    /**
     * Set {@code key} to hold the string {@code value} if {@code key} is absent.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/setnx">Redis Documentation: SETNX</a>
     */

    public Boolean setIfAbsent(K key, V value){
        return valueOperations.setIfAbsent(key,value);
    }

    /**
     * Set multiple keys to multiple values using key-value pairs provided in {@code tuple}.
     *
     * @param map must not be {@literal null}.
     * @see <a href="http://redis.io/commands/mset">Redis Documentation: MSET</a>
     */
    public void multiSet(Map<? extends K, ? extends V> map){
        valueOperations.multiSet(map);
    }

    /**
     * Set multiple keys to multiple values using key-value pairs provided in {@code tuple} only if the provided key does
     * not exist.
     *
     * @param map must not be {@literal null}.
     * @param {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/mset">Redis Documentation: MSET</a>
     */
    @Nullable
    public Boolean multiSetIfAbsent(Map<? extends K, ? extends V> map){
        return valueOperations.multiSetIfAbsent(map);
    }

    /**
     * Get the value of {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/get">Redis Documentation: GET</a>
     */
    @Nullable
    public V get(K key){
        return  valueOperations.get(key);
    }

    /**
     * Set {@code value} of {@code key} and return its old value.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/getset">Redis Documentation: GETSET</a>
     */
    @Nullable
    public V getAndSet(K key, V value){
        return valueOperations.getAndSet(key,value);
    }

    /**
     * Get multiple {@code keys}. Values are returned in the order of the requested keys.
     *
     * @param keys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/mget">Redis Documentation: MGET</a>
     */
    @Nullable
    public List<V> multiGet(Collection<K> keys){
        return  valueOperations.multiGet(keys);
    }

    /**
     * Increment an integer value stored as string value under {@code key} by {@code delta}.
     *
     * @param key must not be {@literal null}.
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/incr">Redis Documentation: INCR</a>
     */
    @Nullable
    public Long increment(K key, long delta){
        return valueOperations.increment(key,delta);
    }

    /**
     * Increment a floating point number value stored as string value under {@code key} by {@code delta}.
     *
     * @param key must not be {@literal null}.
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/incrbyfloat">Redis Documentation: INCRBYFLOAT</a>
     */
    @Nullable
    public Double increment(K key, double delta){
        return valueOperations.increment(key,delta);
    }

    /**
     * Append a {@code value} to {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/append">Redis Documentation: APPEND</a>
     */
    @Nullable
    public Integer append(K key, String value){
        return valueOperations.append(key,value);
    }

    /**
     * Get a substring of value of {@code key} between {@code begin} and {@code end}.
     *
     * @param key must not be {@literal null}.
     * @param start
     * @param end
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/getrange">Redis Documentation: GETRANGE</a>
     */
    @Nullable
    public String get(K key, long start, long end){
        return valueOperations.get(key,start,end);
    }

    /**
     * Overwrite parts of {@code key} starting at the specified {@code offset} with given {@code value}.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @param offset
     * @see <a href="http://redis.io/commands/setrange">Redis Documentation: SETRANGE</a>
     */
    public void set(K key, V value, long offset){
        valueOperations.set(key,value,offset);
    }

    /**
     * Get the length of the value stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/strlen">Redis Documentation: STRLEN</a>
     */
    @Nullable
    public Long size(K key){
        return  valueOperations.size(key);
    }

    /**
     * Sets the bit at {@code offset} in value stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param offset
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @since 1.5
     * @see <a href="http://redis.io/commands/setbit">Redis Documentation: SETBIT</a>
     */
    @Nullable
    public Boolean setBit(K key, long offset, boolean value){
        return  valueOperations.setBit(key,offset,value);
    }

    /**
     * Get the bit value at {@code offset} of value at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param offset
     * @return {@literal null} when used in pipeline / transaction.
     * @since 1.5
     * @see <a href="http://redis.io/commands/setbit">Redis Documentation: GETBIT</a>
     */
    @Nullable
    public Boolean getBit(K key, long offset){
        return  valueOperations.getBit(key,offset);
    }

}
