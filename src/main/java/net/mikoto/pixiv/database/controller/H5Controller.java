package net.mikoto.pixiv.database.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mikoto
 * Create for pixiv-database
 * Create at 2022/8/17
 */
@Controller
public class H5Controller {
    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }
}
