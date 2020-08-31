package com.coocaa.consul.consul.demo;

import com.google.common.base.Optional;
import com.google.common.net.HostAndPort;
import com.orbitz.consul.*;
import com.orbitz.consul.model.agent.ImmutableRegCheck;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.health.ServiceHealth;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

public class ConsulUtil {

    private static Consul consul = Consul.builder().withHostAndPort(HostAndPort.fromString("127.0.0.1:8500")).build();

    /**

     * 服务注册

     */
    public static void serviceRegister() {
        AgentClient agent = consul.agentClient();

        try {
            /**

             * 注意该注册接口：
             * 需要提供一个健康检查的服务URL，以及每隔多长时间访问一下该服务（这里是3s）

             */
            agent.register(8080, URI.create("http://localhost:8080/health").toURL(), 3, "tomcat", "tomcatID", "dev");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    /**

     * 服务获取

     *

     * @param serviceName

     */
    public static void findHealthyService(String serviceName) {
        HealthClient healthClient = consul.healthClient();
        List<ServiceHealth> serviceHealthList = healthClient.getHealthyServiceInstances(serviceName).getResponse();
        serviceHealthList.forEach((response) -> {
            System.out.println(response);
        });
    }

    /**

     * 存储KV

     */
    public static void storeKV(String key, String value) {
        KeyValueClient kvClient = consul.keyValueClient();
        kvClient.putValue(key, value);
    }

    /**

     * 根据key获取value

     */
    public static String getKV(String key) {
        KeyValueClient kvClient = consul.keyValueClient();
        Optional<String> value = kvClient.getValueAsString(key);
        if (value.isPresent()) {
            return value.get();
        }
        return "";
    }

    /**

     * 找出一致性的节点（应该是同一个DC中的所有server节点）

     */
    public static List<String> findRaftPeers() {
        StatusClient statusClient = consul.statusClient();
        return statusClient.getPeers();
    }

    /**

     * 获取leader

     */
    public static String findRaftLeader() {
        StatusClient statusClient = consul.statusClient();
        return statusClient.getLeader();
    }

    public static void main(String[] args) {
        AgentClient agentClient = consul.agentClient();
        agentClient.deregister("tomcatID");
    }
}
