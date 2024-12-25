package com.jack.learn.thirdlib.okhttp

import android.content.Context
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import java.io.ByteArrayInputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class CustomCertificateValidation {
    //只信任指定证书（传入字符串）
    fun setCertificate(context: Context,builder: OkHttpClient.Builder,cerStr: String) {
        val factory = CertificateFactory.getInstance("X.509")
        val byteArrayInputStream = ByteArrayInputStream(cerStr.toByteArray())
        val ca = factory.generateCertificate(byteArrayInputStream)

        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null,null)
        keyStore.setCertificateEntry("ca",ca)

        byteArrayInputStream.close()

        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(keyStore)

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null,tmf.trustManagers, SecureRandom())
        builder.sslSocketFactory(sslContext.socketFactory, tmf.trustManagers[0] as X509TrustManager)
        builder.hostnameVerifier(object : HostnameVerifier {
            override fun verify(hostname: String?, session: SSLSession?): Boolean {
                return true
            }

        })
    }
}