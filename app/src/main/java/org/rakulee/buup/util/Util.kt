package org.rakulee.buup.util

import android.content.Context
import android.util.Base64
import android.util.DisplayMetrics
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object Util {


    fun pxToDp(px : Double, context : Context) : Double {
         return (1.0 * px * (context.resources.displayMetrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT)
    }
    fun pxToDp(px : Int, context : Context) : Double {
        return (1.0 * px * (context.resources.displayMetrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun dpToPx(dp : Double, context: Context) : Double{
        return 1.0 * dp * (context.resources.displayMetrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT
    }

    fun dpToPx(dp : Int, context: Context) : Double{
        return 1.0 * dp *(context.resources.displayMetrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT
    }

    /**
     * Pre: Plain password : String
     * Post: Encrypted password : ByteArray
     */

    const val secretKey = "asdfasdfasdf"
    const val salt = "1234NHNhMTJTQWZ2bGhpV3U=" // base64 decode => AiF4sa12SAfvlhiWu
    const val iv = "bVQz2225RkQ1Njc4UUFaWA==" // base64 decode => mT34SaFD5678QAZX

    fun encryptPassword(password: String) :  String? {
        try
        {
            val ivParameterSpec = IvParameterSpec(Base64.decode(iv, Base64.DEFAULT))

            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec =  PBEKeySpec(secretKey.toCharArray(), Base64.decode(salt, Base64.DEFAULT), 10000, 256)
            val tmp = factory.generateSecret(spec)
            val secretKey =  SecretKeySpec(tmp.encoded, "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
            return Base64.encodeToString(cipher.doFinal(password.toByteArray(Charsets.UTF_8)), Base64.DEFAULT)
        }
        catch (e: Exception){
            println("Error while encrypting: $e")
        }
        return null
    }


    /**
     * Pre: Plain password : String
     * Post: Decrypted password : ByteArray
     */

    fun decryptPassword(encryptedPassword : String) : String? {
        try{

            val ivParameterSpec =  IvParameterSpec(Base64.decode(iv, Base64.DEFAULT))

            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec =  PBEKeySpec(secretKey.toCharArray(), Base64.decode(salt, Base64.DEFAULT), 10000, 256)
            val tmp = factory.generateSecret(spec);
            val secretKey =  SecretKeySpec(tmp.encoded, "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return  String(cipher.doFinal(Base64.decode(encryptedPassword, Base64.DEFAULT)))
        }
        catch (e : Exception) {
            println("Error while decrypting: $e");
        }
        return null
    }


    fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }

    fun String.decodeHex(): ByteArray {
        check(length % 2 == 0) { "Must have an even length" }

        return chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
    }
}