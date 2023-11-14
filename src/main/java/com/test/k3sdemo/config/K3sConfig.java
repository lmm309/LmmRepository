package com.test.k3sdemo.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;

@Configuration
public class K3sConfig {
    @Bean
    public static ApiClient K3sClient() throws IOException {
        /*String kubeConfigPath = "config";
        ApiClient k3sClient =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(k3sClient);*/

        ApiClient k3sClient = new ClientBuilder().setBasePath("172.21.21.19").setVerifyingSsl(false)
                .setAuthentication(new AccessTokenAuthentication("Token")).build();
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(k3sClient);
        return k3sClient;
    }
}
