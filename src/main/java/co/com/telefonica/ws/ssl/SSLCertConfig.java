package co.com.telefonica.ws.ssl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

@Slf4j
@Configuration
public class SSLCertConfig implements X509TrustManager {

    @Bean
    @SneakyThrows
    public Boolean disableSSLValidation() {
        final SSLContext sslContext = SSLContext.getInstance("TLS");

        sslContext.init(null, new TrustManager[]{new SSLCertConfig()}, null);

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifierConfig());

        return true;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
        log.info("checkClientTrusted ");
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
        log.info("checkClientTrusted ");
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

}
