package com.perunit.jdk.reserach.jdk18;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetAddress;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class InternetAddressResolutionTest {

    @SneakyThrows
    @Test
    void resolveGoogle() {
        var addresses = InetAddress.getAllByName("www.google.com");
        assertEquals(2, addresses.length);

        for (var address : addresses) {
            assertEquals("www.google.com", address.getHostName());
            log.info("{}", address);
        }
    }
}
