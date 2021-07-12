package com.movie.controller;

import com.movie.dto.CategoryResponse;
import com.movie.dto.CreateCategory;
import com.movie.dto.MovieResponse;
import com.movie.dto.UpdateCategoryRequest;
import com.movie.service.CategorySevice;
import com.movie.util.Page;
import com.movie.validation.StaticValidator;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class CategoryController {
    private CategorySevice categorySevice;
    private static final int PAGE_SIZE=5;

    @Autowired
    public CategoryController(CategorySevice categorySevice) {
        this.categorySevice = categorySevice;
    }

    @GetMapping("/kategori")
    public String getCategories(ModelMap modelMap,
                                @RequestParam(value = "pageNumber",defaultValue = "1")int pageNumber){
        org.springframework.data.domain.Page<CategoryResponse> p=categorySevice.getByPageable(PageRequest.of(pageNumber-1,PAGE_SIZE));
        Page<CategoryResponse> page=convertPage(p);
        modelMap.addAttribute("page",page);
        return "categories";
    }

    @GetMapping("/kategori/kayit")
    public String saveForm(ModelMap model){
        model.addAttribute("save","save");
        model.addAttribute("category",new CreateCategory());
        model.addAttribute("title","Kategori Kayıt");
        model.addAttribute("url","kategori/kayit");
        return "category-form";
    }

    @PostMapping("/kategori/kayit")
    public ModelAndView save(@Valid @ModelAttribute("category") CreateCategory request,
                             BindingResult result){
        ModelAndView model=new ModelAndView();
        if (result.hasErrors()){

            for (FieldError error:result.getFieldErrors()) {
                model.addObject(error.getField(),error.getDefaultMessage());
            }
            model.setViewName("category-form");
            model.addObject("title","Kategori Kayit");
            model.addObject("category",new CreateCategory());
            model.addObject("save","save");

            return model;
        }
        categorySevice.save(request);
        model.setViewName("redirect:/kategori");
        return model;
    }

    @GetMapping("/kategori/arama")
    public ModelAndView search(@RequestParam(value = "keyword",defaultValue = "Category") String keyword,
                               @RequestParam(value = "pageNumber",defaultValue = "1") int pageNumber){
        ModelAndView model=new ModelAndView("categories");
        org.springframework.data.domain.Page<CategoryResponse> p = categorySevice.getAllByKeyword(keyword, PageRequest.of(pageNumber-1,PAGE_SIZE));
        Page<CategoryResponse>  page=convertPage(p);
        model.addObject("page",page);
        return model;
    }

    @GetMapping("/kategori/guncelle/{id}")
    public ModelAndView updateForm(@PathVariable("id")Long id){
        ModelAndView modelAndView=new ModelAndView("category-form");
        CategoryResponse categoryResponse=categorySevice.getById(id);
        if (categoryResponse==null)modelAndView.setViewName("categories");
        modelAndView.addObject("category",
                new UpdateCategoryRequest(categoryResponse.getId(),categoryResponse.getName()));
        modelAndView.addObject("title","Kategori Güncelleme");
        return modelAndView;
    }

    @PostMapping("/kategori/guncelle")
    public ModelAndView update(@Valid @ModelAttribute("category") UpdateCategoryRequest request,
                        BindingResult result){
        ModelAndView model=new ModelAndView();
        //javax.validation erros
        if (result.hasErrors()){
            result.getFieldErrors()
                    .stream()
                    .forEach(error -> model.addObject(error.getField(),error.getDefaultMessage()));
            model.setViewName("category-form");
            return model;
        }
        //my validation errors
        HashMap<String,String> error= StaticValidator.updateCategory(request);
        if (!error.isEmpty()){
            error.entrySet().stream().forEach(e->model.addObject(e.getKey(),e.getValue()));
            model.setViewName("category-form");
            return model;
        }
        categorySevice.update(request);
        model.setViewName("redirect:/kategori");
        return model;
    }

    @GetMapping("/kategori/sil/{id}")
    public String delete(@PathVariable("id") Long id){
        categorySevice.delete(id);
        return "redirect:/kategori";
    }

    private Page<CategoryResponse> convertPage(org.springframework.data.domain.Page<CategoryResponse> page){
        return new Page<CategoryResponse>(page.getNumber(),page.getTotalPages(),page.getTotalElements(),page.getContent());
    }
}
