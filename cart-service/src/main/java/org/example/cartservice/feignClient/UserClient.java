//package org.example.cartservice.feignClient;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.example.userservice.model.User;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@FeignClient(name = "user-service", url = "http://localhost:8083/")
//public interface UserClient {
//    @GetMapping("/users/get/{id}")
//    User getUserById(@PathVariable Long id);
//}
