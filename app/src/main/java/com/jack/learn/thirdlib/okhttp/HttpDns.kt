package com.jack.learn.thirdlib.okhttp

import okhttp3.Dns
import java.net.InetAddress

/**
 * 在 OkHttp 中使用 HTTPDNS，有两种方式。
 * 1.通过拦截器，在发送请求之前，将域名替换为 IP 地址。
 * 但是这种方案存在一些问题，例如：HTTPS 下 IP 直连的证书问题、代理的问题、Cookie 的问题等等。
 * 其中最严重的问题是，此方案（拦截器+HTTPDNS）遇到 https 时，如果存在一台服务器支持多个域名，可能导致证书无法匹配的问题。
 * 这就引发出来 SNI 方案，SNI（Server Name Indication）是为了解决一个服务器使用多个域名和证书的 SSL/TLS 扩展。
 * SNI 的工作原理，在连接到服务器建立 SSL 连接之前，先发送要访问站点的域名（hostname），服务器根据这个域名返回正确的证书。现在，大部分操作系统和浏览器，都已经很好的支持 SNI 扩展。
 *
 * 2.通过 OkHttp 提供的 .dns() 接口，配置 HTTPDNS。
 * 只需要实现 OkHttp 的 Dns 接口，即可获得 HTTPDNS 的支持。
 * 使用也非常的简单，在 OkHttp.build() 时，通过 dns() 方法配置。
 */
class HttpDns:Dns {
    override fun lookup(hostname: String): List<InetAddress> {

        // 先使用HttpDns 如果失败在使用传统的dns
        return emptyList()
    }
}