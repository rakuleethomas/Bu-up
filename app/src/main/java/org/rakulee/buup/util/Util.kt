package org.rakulee.buup.util

import android.content.Context
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
    fun encryptPassword(password : String) : ByteArray{
        val random = SecureRandom()
        val salt = ByteArray(256)
        random.nextBytes(salt)
        val pbKeySpec = PBEKeySpec(password.toCharArray(), salt, 1111, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES") // 4

        val ivRandom = SecureRandom()
        val iv = ByteArray(16)
        ivRandom.nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encrypted = cipher.doFinal(password.toByteArray())
        return encrypted
    }


    /**
     * Pre: Plain password : String
     * Post: Decrypted password : ByteArray
     */

    fun decryptPassword(password : String) : ByteArray{

        val map = HashMap<String, ByteArray>()
        val salt = map["salt"]
        val iv = map["iv"]
        val encrypted = map["encrypted"]

        //regenerate key from password
        val pbKeySpec = PBEKeySpec(password.toCharArray(), salt, 1111, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")


        //Decrypt
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        return cipher.doFinal(encrypted)
    }
}