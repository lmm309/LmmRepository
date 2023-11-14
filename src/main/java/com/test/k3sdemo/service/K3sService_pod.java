package com.test.k3sdemo.service;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1ContainerStatus;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class K3sService_pod {
    @Autowired
    ApiClient k3sClient;
    private String namespace = "liumin-system";
    private String labelName = "app=k3sdemo";

    public void listPod(String namespace) throws Exception {
        try {
            CoreV1Api api = new CoreV1Api(k3sClient);
            //打印所有的pod
            V1PodList list = api.listNamespacedPod(namespace, null, null, null, null, labelName, null, null, null, null);
            String pod_name = "";
            String pod_ip = "";
            String host_ip = "";
            String host_name = "";
            String which_namespace = "";
            String pod_status = "";
            DateTime pod_creatime = null;
            List<V1Container> containers = null;
            for (V1Pod pod : list.getItems()) {
                System.out.println("================容器信息=================");
                containers = pod.getSpec().getContainers();
                for (V1Container container : containers) {
                    System.out.println("容器名称：" + container.getName());
                }
                List<V1ContainerStatus> container_status = pod.getStatus().getContainerStatuses();
                for (V1ContainerStatus containerStatus : container_status) {
                    System.out.println("容器状态：" + containerStatus.getState());
                }
                //获取 pod name
                pod_name = pod.getMetadata().getName();
                //获取 pod ip
                pod_ip = pod.getStatus().getPodIP();
                host_name = pod.getSpec().getNodeName();
                //获取 host ip,pod所属节点的ip
                host_ip = pod.getStatus().getHostIP();
                pod_status = pod.getStatus().getPhase();
                //获取namespace名称
                which_namespace = pod.getMetadata().getNamespace();
                pod_creatime = pod.getMetadata().getCreationTimestamp();

                System.out.println("================pod信息=================");
                System.out.println(pod_name);
                System.out.println(pod_ip);
                System.out.println(host_ip);
                System.out.println(host_name);
                System.out.println(which_namespace);
                System.out.println(pod_status);
                System.out.println(pod_creatime);
            }
        } catch (Exception e) {
            log.error("getPods failed" + e.getMessage(), e);
            throw new Exception("getPods failed" + e.getMessage(), e);
        }
    }
}
