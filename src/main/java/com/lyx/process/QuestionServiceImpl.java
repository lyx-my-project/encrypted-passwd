package com.lyx.process;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.lyx.common.CommonResult;
import com.lyx.entity.Question;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service("questionServiceImpl")
public class QuestionServiceImpl
{
    /**
     * 获取加密密码
     * @param question 前端数据，密钥.
     * @return 加密密码
     */
    public CommonResult getPasswd(Question question)
    {
        // 读取密文
        byte[] encryptContent;
        try
        {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/EncryptContent");
            encryptContent = StreamUtils.copyToByteArray(inputStream);
            inputStream.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }

        // 获取密钥
        byte[] key = this.getKey(StrUtil.format("{}{}", question.getChuoHao(), question.getXiaoMing()));

        // 解密数据
        AES aes = SecureUtil.aes(key);
        String content = aes.decryptStr(encryptContent);

        return CommonResult.successData(content);
    }

    /**
     * 获取密钥
     * @param keyStr 用来生成密钥字符串，可以把它等同于密钥.
     * @return 密钥
     */
    public byte[] getKey(String keyStr)
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
