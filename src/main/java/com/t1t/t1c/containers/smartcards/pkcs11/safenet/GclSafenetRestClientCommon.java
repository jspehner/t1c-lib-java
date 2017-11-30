package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.GclSafeNetInfo;
import com.t1t.t1c.model.rest.GclSafeNetRequest;
import com.t1t.t1c.model.rest.GclSafeNetSlot;
import com.t1t.t1c.containers.ContainerRestClient;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import t1c.containers.smartcards.pkcs11.safenet.GclSafeNetInfo;
import t1c.containers.smartcards.pkcs11.safenet.GclSafeNetRequest;
import t1c.containers.smartcards.pkcs11.safenet.GclSafeNetSlot;

import java.util.List;
/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Safenet token Container
 */
public interface GclSafenetRestClientCommon extends ContainerRestClient {
    @POST(CONTAINER_AND_READER_CONTEXT_PATH + CERTIFICATES_PATH)
    Call<T1cResponse<List<String>>> getSafeNetCertificates(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclSafeNetRequest request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/info")
    Call<T1cResponse<GclSafeNetInfo>> getSafeNetInfo(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclSafeNetRequest request);

    @POST(CONTAINER_AND_READER_CONTEXT_PATH + "/slots")
    Call<T1cResponse<List<GclSafeNetSlot>>> getSafeNetSlots(@Path("containerId") String containerId, @Path("reader") String readerId, @Body GclSafeNetRequest request, @Query("token-present") Boolean tokenPresent);
}
