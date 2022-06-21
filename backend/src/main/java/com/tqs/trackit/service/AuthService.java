package com.tqs.trackit.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tqs.trackit.model.Rider;
import com.tqs.trackit.model.Store;
import com.tqs.trackit.model.User;
import com.tqs.trackit.repository.RiderRepository;
import com.tqs.trackit.repository.StoreRepository;
import com.tqs.trackit.repository.UserRepository;

@Service("userService")
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRep;

    @Autowired
    StoreRepository storeRep;

    @Autowired
    RiderRepository riderRep;

    private BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    public Store saveStore(Store store) {
        User user = store.getUser();

        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        store.setUser(user);

        // In case we need role authorization
        // Role role = roleService.findByName("USER");
        // Set<Role> roleSet = new HashSet<>();
        // roleSet.add(role);

        return storeRep.save(store);
    }

    public Rider saveRider(Rider rider) {
        User user = rider.getUser();

        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        rider.setUser(user);

        // In case we need role authorization
        // Role role = roleService.findByName("USER");
        // Set<Role> roleSet = new HashSet<>();
        // roleSet.add(role);

        return riderRep.save(rider);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User cust = userRep.findByUsername(username);
        if(cust == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        // In case we need role authorization
        // return new org.springframework.security.core.userdetails.User(cust.getUsername(), cust.getPassword(), getAuthority(user));
        
        return new org.springframework.security.core.userdetails.User(cust.getUsername(), cust.getPassword(), new ArrayList<>());
    }

    public Store getStoreByUsername(String username) {
        return storeRep.findByUser_Username(username);
    }

    public Rider getRiderByUsername(String username) {
        return riderRep.findByUser_Username(username);
    }

    public User getUserByUsername(String username) {
        return userRep.findByUsername(username);
    }

    public Object getStoreByUser(User user) {
        return storeRep.findByUser(user);
    }

    public Object getRiderByUser(User user) {
        return riderRep.findByUser(user);
    }
}
