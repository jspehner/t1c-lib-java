package com.t1t.t1c.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GclVerifyPinRequest {

    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("private_key_reference")
    private String privateKeyReference;

    /**
     * @return The pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * @param pin The pin
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    public GclVerifyPinRequest withPin(String pin) {
        this.pin = pin;
        return this;
    }

    public String getPrivateKeyReference() {
        return privateKeyReference;
    }

    public void setPrivateKeyReference(String privateKeyReference) {
        this.privateKeyReference = privateKeyReference;
    }

    public GclVerifyPinRequest withPrivateKeyReference(String privateKeyReference) {
        this.privateKeyReference = privateKeyReference;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(pin).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GclVerifyPinRequest)) {
            return false;
        }
        GclVerifyPinRequest rhs = ((GclVerifyPinRequest) other);
        return new EqualsBuilder().append(pin, rhs.pin).isEquals();
    }

}
