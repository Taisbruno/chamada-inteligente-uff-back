package br.com.smartroll.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/class-subscription")
@Tag(name = "student-controller", description = "Controller responsável por requisições de presenças")
public class ClassSubscriptionController {
}
