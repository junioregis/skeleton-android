package com.domain.skeleton.core.security

import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.domain.skeleton.core.exception.keystore.*
import com.domain.skeleton.core.ext.chunked
import com.domain.skeleton.core.ext.join
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal
import kotlin.math.floor

class AppKeyStore @Throws(KeyStoreInitializeException::class) constructor(private val context: Context) {

    private val keyStore: KeyStore

    private val entry: KeyStore.PrivateKeyEntry
        get() {
            return try {
                keyStore.getEntry(ALIAS, null) as KeyStore.PrivateKeyEntry
            } catch (e: Exception) {
                throw KeyStoreEntryException(e)
            }
        }

    private val maxEncodeByteSize: Int
        get() {
            return floor(floor(KEY_SIZE.toDouble() / 8) - 11)
                    .toInt()
        }

    init {
        try {
            keyStore = KeyStore.getInstance(PROVIDER)
            keyStore.load(null)
        } catch (e: Exception) {
            throw KeyStoreInitializeException(e)
        }
    }

    @Throws(KeyStoreNotInitialized::class)
    fun exists(): Boolean {
        return try {
            keyStore.containsAlias(ALIAS)
        } catch (e: Exception) {
            throw KeyStoreNotInitialized(ALIAS)
        }
    }

    @Suppress("DEPRECATION")
    @Throws(KeyStoreGenerateException::class)
    fun generate() {
        try {
            val start = GregorianCalendar()
            val end = GregorianCalendar()

            end.add(Calendar.YEAR, VALIDATION_YEARS)

            val generator = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER)
            val subject = X500Principal("CN=$ALIAS")

            val spec = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                val builder = KeyPairGeneratorSpec.Builder(context)
                        .setAlias(ALIAS)
                        .setSubject(subject)
                        .setSerialNumber(SERIAL)
                        .setStartDate(start.time)
                        .setEndDate(end.time)
                        .setKeySize(KEY_SIZE)

                builder.build()
            } else {
                val purposes = KeyProperties.PURPOSE_VERIFY or
                        KeyProperties.PURPOSE_SIGN or
                        KeyProperties.PURPOSE_DECRYPT

                KeyGenParameterSpec.Builder(ALIAS, purposes)
                        .setCertificateSubject(subject)
                        .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                        .setCertificateSerialNumber(SERIAL)
                        .setKeySize(KEY_SIZE)
                        .setKeyValidityStart(start.time)
                        .setKeyValidityEnd(end.time)
                        .setDigests(KeyProperties.DIGEST_SHA1)
                        .build()
            }

            generator.initialize(spec, SecureRandom())
            generator.generateKeyPair()
        } catch (e: Exception) {
            throw KeyStoreGenerateException(e)
        }
    }

    @Throws(KeyStoreDeletionException::class)
    fun delete() {
        try {
            keyStore.deleteEntry(ALIAS)
        } catch (e: Exception) {
            throw KeyStoreDeletionException(ALIAS, e)
        }
    }

    @Throws(KeyStoreEncodeException::class)
    fun encodeToString(text: String, charset: Charset = DEFAULT_CHARSET): String {
        return if (text.isNotEmpty()) {
            val bytes = text.toByteArray(charset)

            Base64.encodeToString(encode(bytes), Base64.DEFAULT)
        } else {
            ""
        }
    }

    @Throws(KeyStoreEncodeException::class)
    fun encode(bytes: ByteArray): ByteArray {
        return try {
            val cipher = Cipher.getInstance(CIPHER)

            cipher.init(Cipher.ENCRYPT_MODE, entry.certificate.publicKey)

            val chuncked = bytes.chunked(maxEncodeByteSize)

            chuncked.map {
                cipher.doFinal(it)
            }.toTypedArray()
                    .join()
        } catch (e: Exception) {
            throw KeyStoreEncodeException(e)
        }
    }

    @Throws(KeyStoreDecodeException::class)
    fun decodeToString(text: String, charset: Charset = DEFAULT_CHARSET): String {
        return if (text.isNotEmpty()) {
            val decoded = Base64.decode(text, Base64.DEFAULT)

            String(decode(decoded), charset)
        } else {
            ""
        }
    }

    @Throws(KeyStoreDecodeException::class)
    fun decode(bytes: ByteArray): ByteArray {
        return try {
            val cipher = Cipher.getInstance(CIPHER)

            cipher.init(Cipher.DECRYPT_MODE, entry.privateKey)

            val chuncked = bytes.chunked(CHUNK_SIZE)

            chuncked.map {
                cipher.doFinal(it)
            }.toTypedArray()
                    .join()
        } catch (e: Exception) {
            throw KeyStoreDecodeException(e)
        }
    }

    companion object {

        private const val ALIAS = "AppAlias"

        private const val PROVIDER = "AndroidKeyStore"

        private val ALGORITHM = "RSA"

        private val KEY_SIZE = 2048

        private val CHUNK_SIZE = 256

        private val SERIAL = BigInteger.valueOf(1337)

        private const val CIPHER = "RSA/ECB/PKCS1Padding"

        private const val VALIDATION_YEARS = 25

        private const val DEFAULT_ENCODE = "UTF-8"

        private val DEFAULT_CHARSET = Charset.forName(DEFAULT_ENCODE)
    }
}