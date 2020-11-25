package com.cisco.spa.sashimi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import code.messy.net.radius.attribute.*;
import code.messy.net.radius.packet.AccessRequest;
import code.messy.net.radius.packet.AccountingRequest;
import code.messy.net.radius.packet.RadiusPacket;

public class RadiusActions {

    static DatagramChannel channel(String address, int port) throws IOException {
        InetAddress a = InetAddress.getByName(address);
        InetSocketAddress sa = new InetSocketAddress(a, port);
        DatagramChannel channel = DatagramChannel.open();
        channel.connect(sa);
        return channel;
    }

    static RadiusPacket packet(DatagramChannel channel, ByteBuffer payload) throws IOException {
        channel.write(payload);
        ByteBuffer bb = ByteBuffer.allocate(10 * 1024);
        channel.read(bb);
        bb.flip();
        channel.close();
        return RadiusPacket.create(bb);
    }

    static AccountingRequest accounting(Session session) throws UnknownHostException {
        byte[] requestAuthenticator = new byte[16];
        AccountingRequest req = new AccountingRequest(session.getRadiusSecret());
        req.add(new UserName(session.getUsername()));
        req.add(new UserPassword(session.getPassword(), session.getRadiusSecret(), requestAuthenticator));
        req.add(new NASIPAddress(InetAddress.getByName(session.getNasIp()).getAddress()));
        req.add(new CallingStationID(session.getMacAddress()));
        req.add(new CiscoVSA("audit-session-id=" + session.getAuditId()));

        req.add(new AcctDelayTime(1));
        req.add(new AcctInputOctets(2));
        req.add(new AcctOutputOctets(3));
        req.add(new AcctAuthentic(AcctAuthentic.Type.RADIUS));
        req.add(new AcctSessionTime(4));
        req.add(new AcctInputPackets(5));
        req.add(new AcctOutputPackets(6));
        req.add(new AcctMultiSessionID("321"));
        req.add(new AcctLinkCount(1));

        if(session.getAccountingId() != null){
            req.add(new AcctSessionID(session.getAccountingId()));
        } else {
            req.add(new AcctSessionID(session.getAuditId()));
        }
        if (session.getFramedIp() != null) {
            req.add(new FramedIPAddress(InetAddress.getByName(session.getFramedIp()).getAddress()));
            req.add(new FramedIPNetmask(InetAddress.getByName(session.getFramedIpMask()).getAddress()));
        }
        if (session.getNasPort() != null) {
            req.add(new NASPort(session.getNasPort()));
        }
        if (session.getNasPortId() != null) {
            req.add(new NASPortID(session.getNasPortId()));
        }
        if (session.getNasPortType() != null) {
            req.add(new NASPortType(session.getNasPortType()));
        }
        if (session.getFramedIpv6() != null) {
            String[] addresses = session.getFramedIpv6().split(",");
            for (String ipv6 : addresses) {
                req.add(new FramedIPv6Address(InetAddress.getByName(ipv6).getAddress()));
            }
        }
        if (session.getNasIpv6() != null) {
            req.add(new NASIPv6Address(InetAddress.getByName(session.getNasIpv6()).getAddress()));
        }
        return req;
    }

    static RadiusPacket stop(Session session) throws IOException {
        DatagramChannel channel = channel(session.getRadiusAddress(), 1813);
        AccountingRequest req = accounting(session);
        req.add(new AcctStatusType(AcctStatusType.Type.Stop));
        return packet(channel, req.getPayload());
    }

    static RadiusPacket start(Session session) throws IOException {
        DatagramChannel channel = channel(session.getRadiusAddress(), 1813);
        AccountingRequest req = accounting(session);
        req.add(new AcctStatusType(AcctStatusType.Type.Start));
        return packet(channel, req.getPayload());
    }

    static RadiusPacket login(Session session) throws IOException {
        DatagramChannel channel = channel(session.getRadiusAddress(), 1812);
        byte[] requestAuthenticator = new byte[16];
        AccessRequest req = new AccessRequest(session.getRadiusSecret(), requestAuthenticator);
        req.add(new UserName(session.getUsername()));
        req.add(new UserPassword(session.getPassword(), session.getRadiusSecret(), requestAuthenticator));
        req.add(new NASIPAddress(InetAddress.getByName(session.getNasIp()).getAddress()));
        req.add(new CallingStationID(session.getMacAddress()));
        req.add(new CiscoVSA("audit-session-id=" + session.getAuditId()));
        if (session.getFramedIp() != null && session.getFramedIpMask() != null) {
            req.add(new FramedIPAddress(InetAddress.getByName(session.getFramedIp()).getAddress()));
            req.add(new FramedIPNetmask(InetAddress.getByName(session.getFramedIpMask()).getAddress()));
        }
        if (session.getNasPort() != null) {
            req.add(new NASPort(session.getNasPort()));
        }
        if (session.getNasPortId() != null) {
            req.add(new NASPortID(session.getNasPortId()));
        }
        if (session.getNasPortType() != null) {
            req.add(new NASPortType(session.getNasPortType()));
        }
        if (session.getFramedIpv6() != null) {
            String[] addresses = session.getFramedIpv6().split(",");
            for (String ipv6 : addresses) {
                req.add(new FramedIPv6Address(InetAddress.getByName(ipv6).getAddress()));
            }
        }
        if (session.getNasIpv6() != null) {
            req.add(new NASIPv6Address(InetAddress.getByName(session.getNasIpv6()).getAddress()));
        }
        return packet(channel, req.getPayload());
    }
}
