package com.ll.sb231127.domain.article.article.controller;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.domain.article.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "kwType", defaultValue = "title,body") List<String> kwTypes,
                       @RequestParam(defaultValue = "") String kw,
                       @RequestParam(defaultValue = "0") int page, Model model) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        // kwTypes를 맵 형태로 바꿔서, 타임리프의 폼 안에서 쉽게 쓸 수 있도록 한다.
        Map<String, Boolean> kwTypesMap = kwTypes
                .stream()
                .collect(Collectors.toMap(
                        kwType -> kwType,
                        kwType -> true
                ));

        Page<Article> itemsPage = articleService.search(kwTypes, kw, pageable);
        model.addAttribute("itemsPage", itemsPage);
        model.addAttribute("kwTypesMap", kwTypesMap);

        return "article/list";
    }
}
