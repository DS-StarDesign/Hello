package ds.app.cs24rider.Utils;

import android.content.res.Resources;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLSocketFactory;
/*
 *  Created By MD. OLI ULLAH DEWAN 12-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public abstract class TLSCertificate {
    public abstract SSLSocketFactory getSSLSocketFactory();
    public abstract void loadCertificate() throws Resources.NotFoundException, CertificateException, IOException;
    public abstract void loadKeyStore() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, KeyManagementException;
}
