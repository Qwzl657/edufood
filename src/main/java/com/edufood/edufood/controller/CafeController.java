package com.edufood.edufood.controller;

import com.edufood.edufood.entity.Cafe;
import com.edufood.edufood.entity.Dish;
import com.edufood.edufood.service.CafeService;
import com.edufood.edufood.service.CartService;
import com.edufood.edufood.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CafeController {

    private final CafeService cafeService;
    private final DishService dishService;
    private final CartService cartService;

    @GetMapping({"/", "/cafes"})
    public String cafes(@RequestParam(defaultValue = "") String search,
                        @RequestParam(defaultValue = "0") int page,
                        Authentication authentication,
                        Model model,
                        HttpServletRequest request) {
        Page<Cafe> cafePage = cafeService.getCafes(search, page);
        model.addAttribute("cafes", cafePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", cafePage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("cartCount", cartService.getTotalCount(request));
        model.addAttribute("username",
                authentication != null ? authentication.getName() : null);
        return "cafe/list";
    }

    @GetMapping("/cafes/{id}")
    public String cafeMenu(@PathVariable Long id,
                           @RequestParam(defaultValue = "0") int page,
                           Authentication authentication,
                           Model model,
                           HttpServletRequest request) {
        Cafe cafe = cafeService.getById(id);
        Page<Dish> dishPage = dishService.getDishesByCafe(id, page);
        model.addAttribute("cafe", cafe);
        model.addAttribute("dishes", dishPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", dishPage.getTotalPages());
        model.addAttribute("cartCount", cartService.getTotalCount(request));
        model.addAttribute("username",
                authentication != null ? authentication.getName() : null);
        return "cafe/menu";
    }
}
