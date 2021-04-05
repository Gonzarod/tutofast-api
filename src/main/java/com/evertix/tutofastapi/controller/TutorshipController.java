package com.evertix.tutofastapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@Tag(name = "Tutorship", description = "API is Ready")
@RequestMapping("api/tutorship")
@RestController
public class TutorshipController {
}
