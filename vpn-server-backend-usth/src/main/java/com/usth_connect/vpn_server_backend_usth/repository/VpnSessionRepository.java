package com.usth_connect.vpn_server_backend_usth.repository;

import com.usth_connect.vpn_server_backend_usth.entity.vpn.VpnSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VpnSessionRepository extends JpaRepository<VpnSession, String> {

}
