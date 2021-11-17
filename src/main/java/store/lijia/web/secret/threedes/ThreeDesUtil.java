package store.lijia.web.secret.threedes;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午5:12
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
@Component
@EnableConfigurationProperties(ThreeDesProperties.class)
public class ThreeDesUtil {
    /**
     * 加解密统一使用的编码方式
     */
    private final static String encoding = "UTF-8";


    private static ThreeDesProperties threeDesProperties;

    @Resource
    public void setThreeDesProperties(ThreeDesProperties threeDesProperties) {
        this.threeDesProperties = threeDesProperties;
    }

    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return 加密后的文本，失败返回null
     */
    public static String encode(String plainText, String secretKey, String iv) throws Exception {
        DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(threeDesProperties.getKeyAlgorithm());
        Key desKey = secretKeyFactory.generateSecret(deSedeKeySpec);
        Cipher cipher = Cipher.getInstance(threeDesProperties.getCipherAlgorithm());
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64Utils.encodeToString(encryptData);
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return 解密后明文，失败返回null
     */
    public static String decode(String encryptText, String secretKey, String iv) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(threeDesProperties.getKeyAlgorithm());
        Key desKey = secretKeyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance(threeDesProperties.getCipherAlgorithm());
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, desKey, ips);
        byte[] decryptData = cipher.doFinal(Base64Utils.decodeFromString(encryptText));
        return new String(decryptData, encoding);
    }


}
