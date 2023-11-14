package com.test.k3sdemo.controller;

import com.test.k3sdemo.service.K3sService_ns;
import com.test.k3sdemo.service.K3sService_pod;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class K3sController {
    @Autowired
    K3sService_ns k3sService_ns;
    @Autowired
    K3sService_pod k3sService_pod;

    @RequestMapping("/hellok3s")
    public String hellok3s() {
        for (int i = 0; i < 10; i++) {
            System.out.println("远程调试测试");
        }
        return "----this is my k3s demo----";
    }

    @GetMapping("/getPods/{namespace}")
    public void getPods(@PathVariable String namespace) {
        try {
            k3sService_pod.listPod(namespace);
        } catch (Exception e) {
            log.error("getPods failed" + e.getMessage(), e);
        }
    }

    @GetMapping("/createDeployment")
    public void createDeployment() {
        try {
            k3sService_ns.createDeployment("k3sdemo-deploy");
        } catch (Exception e) {
            log.error("createNamespacedDeployment failed" + e.getMessage(), e);
        }
    }
}
