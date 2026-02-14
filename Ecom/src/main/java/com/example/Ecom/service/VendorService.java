package com.example.Ecom.service;

import com.example.Ecom.dto.OnboardVendorRequestDto;
import com.example.Ecom.dto.VendorResponse;
import com.example.Ecom.model.User;
import com.example.Ecom.model.Vendor;
import com.example.Ecom.model.type.RoleType;
import com.example.Ecom.repository.UserRepository;
import com.example.Ecom.repository.VendorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorService {
    public final UserRepository userRepository;
    public final ModelMapper modelMapper;
    private final VendorRepository vendorRepository;

    @Transactional
    public ResponseEntity<VendorResponse> onBoardNewVendor(OnboardVendorRequestDto onboardVendorRequestDto) {
        User user = userRepository.findById(onboardVendorRequestDto.getUserId()).orElseThrow();
        if(vendorRepository.existsById(onboardVendorRequestDto.getUserId())){
            throw new IllegalArgumentException("Already a vendor");
        }
        Vendor vendor = Vendor.builder().name(user.getUsername()).email(onboardVendorRequestDto.getEmail()).user(user).build();
        user.getRoles().add(RoleType.VENDOR);
        return ResponseEntity.ok().body( modelMapper.map(vendorRepository.save(vendor),VendorResponse.class));
    }
}
