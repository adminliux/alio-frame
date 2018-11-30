package ml.alio.frame.common

import org.apache.commons.codec.binary.Base64
import org.apache.commons.io.IOUtils

import javax.crypto.Cipher
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.security.*
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import kotlin.Pair
/**
 * RSA加密算法工具
 */
object RSAUtils {

    private const val CHARSET = "UTF-8"
    private const val RSA_ALGORITHM = "RSA"


    fun createKeys(keySize: Int): Pair<String, String> {
        //为RSA算法创建一个KeyPairGenerator对象
        val kpg: KeyPairGenerator
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalArgumentException("No such algorithm-->[$RSA_ALGORITHM]")
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize)
        //生成密匙对
        val keyPair = kpg.generateKeyPair()
        //得到公钥
        val publicKey = keyPair.public
        val publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.encoded)
        //得到私钥
        val privateKey = keyPair.private
        val privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.encoded)

        return publicKeyStr to privateKeyStr
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     */
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun getPublicKey(publicKey: String): RSAPublicKey {
        //通过X509编码的Key指令获得公钥对象
        val keyFactory = KeyFactory.getInstance(RSA_ALGORITHM)
        val x509KeySpec = X509EncodedKeySpec(Base64.decodeBase64(publicKey))
        return keyFactory.generatePublic(x509KeySpec) as RSAPublicKey
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     */
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun getPrivateKey(privateKey: String): RSAPrivateKey {
        //通过PKCS#8编码的Key指令获得私钥对象
        val keyFactory = KeyFactory.getInstance(RSA_ALGORITHM)
        val pkcs8KeySpec = PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey))
        return keyFactory.generatePrivate(pkcs8KeySpec) as RSAPrivateKey
    }

    /**
     * 公钥加密
     *
     * @param data      数据
     * @param publicKey 公钥
     */
    fun encrypt(data: String, publicKey: RSAPublicKey): String {
        try {
            val cipher = Cipher.getInstance(RSA_ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.toByteArray(charset(CHARSET)), publicKey.modulus.bitLength()))
        } catch (e: Exception) {
            throw RuntimeException("加密字符串[$data]时遇到异常", e)
        }

    }

    /**
     * 私钥解密
     *
     * @param data       数据
     * @param privateKey 私钥
     */

    fun decrypt(data: String, privateKey: RSAPrivateKey): String {
        try {
            val cipher = Cipher.getInstance(RSA_ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            return String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.modulus.bitLength()),
                    Charset.forName(CHARSET))
        } catch (e: Exception) {
            throw RuntimeException("解密字符串[$data]时遇到异常", e)
        }

    }


    private fun rsaSplitCodec(cipher: Cipher, opmode: Int, datas: ByteArray, keySize: Int): ByteArray {
        val maxBlock: Int = if (opmode == Cipher.DECRYPT_MODE) {
            keySize / 8
        } else {
            keySize / 8 - 11
        }
        val out = ByteArrayOutputStream()
        var offSet = 0
        var buff: ByteArray
        var i = 0
        try {
            while (datas.size > offSet) {
                buff = if (datas.size - offSet > maxBlock) {
                    cipher.doFinal(datas, offSet, maxBlock)
                } else {
                    cipher.doFinal(datas, offSet, datas.size - offSet)
                }
                out.write(buff, 0, buff.size)
                i++
                offSet = i * maxBlock
            }
        } catch (e: Exception) {
            throw RuntimeException("加解密阀值为[$maxBlock]的数据时发生异常", e)
        }

        val resultDatas = out.toByteArray()
        out.close()
        return resultDatas
    }

}