package example.com.dealsapp;

import java.io.Serializable;

/**
 * Created by MONISHA on 10-12-2017.
 */

public class Deal implements Serializable {
    String dealName, dealDuration;
    Long cost;
    Double lat, lng;
    Boolean dealFlag = false;

    public Boolean getDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(Boolean dealFlag) {
        this.dealFlag = dealFlag;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealDuration() {
        return dealDuration;
    }

    public void setDealDuration(String dealDuration) {
        this.dealDuration = dealDuration;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "dealName='" + dealName + '\'' +
                ", dealDuration='" + dealDuration + '\'' +
                ", cost=" + cost +
                ", lat=" + lat +
                ", lng=" + lng +
                ", dealFlag=" + dealFlag +
                '}';
    }
}
