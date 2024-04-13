package yswy.timesystem.backend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {
    @GetMapping("/docker")
    public String docker(){
        return "hello docker";
    }
}
