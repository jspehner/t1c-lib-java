package com.t1t.t1c.containers.smartcards.emv;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class EmvContainer extends GenericContainer<EmvContainer, GclEmvRestClient> {

    private static final Logger log = LoggerFactory.getLogger(EmvContainer.class);

    public EmvContainer(LibConfig config, GclReader reader, GclEmvRestClient gclEmvRestClient) {
        this.config = config;
        this.reader = reader;
        this.httpClient = gclEmvRestClient;
    }

    @Override
    public EmvContainer createInstance(LibConfig config, GclReader reader, GclEmvRestClient httpClient, String pin) {
        return null;
    }

    @Override
    public List<String> getAllDataFilters() {
        return null;
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return null;
    }

    @Override
    public AllData getAllData() throws GenericContainerException {
        return null;
    }

    @Override
    public AllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public AllData getAllData(Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates() throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates(Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException {
        return null;
    }

    @Override
    public String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException {
        return null;
    }

    @Override
    public String sign(GclAuthenticateOrSignData data) throws GenericContainerException {
        return null;
    }

    @Override
    public ContainerType getType() {
        return null;
    }

    @Override
    public String getTypeId() {
        return null;
    }
}