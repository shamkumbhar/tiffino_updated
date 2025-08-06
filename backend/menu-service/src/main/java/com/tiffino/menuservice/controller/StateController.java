package com.tiffino.menuservice.controller;

import com.tiffino.menuservice.dto.StateDTO;
import com.tiffino.menuservice.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
//@CrossOrigin("http://13.200.6.63:4200")
public class StateController {
    private final StateService stateService;

    @GetMapping("/getall")
    public ResponseEntity<List<StateDTO>> getAll() {
        return ResponseEntity.ok(stateService.getAllStates());
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<List<StateDTO>> getByRegion(@PathVariable Long regionId) {
        return ResponseEntity.ok(stateService.getStatesByRegionId(regionId));
    }
}
