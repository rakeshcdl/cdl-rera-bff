

package com.cdl.escrow.helper;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AESUtil {

    private static final String AES = "AES";
    private static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128; // in bits
    private static final int IV_LENGTH = 12; // Recommended for GCM
   // private static final int KEY_SIZE = 128; // or 256 if needed

    // You should not hardcode this key — use a key management system or derive it
    private static final byte[] SECRET = "cdl@120813cdl@1".getBytes(); // 16 bytes for 128-bit

    public static String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
        byte[] iv = new byte[IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        SecretKey key = new SecretKeySpec(SECRET, AES);

        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[IV_LENGTH + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, IV_LENGTH);
        System.arraycopy(encrypted, 0, combined, IV_LENGTH, encrypted.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    public static String decrypt(String cipherText) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(cipherText);
        byte[] iv = new byte[IV_LENGTH];
        byte[] cipherBytes = new byte[decoded.length - IV_LENGTH];

        System.arraycopy(decoded, 0, iv, 0, IV_LENGTH);
        System.arraycopy(decoded, IV_LENGTH, cipherBytes, 0, cipherBytes.length);

        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
        SecretKey key = new SecretKeySpec(SECRET, AES);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decrypted = cipher.doFinal(cipherBytes);

        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
