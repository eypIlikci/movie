package com.movie.controller;

import com.movie.dto.ActorResponse;
import com.movie.dto.CreateMediaRequest;
import com.movie.dto.MediaResponse;
import com.movie.dto.UpdateMediaRequest;
import com.movie.service.MediaService;
import com.movie.util.Page;
import com.movie.validation.StaticValidator;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
public class MediaController {
    private static final int PAGE_SIZE=5;
    private MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }


    @GetMapping("/medya")
    public String getMedia(ModelMap modelMap,
                           @RequestParam(value = "pageNumber",defaultValue = "1")int pageNumber){
        modelMap.addAttribute("page",convertPage(mediaService.getByPageable(PageRequest.of(pageNumber-1,PAGE_SIZE))));
        return "media";
    }

    @GetMapping("/medya/kayit")
    public ModelAndView saveForm(){
        ModelAndView modelAndView=new ModelAndView("media-form");
        modelAndView.addObject("save","save");
        modelAndView.addObject("title","Medya Kayıt");
        modelAndView.addObject("media",new CreateMediaRequest());
        return modelAndView;
    }

    @PostMapping("/medya/kayit")
    public ModelAndView save(@Valid @ModelAttribute("media") CreateMediaRequest request,
                             BindingResult result){
        ModelAndView modelAndView=new ModelAndView();

        if (result.hasErrors()){
            modelAndView.addObject("save","save");
            modelAndView.setViewName("media-form");
            modelAndView.addObject("media",request);
            result.getFieldErrors().stream().forEach(error->{
                modelAndView.addObject(error.getField(),error.getDefaultMessage());
            });
            return modelAndView;
        }
        mediaService.save(request);
        modelAndView.setViewName("redirect:/medya");
        return modelAndView;
    }

    @GetMapping("/medya/arama")
    public ModelAndView search(@RequestParam(value = "keyword",defaultValue = "Media") String keyword,
                               @RequestParam(value = "pageNumber",defaultValue = "1")int pageNumber){
        ModelAndView modelAndView=new ModelAndView("media");
        modelAndView.addObject("page",convertPage(mediaService
                .getByKeyword(keyword,PageRequest.of(pageNumber-1,PAGE_SIZE))));
        return modelAndView;
    }

    @GetMapping("/medya/guncelle/{id}")
    public String updateForm(ModelMap modelMap, @PathVariable("id") Long id){
        MediaResponse response=mediaService.getById(id);
        if (response==null)
            return "redirect:/medya";
        modelMap.addAttribute("media",response);
        return "media-form";
    }

    @PostMapping("/medya/guncelle")
    public ModelAndView update(@Valid @ModelAttribute("media")UpdateMediaRequest request,
                               BindingResult result){
        ModelAndView modelAndView = new ModelAndView();
        //javax.validations errors
        if (result.hasErrors()){
            result.getFieldErrors().stream()
                    .forEach(error -> modelAndView.addObject(error.getField(),error.getDefaultMessage()));
            modelAndView.setViewName("media-form");
            return modelAndView;
        }
        //my validations errors
        HashMap<String,String> error= StaticValidator.updateMedia(request);
        if (!error.isEmpty()){
            error.entrySet().stream()
                    .forEach(e->modelAndView.addObject(e.getKey(),e.getValue()));
            modelAndView.setViewName("media-form");
            return modelAndView;
        }
        mediaService.update(request);
        modelAndView.setViewName("redirect:/medya");
        return modelAndView;
    }

    @GetMapping("/medya/sil/{id}")
    public String delete(@PathVariable("id") Long id){
        mediaService.delete(id);
        return "redirect:/medya";
    }

    private Page<MediaResponse> convertPage(org.springframework.data.domain.Page<MediaResponse> page){
        return new Page<>(page.getNumber(),page.getTotalPages(),page.getTotalElements(),page.getContent());
    }

}
