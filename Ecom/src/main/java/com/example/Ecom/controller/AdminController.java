package com.example.Ecom.controller;


import com.example.Ecom.dto.OnboardVendorRequestDto;
import com.example.Ecom.dto.VendorResponse;
import com.example.Ecom.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final VendorService vendorService;

    @PostMapping("/onboardNewVendor")
    public ResponseEntity<VendorResponse> onBoardNewVendor(@RequestBody OnboardVendorRequestDto onboardVendorRequestDto){
        return vendorService.onBoardNewVendor(onboardVendorRequestDto);
    }

}
