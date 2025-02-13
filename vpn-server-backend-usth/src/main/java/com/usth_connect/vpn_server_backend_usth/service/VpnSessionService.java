package com.usth_connect.vpn_server_backend_usth.service;

import com.usth_connect.vpn_server_backend_usth.entity.vpn.VpnSession;
import com.usth_connect.vpn_server_backend_usth.repository.VpnSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VpnSessionService {
    @Autowired
    private VpnSessionRepository vpnSessionRepository;

    public VpnSession saveVpnSession(VpnSession vpnSession) {
        return vpnSessionRepository.save(vpnSession);
    }

    public List<VpnSession> getAllVpnSessions() {
        return vpnSessionRepository.findAll();
    }

    public Optional<VpnSession> getVpnSessionById(String id) {
        return vpnSessionRepository.findById(id);
    }

    public void deleteVpnSession(String id) {
        vpnSessionRepository.deleteById(id);
    }
}
