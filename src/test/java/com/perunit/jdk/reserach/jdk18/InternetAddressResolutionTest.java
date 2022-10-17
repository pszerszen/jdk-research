package com.perunit.jdk.reserach.jdk18;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetAddress;
import java.util.Arrays;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class InternetAddressResolutionTest {

    @SneakyThrows
    @Test
    void resolveGoogle() {
        var addresses = InetAddress.getAllByName("www.google.com");
        assertThat(addresses).hasSizeGreaterThan(0);

        Arrays.stream(addresses)
            .peek(inetAddress -> log.info("{}", inetAddress))
            .map(InetAddress::getHostName)
            .forEach(hostName -> assertThat(hostName).isEqualTo("www.google.com"));
    }
}
