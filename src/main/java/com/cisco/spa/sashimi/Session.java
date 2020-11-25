package com.cisco.spa.sashimi;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

enum STATE
{
    AUTHENTICATED, DISCONNECTED, STARTED;
}

@Entity
public class Session {

    @NotBlank
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "Radius Address must be valid IP Address")
    private String radiusAddress;
    @NotBlank(message="Missing RADIUS Shared secret")
    private String radiusSecret;
    private String username;
    private String password;
    @NotBlank
    @Pattern(regexp = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$", message = "Must be valid MAC Address")
    private String macAddress;
    private String auditId = UUID.randomUUID().toString().replace("-", "");
    private String accountingId;
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "Framed address must be valid IP Address")
    private String framedIp;
    private String framedIpv6;
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "Framed Mask must be valid IP Mask")
    private String framedIpMask = "255.255.255.0";
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "NAS IP address must be valid IP Address")
    private String nasIp;
    private String nasIpv6;
    private Integer nasPort;
    private String nasPortId;
    private Integer nasPortType;
    @Enumerated(EnumType.ORDINAL)
    private STATE state;


    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public String getRadiusAddress() {
        return radiusAddress;
    }

    public void setRadiusAddress(String radiusAddress) {
        this.radiusAddress = radiusAddress;
    }

    public String getRadiusSecret() {
        return radiusSecret;
    }

    public void setRadiusSecret(String radiusSecret) {
        this.radiusSecret = radiusSecret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getAccountingId() {
        return accountingId;
    }

    public void setAccountingId(String accountingId) {
        this.accountingId = accountingId;
    }

    public String getFramedIp() {
        return framedIp;
    }

    public void setFramedIp(String framedIp) {
        this.framedIp = framedIp;
    }

    public String getFramedIpv6() {
        return framedIpv6;
    }

    public void setFramedIpv6(String framedIpv6) {
        this.framedIpv6 = framedIpv6;
    }

    public String getFramedIpMask() {
        return framedIpMask;
    }

    public void setFramedIpMask(String framedIpMask) {
        this.framedIpMask = framedIpMask;
    }

    public String getNasIp() {
        return nasIp;
    }

    public void setNasIp(String nasIp) {
        this.nasIp = nasIp;
    }

    public String getNasIpv6() {
        return nasIpv6;
    }

    public void setNasIpv6(String nasIpv6) {
        this.nasIpv6 = nasIpv6;
    }

    public Integer getNasPort() {
        return nasPort;
    }

    public void setNasPort(Integer nasPort) {
        this.nasPort = nasPort;
    }

    public String getNasPortId() {
        return nasPortId;
    }

    public void setNasPortId(String nasPortId) {
        this.nasPortId = nasPortId;
    }

    public Integer getNasPortType() {
        return nasPortType;
    }

    public void setNasPortType(Integer nasPortType) {
        this.nasPortType = nasPortType;
    }

}
