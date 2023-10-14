package br.com.smartroll.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/presences")
@Tag(name = "presence-controller", description = "Controller responsável por requisições de presenças")
public class PresenceController {
}
