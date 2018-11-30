package ml.alio.frame.common

import org.apache.commons.codec.binary.Base64
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher


object RSAHelper {

    @Throws(NoSuchAlgorithmException::class)
    fun keyPair(): KeyPair {
        val keyPairGen = KeyPairGenerator.getInstance("RSA")
        //密钥位数
        keyPairGen.initialize(1024)
        //密钥对
        return keyPairGen.generateKeyPair()
    }


    @Throws(Exception::class)
    fun getPublicKey(key: String): PublicKey {
        val keyBytes: ByteArray = Base64.decodeBase64(key)

        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec)
    }

    @Throws(Exception::class)
    fun getPrivateKey(key: String): PrivateKey {
        val keyBytes: ByteArray = Base64.decodeBase64(key)

        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(keySpec)
    }


    @Throws(Exception::class)
    fun getKeyString(key: Key): String {
        val keyBytes = key.encoded
        return Base64.encodeBase64String(keyBytes)
    }

    /**
     * 加密
     *
     * @param plainText 文本
     * @param publicKey 公钥
     * @return 密码
     */
    fun encryption(plainText: String, publicKey: PublicKey): String {
        try {
            //加解密类
            val cipher = Cipher.getInstance("RSA")
            //加密
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            val enBytes = cipher.doFinal(plainText.toByteArray())
            return Base64.encodeBase64URLSafeString((enBytes))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * 解密
     *
     * @param privateKey 私钥
     * @param plainText  文本
     * @return 文本
     */
    fun decrypt(privateKey: PrivateKey, plainText: String): String {
        try {
            val cipher = Cipher.getInstance("RSA")
            //解密
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            val deBytes = cipher.doFinal(plainText.toByteArray())

            return String(deBytes)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
} 