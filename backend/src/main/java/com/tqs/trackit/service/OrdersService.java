package com.tqs.trackit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tqs.trackit.model.Order;
import com.tqs.trackit.model.Rider;
import com.tqs.trackit.repository.OrderRepository;

@Service
public class OrdersService {

    @Autowired
    OrderRepository orderRep;

    public Page<Order> getOrders(Integer page) {
        Pageable elements = PageRequest.of(page, 4);
        return orderRep.findAll(elements);
    }

    public Order getOrderById(Long id) {
        return orderRep.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        return orderRep.save(order);
    }

    public Page<Order> getOrdersByRiderId(Long riderId,Integer page) {
        Pageable elements = PageRequest.of(page, 4);
        return orderRep.findByRiderId(riderId,elements);
    }

    public Page<Order> getOrdersByStoreId(Long storeId,Integer page) {
        Pageable elements = PageRequest.of(page, 4);
        return orderRep.findByStoreId(storeId,elements);
    }

    public Page<Order> getOrdersByStatus(String status,Integer page) {
        Pageable elements = PageRequest.of(page, 4);
        return orderRep.findByOrderStatus(status,elements);
    }

    public void deleteOrder(Long orderId) {
        orderRep.deleteById(orderId);
    }

    public List<Map<String,Object>> getRiderOrders(Rider rider, Integer limit) {
        List<Order> orders = orderRep.findAll();

        List<Map<String,Object>> ret = new ArrayList<>();

        for(Order o : orders) {
            Map<String,Object> map = new HashMap<>();
            map.put("order", o);
            map.put("distance", distanceFromRiderToOrderDouble(o, rider));
            ret.add(map);
        }

        ret.sort((Map<String,Object> o1, Map<String,Object> o2) -> ((Double) o1.get("distance") > (Double) o2.get("distance") ? 1 : -1));
         
        if(limit != null) if(ret.size() > limit) ret = ret.subList(0, ret.size()-limit+1);

        return ret;
    }

    public Double distanceFromRiderToOrderDouble(Order order, Rider rider) {
        return distanceDouble(order.getDeliveryLat(), order.getDeliveryLong(), rider.getLatitude(), rider.getLongitude());
    }

    // Functions below adapted from https://dzone.com/articles/distance-calculation-using-3
    public Double distanceDouble(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return dist;
    }

    private Double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private Double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    
}
