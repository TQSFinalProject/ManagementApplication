package com.tqs.trackit.service;

import java.time.LocalDateTime;
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
import com.tqs.trackit.model.Store;
import com.tqs.trackit.repository.OrderRepository;
import com.tqs.trackit.repository.RiderRepository;
import com.tqs.trackit.repository.StoreRepository;

@Service
public class OrdersService {

    @Autowired
    OrderRepository orderRep;

    @Autowired
    StoreRepository storeRep;

    @Autowired
    RiderRepository riderRep;

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
            System.out.println("ABC "+ o.getStoreId());
            Store store = storeRep.findById(o.getStoreId()).get();
            Map<String,Object> map = new HashMap<>();
            map.put("order", o);
            map.put("distance", distanceFromRiderToStoreDouble(store, rider) + distanceFromStoreToOrderDouble(o, store));
            ret.add(map);
        }

        ret.sort((Map<String,Object> o1, Map<String,Object> o2) -> ((Double) o1.get("distance") > (Double) o2.get("distance") ? 1 : -1));
         
        if(limit != null) if(ret.size() > limit) ret = ret.subList(0, ret.size()-limit+1);

        return ret;
    }

    public Order riderAcceptOrder(Rider rider, Long orderid) throws IllegalAccessException {
        // Is rider free
        List<Order> riderOrders = orderRep.findByRiderId(rider.getId());
        for(Order o : riderOrders) if(!o.getOrderStatus().equals("completed")) throw new IllegalAccessException();

        Order order = orderRep.findById(orderid).get();
        order.setRiderId(rider.getId());
        order.setOrderStatus("accepted");

        Store store = storeRep.findById(order.getStoreId()).get();
        
        Double distance = distanceFromRiderToStoreDouble(store, rider) + distanceFromStoreToOrderDouble(order, store);
        int minutes = (int) (distance * 4 + 4);

        order.setEstimatedDeliveryTime(LocalDateTime.now().plusMinutes(minutes));
        
        return orderRep.save(order);
    }

    public Order riderDeliveringOrder(Rider rider, Long orderid) throws IllegalAccessException {
        Order order = orderRep.findById(orderid).get();
        System.out.println("ABC "+order.getRiderId()+" "+rider.getId());
        if(order.getRiderId() != rider.getId()) throw new IllegalAccessException();
        order.setOrderStatus("delivering");

        return orderRep.save(order);
    }

    public Order riderCompleteOrder(Rider rider, Long orderid) throws IllegalAccessException {
        Order order = orderRep.findById(orderid).get();
        if(order.getRiderId() != rider.getId()) throw new IllegalAccessException();
        order.setOrderStatus("completed");
        order.setDeliveryTime(LocalDateTime.now());

        return orderRep.save(order);
    }

    public Order newOrderFromStore(Order order, Store store) {
        order.setStoreId(store.getId());
        order.setOrderStatus("requested");
        return orderRep.save(order);
    }

    public Map<String, Object> storeGetsOrderAndRider(Store store, Long orderid) throws IllegalAccessException {
        Order order = orderRep.findById(orderid).get();
        if(order.getStoreId() != store.getId()) throw new IllegalAccessException();

        Rider rider = null;
        if(order.getRiderId() != null) {
            rider = riderRep.findById(order.getRiderId()).get();
            rider.setUser(null);
        }

        Map<String, Object> ret = new HashMap<>();
        ret.put("order", order);
        ret.put("rider", rider);
        return ret;
    }



    public Double distanceFromStoreToOrderDouble(Order order, Store store) {
        return distanceDouble(order.getDeliveryLat(), order.getDeliveryLong(), store.getStoreLat(), store.getStoreLong());
    }

    public Double distanceFromRiderToStoreDouble(Store store, Rider rider) {
        return distanceDouble(store.getStoreLat(), store.getStoreLong(), rider.getLatitude(), rider.getLongitude());
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
