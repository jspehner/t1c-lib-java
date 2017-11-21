package com.t1t.t1c;

import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.readerapi.ReaderApiContainer;
import com.t1t.t1c.containers.remoteloading.RemoteLoadingContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.dni.DnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.PtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.EmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.MobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.OcraContainer;
import com.t1t.t1c.containers.smartcards.piv.PivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainerConfiguration;
import com.t1t.t1c.containers.smartcards.pki.aventra.AventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.OberthurContainer;
import com.t1t.t1c.core.Core;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.ocv.IOcvClient;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IT1cClient {
    /*General*/
    Core getCore();
    ConnectionFactory getConnectionFactory();

    /*Clients*/
    IDsClient getDsClient();
    IOcvClient getOcvClient();

    /*Containers*/
    GenericContainer getGenericContainer(String readerId);
    BeIdContainer getBeIdContainer(String readerId);
    LuxIdContainer getLuxIdContainer(String readerId, String pin);
    LuxTrustContainer getLuxTrustContainer(String readerId, String pin);
    DnieContainer getDnieContainer(String readerId);
    EmvContainer getEmvContainer(String readerId);
    MobibContainer getMobibContainer(String readerId);
    OcraContainer getOcraContainer(String readerId);
    AventraContainer getAventraContainer(String readerId);
    OberthurContainer getOberthurContainer(String readerId);
    PivContainer getPivContainer(String readerId);
    PtEIdContainer getPtIdContainer(String readerId);
    SafeNetContainer getSafeNetContainer(String readerId);
    SafeNetContainer getSafeNetContainer(String readerId, SafeNetContainerConfiguration configuration);

    /*Functional containers*/
    RemoteLoadingContainer getRemoteLoadingContainer(String readerId);
    ReaderApiContainer getReaderApiContainer();

    /*DS Functionality*/
    String getDownloadLink();
    List<GclReader> getAuthenticateCapableReaders();
    List<GclReader> getSignCapableReaders();
    List<GclReader> getPinVerificationCapableReaders();
}
