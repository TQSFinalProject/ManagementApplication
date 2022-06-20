package com.tqs.trackit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tqs.trackit.config.TokenProvider;
import com.tqs.trackit.dtos.AuthTokenDTO;
import com.tqs.trackit.dtos.LogInRequestDTO;
import com.tqs.trackit.dtos.RiderCreationDTO;
import com.tqs.trackit.dtos.StoreDTO;
import com.tqs.trackit.model.Store;
import com.tqs.trackit.service.AuthService;

@CrossOrigin("*")
@RestController
@RequestMapping
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private AuthService authServ;

    @PostMapping("/authentication")
    public ResponseEntity<?> generateToken(@RequestBody LogInRequestDTO loginrequest) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginrequest.getUsername(),
                        loginrequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthTokenDTO(token));
    }

    @PostMapping("/registration/store")
    public ResponseEntity<String> saveStore(@RequestBody StoreDTO store){
        Boolean bool = authServ.getStoreByUsername(store.getUsername()) != null;
        if(bool) return ResponseEntity.status(409).body("Username already in use.");
        else {
            Store storeEntity = store.toStoreEntity();
            authServ.saveStore(storeEntity);
            return ResponseEntity.status(200).body("User registered successfully.");
        }
    }

    @PostMapping("/registration/rider")
    public ResponseEntity<String> saveRider(@RequestBody RiderCreationDTO rider){
        Boolean bool = authServ.getRiderByUsername(rider.getUsername()) != null;
        if(bool) return ResponseEntity.status(409).body("Username already in use.");
        else {
            authServ.saveRider(rider.toRiderEntity());
            return ResponseEntity.status(200).body("User registered successfully.");
        }
    }

    // @GetMapping("/myprofile/store")
    // public ResponseEntity<CustomerDTO> getCustomerDetails(@RequestHeader("authorization") String auth) {
    //     String token = auth.split(" ")[1];
    //     String username = jwtTokenUtil.getUsernameFromToken(token);
    //     Customer cust = authServ.getCustomerByUsername(username);
    //     CustomerDTO response = CustomerDTO.fromCustomerEntity(cust);
    //     return ResponseEntity.ok().body(response);
    // }

    // @GetMapping("/myprofile/rider")
    // public ResponseEntity<CustomerDTO> getCustomerDetails(@RequestHeader("authorization") String auth) {
    //     String token = auth.split(" ")[1];
    //     String username = jwtTokenUtil.getUsernameFromToken(token);
    //     Customer cust = authServ.getCustomerByUsername(username);
    //     CustomerDTO response = CustomerDTO.fromCustomerEntity(cust);
    //     return ResponseEntity.ok().body(response);
    // }
}
