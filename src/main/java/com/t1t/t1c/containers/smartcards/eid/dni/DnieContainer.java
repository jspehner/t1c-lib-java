package com.t1t.t1c.containers.smartcards.eid.dni;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.rest.RestExecutor;
import com.t1t.t1c.utils.CertificateUtil;
import com.t1t.t1c.utils.PinUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class DnieContainer extends GenericContainer<DnieContainer, GclDniRestClient, DnieAllData, DnieAllCertificates> {

    public DnieContainer(LibConfig config, GclReader reader, GclDniRestClient gclDniRestClient) {
        super(config, reader, gclDniRestClient, null);
    }

    @Override
    public DnieContainer createInstance(LibConfig config, GclReader reader, GclDniRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.DNIE;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("authentication-certificate", "signing-certificate", "intermediate-certificate", "info");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Arrays.asList("authentication-certificate", "signing-certificate", "intermediate-certificate", "info");
    }

    @Override
    public DnieAllData getAllData() throws RestException, NoConsentException {
        return getAllData(null, null);
    }

    @Override
    public DnieAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws RestException, NoConsentException {
        return new DnieAllData(RestExecutor.returnData(httpClient.getDnieAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);

    }

    @Override
    public DnieAllData getAllData(Boolean... parseCertificates) throws RestException, NoConsentException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public DnieAllCertificates getAllCertificates() throws RestException, NoConsentException {
        return getAllCertificates(null, null);
    }

    @Override
    public DnieAllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws RestException, NoConsentException {
        return new DnieAllCertificates(RestExecutor.returnData(httpClient.getDnieAllCertificates(getTypeId(), reader.getId(), createFilterParams(filterParams)), config.isConsentRequired()), parseCertificates);
    }

    @Override
    public DnieAllCertificates getAllCertificates(Boolean... parseCertificates) throws RestException, NoConsentException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public Boolean verifyPin(String... pin) throws RestException, NoConsentException, VerifyPinException {
        PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
        try {
            if (pin != null && pin.length > 0) {
                Preconditions.checkArgument(pin.length == 1, "Only one PIN allowed as argument");
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId(), new GclVerifyPinRequest().withPin(pin[0])), config.isConsentRequired()));
            } else {
                return RestExecutor.isCallSuccessful(RestExecutor.executeCall(httpClient.verifyPin(type.getId(), reader.getId()), config.isConsentRequired()));
            }
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws VerifyPinException, NoConsentException, RestException {
        try {
            Preconditions.checkNotNull(data, "data to authenticate must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.authenticate(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws VerifyPinException, NoConsentException, RestException {
        try {
            Preconditions.checkNotNull(data, "data to sign must not be null");
            Preconditions.checkNotNull(algo, "digest algorithm must not be null");
            PinUtil.pinEnforcementCheck(reader, config.isHardwarePinPadForced(), pin);
            return RestExecutor.returnData(httpClient.sign(getTypeId(), reader.getId(), PinUtil.setPinIfPresent(new GclAuthenticateOrSignData().withData(data).withAlgorithmReference(algo.getStringValue()), pin)), config.isConsentRequired());
        } catch (RestException ex) {
            throw PinUtil.checkPinExceptionMessage(ex);
        }
    }

    public T1cCertificate getAuthenticationCertificate(Boolean... parse) throws RestException, NoConsentException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getAuthenticationCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getIntermediateCertificate(Boolean... parse) throws RestException, NoConsentException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getIntermediateCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public T1cCertificate getSigningCertificate(Boolean... parse) throws RestException, NoConsentException {
        return CertificateUtil.createT1cCertificate(RestExecutor.returnData(httpClient.getSigningCertificate(getTypeId(), reader.getId()), config.isConsentRequired()), parse);
    }

    public GclDnieInfo getInfo() throws RestException, NoConsentException {
        return RestExecutor.returnData(httpClient.getDnieInfo(getTypeId(), reader.getId()), config.isConsentRequired());
    }

    @Override
    public ContainerType getType() {
        return type;
    }

    @Override
    public String getTypeId() {
        return type.getId();
    }

    @Override
    public Class<DnieAllData> getAllDataClass() {
        return DnieAllData.class;
    }

    @Override
    public Class<DnieAllCertificates> getAllCertificatesClass() {
        return DnieAllCertificates.class;
    }

    @Override
    public ContainerData dumpData(String... pin) throws RestException, NoConsentException, UnsupportedOperationException {
        ContainerData data = new ContainerData();
        DnieAllData allData = getAllData(true);
        data.setAllCertificates(getCertificatesMap(allData));
        data.setSigningCertificateChain(orderCertificates(allData.getIntermediateCertificate(), allData.getSigningCertificate()));
        data.setAuthenticationCertificateChain(orderCertificates(allData.getIntermediateCertificate(), allData.getSigningCertificate()));
        data.setCertificateChains(Arrays.asList(data.getAuthenticationCertificateChain(), data.getSigningCertificateChain()));
        GclDnieInfo info = allData.getInfo();
        data.setGivenName(info.getFirstName());
        data.setSurName(info.getFirstLastName() + " " + info.getSecondLastName());
        data.setFullName(info.getFirstName() + " " + data.getSurName());
        data.setDocumentId(info.getSerialNumber());
        return data;
    }

    @Override
    public Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        DnieAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getSigningCertificate(), certs.getIntermediateCertificate());
    }

    @Override
    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, NoConsentException, RestException {
        DnieAllCertificates certs = getAllCertificates(true);
        return orderCertificates(certs.getAuthenticationCertificate(), certs.getIntermediateCertificate());
    }

    private Map<String, T1cCertificate> getCertificatesMap(DnieAllData allData) {
        Map<String, T1cCertificate> certs = new HashMap<>();
        certs.put("intermediate-certificate", allData.getIntermediateCertificate());
        certs.put("authentication-certificate", allData.getAuthenticationCertificate());
        certs.put("signing-certificate", allData.getSigningCertificate());
        return certs;
    }
}