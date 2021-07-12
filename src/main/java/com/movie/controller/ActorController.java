package com.movie.controller;

import com.movie.dto.ActorResponse;
import com.movie.dto.CreateActorRequest;
import com.movie.dto.UpdateActorRequest;
import com.movie.service.ActorService;
import com.movie.util.Page;
import com.movie.validation.StaticValidator;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;


@Controller
public class ActorController {
    private static final int PAGE_SIZE=5;
    private ActorService actorService;
    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/aktor/kayit")
    public String saveForm(ModelMap modelMap){
        modelMap.addAttribute("save","save");
        modelMap.addAttribute("actor",new CreateActorRequest());
        return "actor-form";
    }
    @PostMapping("/aktor/kayit")
    public ModelAndView save(@Valid @ModelAttribute("actor") CreateActorRequest request,
                             BindingResult result){
        ModelAndView modelAndView=new ModelAndView();
        if (result.hasErrors()){
            modelAndView.addObject("save","save");
            modelAndView.setViewName("actor-form");
            modelAndView.addObject("actor",request);
            result.getFieldErrors().stream().forEach(error->{
                modelAndView.addObject(error.getField(),error.getDefaultMessage());
            });
            return modelAndView;
        }
        actorService.save(request);
        modelAndView.setViewName("redirect:/aktor");
        return modelAndView;
    }


    @GetMapping("/aktor/guncelle/{id}")
    public String updateForm(ModelMap modelMap,
                             @PathVariable("id")Long id){
        ActorResponse response=actorService.getById(id);
        if (response==null)
            return "redirect:/aktor";
        modelMap.addAttribute("actor",actorService.getById(id));
        return "actor-form";
    }

    @PostMapping("/aktor/guncelle")
    public ModelAndView update(@Valid @ModelAttribute("actor") UpdateActorRequest request,
                               BindingResult result){
        ModelAndView model=new ModelAndView();
        //javax.validations error
        if (result.hasErrors()){
            result.getFieldErrors().stream()
                    .forEach(error -> model.addObject(error.getField(),error.getDefaultMessage()));
            model.setViewName("actor-form");
            return model;
        }
        //my validations error
        HashMap<String,String> error= StaticValidator.updateActor(request);
        if (!error.isEmpty()){
            error.entrySet().stream()
                    .forEach(e->model.addObject(e.getKey(),e.getValue()));
            model.setViewName("actor-form");
            return model;
        }

        actorService.update(request);
        model.setViewName("redirect:/aktor");
        return model;
    }

    @GetMapping("/aktor")
    public ModelAndView getAll(@RequestParam(value = "pageNumber",defaultValue = "1")int pageNumber){

        Page<ActorResponse> page=convertPage(actorService.getByPageable(PageRequest.of(pageNumber-1,PAGE_SIZE)));
        ModelAndView modelAndView=new ModelAndView("actors");
        modelAndView.addObject("page",page);
        return modelAndView;
    }

    @GetMapping("/aktor/arama")
    public ModelAndView search(@RequestParam(value = "keyword",defaultValue ="Actor") String keyword,
                               @RequestParam(value = "pageNumber",defaultValue = "1")int pageNumber){

        Page<ActorResponse> page=convertPage(actorService.getByKeyword(keyword,PageRequest.of(pageNumber-1,PAGE_SIZE)));
        ModelAndView modelAndView=new ModelAndView("actors");
        modelAndView.addObject("page",page);
        return modelAndView;
    }

    @GetMapping("/aktor/sil/{id}")
    public String delete(@PathVariable("id")Long id,ModelMap modelMap){
        actorService.delete(id);
        return "redirect:/aktor";
    }

    private Page<ActorResponse> convertPage(org.springframework.data.domain.Page<ActorResponse> page){
        return new Page<ActorResponse>(page.getNumber(),page.getTotalPages(),page.getTotalElements(),page.getContent());
    }
}
