package com.test.k3sdemo.service;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class K3sService_ns {
    @Autowired
    ApiClient k3sClient;
    private String namespace = "liumin-system";
    private String labelName = "app=k3sdemo";


    /**
     * 创建命名空间
     *
     * @param namespace
     * @return
     * @throws Exception
     */
    public V1Namespace createnamespace(String namespace) throws Exception {
        CoreV1Api coreV1Api = new CoreV1Api(k3sClient);
        V1Namespace v1Namespace = new V1NamespaceBuilder()
                .withNewMetadata()
                .withName(namespace)
                .endMetadata()
                .build();
        V1Namespace ns = coreV1Api.createNamespace(v1Namespace, null, null, null);
        return ns;
    }

    /**
     * 创建容器部署
     *
     * @param deploymentName deployment名称
     * @throws IOException
     */
    public void createDeployment(String deploymentName) throws Exception {
        AppsV1Api apiInstance = new AppsV1Api(k3sClient);
        //设置标签
        Map<String, String> selectLabels = new HashMap<>(0);
        List<V1ContainerPort> containerPort = new ArrayList<V1ContainerPort>(0);
        //设置容器端口
        containerPort.add(new V1ContainerPort().containerPort(8080));
        selectLabels.put("appName", "k3sdemo");
        V1Deployment body = new V1DeploymentBuilder()
                .withMetadata(new V1ObjectMetaBuilder()
                        .withName(deploymentName)
                        .withNamespace(namespace)
                        .withLabels(selectLabels)
                        .build())
                .withSpec(new V1DeploymentSpecBuilder()
                        //设置默认副本数
                        .withReplicas(3)
                        //设置选择器
                        .withSelector(new V1LabelSelectorBuilder()
                                .withMatchLabels(selectLabels)
                                .build())
                        //设置容器模板
                        .withTemplate(new V1PodTemplateSpecBuilder()
                                .withMetadata(new V1ObjectMetaBuilder()
                                        .withLabels(selectLabels)
                                        .build())
                                .withSpec(new V1PodSpecBuilder()
                                        .withContainers(new V1ContainerBuilder()
                                                .withName("k3sdemoContainer")//设置容器名
                                                .withImage("harbor-repo.io/lm-test/k3sdemo:v1.0")//设置容器镜像名
                                                .withImagePullPolicy("IfNotPresent")//镜像本地拉去策略
                                                .withCommand("/bin/bash",
                                                        "echo",
                                                        "this is a k3sdemo")//命令
                                                .withPorts(containerPort)//容器端口
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build(); // V1Deployment |
        String pretty = null; // 是否会漂亮输出
        String dryRun = null; //
        String fieldManager = null; //
        try {
            V1Deployment result = apiInstance.createNamespacedDeployment(namespace, body, pretty, dryRun, fieldManager);//调用createNamespacedDeployment方法创建容器部署
            System.out.println(result);
        } catch (Exception e) {
            log.error("createDeployment failed" + e.getMessage(), e);
            throw new Exception("createDeployment failed" + e.getMessage(), e);
        }
    }
}
