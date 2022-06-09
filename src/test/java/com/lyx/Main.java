package com.lyx;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main
{
    public static void main(String[] args)
    {
        // 明文
        String content = "jizhu.com741123";

        // 密钥
        byte[] key = getKey("jiujinhongluzi");

        // 生成密文
        AES aes = SecureUtil.aes(key);
        byte[] encryptData = aes.encrypt(content);

        // 写入密文
        FileUtil.writeBytes(encryptData, "/Users/lgf/my-dir/project/other/encrypted-passwd/src/main/resources/static/EncryptContent");
    }

    /**
     * 获取密钥
     * @param keyStr 用来生成密钥字符串，可以把它等同于密钥.
     * @return 密钥
     */
    public static byte[] getKey(String keyStr)
    {
        MessageDigest dig;
        try
        {
            dig = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e.getMessage());
        }
        byte[] key = dig.digest(keyStr.getBytes(CharsetUtil.CHARSET_UTF_8));
        SecretKeySpec secKey = new SecretKeySpec(key, SymmetricAlgorithm.AES.getValue());

        return secKey.getEncoded();
    }
}
