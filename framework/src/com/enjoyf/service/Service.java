package com.enjoyf.service;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/8/3
 * Description:
 */
public class Service {
    private String serviceType;
    private String serviceName;

    public Service(String serviceType, String serviceName) {
        this.serviceType = serviceType;
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceId() {
        return serviceType + ":" + serviceName;
    }

    public static Service getServiceByString(String serviceString) {
        String[] serviceArray = serviceString.split(":");
        if (serviceArray.length != 2) {
            return new Service("UNKNOWN", "UNKNOWN");
        }
        return new Service(serviceArray[0], serviceArray[1]);
    }

    @Override
    public String toString() {
        return getServiceId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        if (serviceName != null ? !serviceName.equals(service.serviceName) : service.serviceName != null) return false;
        if (serviceType != null ? !serviceType.equals(service.serviceType) : service.serviceType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serviceType != null ? serviceType.hashCode() : 0;
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        return result;
    }
}
