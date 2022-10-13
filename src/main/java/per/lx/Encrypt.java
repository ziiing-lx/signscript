package per.lx;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

public class Encrypt {
    private static final String ALGORITHM = "DES";
    private static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
    private static final String CHARSET = "utf-8";

    private Key generateKey(String password) throws Exception {
        DESKeySpec dks = new DESKeySpec(password.getBytes(CHARSET));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(dks);
    }

    public String encrypt(String password, String data) {
        if (password== null || password.length() < 8) {
            throw new RuntimeException("加密失败，key不能小于8位");
        }
        if (data == null) {
            return null;
        }

        try {
            Key secretKey = generateKey(password);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(data.getBytes(CHARSET));
            return Hex.encodeHexString(bytes);

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

}
