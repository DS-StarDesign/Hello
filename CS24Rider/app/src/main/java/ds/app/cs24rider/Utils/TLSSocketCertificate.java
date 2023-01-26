package ds.app.cs24rider.Utils;

import android.content.res.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import ds.app.cs24rider.MainApplication;
import ds.app.cs24rider.R;

/*
 *  Created By MD. OLI ULLAH DEWAN 12-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class TLSSocketCertificate extends TLSCertificate {

    private CertificateFactory cf;
    private InputStream cert;
    private Certificate ca;
    private KeyStore keyStore;
    private TrustManagerFactory tmf;
    private SSLContext sslContext;
    private String keyStoreType;
    private String tmfAlgorithm;

    public TLSSocketCertificate() throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        this.tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        this.keyStoreType = KeyStore.getDefaultType();
        this.loadCertificate();
        this.loadKeyStore();
    }

    @Override
    public SSLSocketFactory getSSLSocketFactory() {
        return sslContext.getSocketFactory();
    }

    @Override
    public void loadCertificate() throws Resources.NotFoundException, CertificateException, IOException {
        cf = CertificateFactory.getInstance("X.509");
        cert = MainApplication.getInstance().getResources().openRawResource(R.raw.mycert);
        ca = cf.generateCertificate(cert);
        cert.close();
    }

    @Override
    public void loadKeyStore() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, KeyManagementException {
        keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
        tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
    }
}
