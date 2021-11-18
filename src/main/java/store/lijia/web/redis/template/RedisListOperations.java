package store.lijia.web.redis.template;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/12 上午10:47
*
 */
@AutoConfigureAfter(RedisBaseTemplate.class)
public class RedisListOperations<K,V>{

    protected ListOperations<K,V> listOperations;

    public RedisListOperations(RedisBaseTemplate<K,V> redisBaseTemplate){
        this.listOperations=redisBaseTemplate.opsForList();
    }


    /**
     * Get elements between {@code begin} and {@code end} from list at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param start
     * @param end
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/lrange">Redis Documentation: LRANGE</a>
     */
    public List<V> range(K key, long start, long end){
        return listOperations.range(key,start,end);
    }


    /**
     * Trim list at {@code key} to elements between {@code start} and {@code end}.
     *
     * @param key must not be {@literal null}.
     * @param start
     * @param end
     * @see <a href="http://redis.io/commands/ltrim">Redis Documentation: LTRIM</a>
     */
    public void trim(K key, long start, long end){
        listOperations.trim(key,start,end);
    }

    /**
     * Get the size of list stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/llen">Redis Documentation: LLEN</a>
     */
    @Nullable
    public Long size(K key){
        return listOperations.size(key);
    }

    /**
     * Prepend {@code value} to {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     */
    @Nullable
    public Long leftPush(K key, V value){
        return listOperations.leftPush(key,value);
    }

    /**
     * Prepend {@code values} to {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param values
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     */
    @Nullable
    public Long leftPushAll(K key, V... values){
        return  listOperations.leftPushAll(key,values);
    }

    /**
     * Prepend {@code values} to {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param values must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @since 1.5
     * @see <a href="http://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     */
    @Nullable
    public Long leftPushAll(K key, Collection<V> values){
        return listOperations.leftPushAll(key,values);
    }

    /**
     * Prepend {@code values} to {@code key} only if the list exists.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/lpushx">Redis Documentation: LPUSHX</a>
     */
    @Nullable
    public Long leftPushIfPresent(K key, V value){
        return listOperations.leftPushIfPresent(key,value);
    }

    /**
     * Prepend {@code values} to {@code key} before {@code value}.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     */
    @Nullable
    public Long leftPush(K key, V pivot, V value){
        return listOperations.leftPush(key,pivot,value);
    }

    /**
     * Append {@code value} to {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     */
    @Nullable
    public Long rightPush(K key, V value){
        return listOperations.rightPush(key,value);
    }

    /**
     * Append {@code values} to {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param values
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     */
    @Nullable
    public Long rightPushAll(K key, V... values){
        return listOperations.rightPushAll(key,values);
    }

    /**
     * Append {@code values} to {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param values
     * @return {@literal null} when used in pipeline / transaction.
     * @since 1.5
     * @see <a href="http://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     */
    @Nullable
    public Long rightPushAll(K key, Collection<V> values){
        return listOperations.rightPushAll(key,values);
    }

    /**
     * Append {@code values} to {@code key} only if the list exists.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/rpushx">Redis Documentation: RPUSHX</a>
     */
    @Nullable
    public Long rightPushIfPresent(K key, V value){
        return listOperations.rightPushIfPresent(key,value);
    }

    /**
     * Append {@code values} to {@code key} before {@code value}.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/lpush">Redis Documentation: RPUSH</a>
     */
    @Nullable
    public Long rightPush(K key, V pivot, V value){
        return listOperations.rightPush(key,value);
    }

    /**
     * Set the {@code value} list element at {@code index}.
     *
     * @param key must not be {@literal null}.
     * @param index
     * @param value
     * @see <a href="http://redis.io/commands/lset">Redis Documentation: LSET</a>
     */
    public void set(K key, long index, V value){
        listOperations.set(key,index,value);
    }

    /**
     * Removes the first {@code count} occurrences of {@code value} from the list stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param count
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/lrem">Redis Documentation: LREM</a>
     */
    @Nullable
    public Long remove(K key, long count, Object value){
        return listOperations.remove(key,count,value);
    }

    /**
     * Get element at {@code index} form list at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param index
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/lindex">Redis Documentation: LINDEX</a>
     */
    @Nullable
    public V index(K key, long index){
        return listOperations.index(key,index);
    }

    /**
     * Removes and returns first element in list stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="http://redis.io/commands/lpop">Redis Documentation: LPOP</a>
     */
    @Nullable
    public V leftPop(K key){
        return listOperations.leftPop(key);
    }

    /**
     * Removes and returns first element from lists stored at {@code key} . <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param key must not be {@literal null}.
     * @param timeout
     * @param unit must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="http://redis.io/commands/blpop">Redis Documentation: BLPOP</a>
     */
    @Nullable
    public V leftPop(K key, long timeout, TimeUnit unit){
        return listOperations.leftPop(key,timeout,unit);
    }

    /**
     * Removes and returns last element in list stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="http://redis.io/commands/rpop">Redis Documentation: RPOP</a>
     */
    @Nullable
    public V rightPop(K key){
        return listOperations.rightPop(key);
    }

    /**
     * Removes and returns last element from lists stored at {@code key}. <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param key must not be {@literal null}.
     * @param timeout
     * @param unit must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="http://redis.io/commands/brpop">Redis Documentation: BRPOP</a>
     */
    @Nullable
    public V rightPop(K key, long timeout, TimeUnit unit){
        return  listOperations.rightPop(key,timeout,unit);
    }

    /**
     * Remove the last element from list at {@code sourceKey}, append it to {@code destinationKey} and return its value.
     *
     * @param sourceKey must not be {@literal null}.
     * @param destinationKey must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="http://redis.io/commands/rpoplpush">Redis Documentation: RPOPLPUSH</a>
     */
    @Nullable
    public V rightPopAndLeftPush(K sourceKey, K destinationKey){
        return listOperations.rightPopAndLeftPush(sourceKey,destinationKey);
    }

    /**
     * Remove the last element from list at {@code srcKey}, append it to {@code dstKey} and return its value.<br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param sourceKey must not be {@literal null}.
     * @param destinationKey must not be {@literal null}.
     * @param timeout
     * @param unit must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="http://redis.io/commands/brpoplpush">Redis Documentation: BRPOPLPUSH</a>
     */
    @Nullable
    public V rightPopAndLeftPush(K sourceKey, K destinationKey, long timeout, TimeUnit unit){
        return  listOperations.rightPopAndLeftPush(sourceKey,destinationKey,timeout,unit);
    }
}
