package com.mohamedachrefgharbi.stompprotocolclientlibrary.stomp.Utils;

import android.util.Log;

import org.apache.http.conn.ssl.StrictHostnameVerifier;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by agharbi on 19/07/2019.
 */

public class AllCertificatesAndHostsTruster implements TrustManager, X509TrustManager {

    @Override
    public final void checkClientTrusted(final X509Certificate[] xcs, final String string)
            throws CertificateException {
    }

    @Override
    public final void checkServerTrusted(final X509Certificate[] xcs, final String string)
            throws CertificateException {
    }

    @Override
    public final X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    /**
     * Gets an {@link SSLContext} which trusts all certificates.
     * @return {@link SSLContext}
     */
    public static SSLContext getSSLContext() {
        final TrustManager[] trustAllCerts =
                new TrustManager[] {new AllCertificatesAndHostsTruster()};

        try {
            final SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, trustAllCerts, new SecureRandom());
            return context;

        } catch (Exception exc) {
            Log.e("CertHostTruster", "Unable to initialize the Trust Manager to trust all the "
                    + "SSL certificates and HTTPS hosts.", exc);
            return null;
        }
    }

    /**
     * Call this method once before all your network calls
     * to accept all the self-signed certificates in HTTPS connections.
     */
    public static void apply() {
        final TrustManager[] trustAllCerts =
                new TrustManager[] {new AllCertificatesAndHostsTruster()};

        try {
            final SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
            StrictHostnameVerifier verifier = new StrictHostnameVerifier();
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return verifier.verify(hostname,session);
                }
            });

        } catch (Exception exc) {
            Log.e("CertHostTruster", "Unable to initialize the Trust Manager to trust all the "
                    + "SSL certificates and HTTPS hosts.", exc);
        }
    }
}