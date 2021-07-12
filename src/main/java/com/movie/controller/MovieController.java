package com.movie.controller;

import com.movie.dto.*;
import com.movie.service.ActorService;
import com.movie.service.CategorySevice;
import com.movie.service.MediaService;
import com.movie.service.MovieService;
import com.movie.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.stream.Collectors;
import javax.validation.Valid;

@Controller
public class MovieController{
    private static final int PAGE_SIZE=5;
    private MovieService movieService;
    private ActorService actorService;
    private CategorySevice categorySevice;
    private MediaService mediaService;

    @Autowired
    public MovieController(MovieService movieService,
                           ActorService actorService,
                           CategorySevice categorySevice,
                           MediaService mediaService) {
        this.movieService = movieService;
        this.actorService = actorService;
        this.categorySevice = categorySevice;
        this.mediaService = mediaService;
    }

    @GetMapping({"/","film","/index"})
    public String index(ModelMap modelMap,
                        @RequestParam(value = "pageNumber",defaultValue = "1") int pageNumber,
                        @RequestParam(value = "sort",defaultValue = "oldYear") String sort){


        if (sort.equals("newYear"))
            modelMap.addAttribute("page",
                    convertPage(movieService.getByPageable(PageRequest.of(pageNumber-1,PAGE_SIZE, Sort.by("year").descending()))));
        //defaul page
        else modelMap.addAttribute("page",
                convertPage(movieService.getByPageable(PageRequest.of(pageNumber-1,PAGE_SIZE, Sort.by("year").ascending()))));

        modelMap.addAttribute("title","Tüm Filmler");
        return "index";
    }

    @GetMapping("/film/kayit")
    public String saveForm(ModelMap modelMap){
        //Attribute
        modelMap.addAttribute("save","save");
        modelMap.addAttribute("movie",new CreateMovieRequest());
        modelMap.addAttribute("actors",actorService.getAll());
        modelMap.addAttribute("media",mediaService.getAll());
        modelMap.addAttribute("categories",categorySevice.getAll());

        //Page
        modelMap.addAttribute("title","Film Kayıt");
        modelMap.addAttribute("url","/film/kayit");
        return "movie-form";
    }

    @PostMapping("/film/kayit")
    public ModelAndView save(@Valid @ModelAttribute("movie") CreateMovieRequest request,
                             BindingResult result){
        ModelAndView modelAndView=new ModelAndView();
        if (result.hasErrors()){
            //Attribute
            modelAndView.addObject("save","save");
            modelAndView.addObject("movie",request);
            modelAndView.addObject("actors",actorService.getAll());
            modelAndView.addObject("media",mediaService.getAll());
            modelAndView.addObject("categories",categorySevice.getAll());

            //Page
            modelAndView.addObject("title","Film Kayıt");

            //Error
            result.getFieldErrors().stream()
                    .forEach(error ->modelAndView.addObject(error.getField(),error.getDefaultMessage()));

            //Return
            modelAndView.setViewName("movie-form");
            return modelAndView;
        }
        //Save
        Long id= movieService.save(request).getId();

        //Return
        modelAndView.setViewName("redirect:/film/detay/"+id);
        return modelAndView;
    }

    @GetMapping("/film/arama")
    public ModelAndView search(@RequestParam(value = "keyword",defaultValue = "Movie") String keyword,
                               @RequestParam(value = "pageNumber",defaultValue = "1")int pageNumber,
                               @RequestParam(value = "sort",defaultValue = "oldYear") String sort){

        ModelAndView modelAndView=new ModelAndView("index");
        if (sort.equals("newYear"))
        modelAndView.addObject("page",
                convertPage(movieService.getByKeyword(keyword,PageRequest.of(pageNumber-1,PAGE_SIZE,Sort.by("year").descending()))));
        else modelAndView.addObject("page",
                convertPage(movieService.getByKeyword(keyword,PageRequest.of(pageNumber-1,PAGE_SIZE,Sort.by("year").ascending()))));

        modelAndView.addObject("title",keyword+" Filmler");
        return modelAndView;
    }
    @GetMapping("/film/aktor/{name}/{id}")
    public ModelAndView getByActor(@PathVariable("id")Long id,
                                   @RequestParam(value = "pageNumber",defaultValue = "1")int pageNumber,
                                   @RequestParam(value = "sort",defaultValue = "oldYear") String sort){

        HashMap<String,Object> filter=new HashMap<String,Object>();
        filter.put("actor",id);
        ModelAndView modelAndView=new ModelAndView("index");
        modelAndView.addObject("title","Filmler");
        modelAndView.addObject("page",getPage(filter,pageNumber,sort));
        return modelAndView;
    }
    @GetMapping("/film/kategori/{name}/{catId}")
    public ModelAndView getByCategory(@PathVariable("catId")Long catId,
                                      @RequestParam(value = "pageNumber",defaultValue = "1")int pageNumber,
                                      @RequestParam(value = "sort",defaultValue = "oldYear") String sort){

        HashMap<String,Object> filter=new HashMap<String,Object>();
        filter.put("category",catId);
        ModelAndView modelAndView=new ModelAndView("index");
        modelAndView.addObject("title","Filmler");
        modelAndView.addObject("page",getPage(filter,pageNumber,sort));
        return modelAndView;
    }

    @GetMapping("/film/medya/{name}/{id}")
    public ModelAndView getByMedia(@PathVariable("id")Long id,
                                   @RequestParam(value = "pageNumber",defaultValue = "1")int pageNumber,
                                   @RequestParam(value = "sort",defaultValue = "oldYear") String sort){
        HashMap<String,Object> filter=new HashMap<String,Object>();
        filter.put("media",id);
        ModelAndView modelAndView=new ModelAndView("index");
        modelAndView.addObject("title","Filmler");
        modelAndView.addObject("page",getPage(filter,pageNumber,sort));
        return modelAndView;
    }

    @GetMapping("/film/guncelle/{id}")
    public String updateForm(ModelMap model,@PathVariable("id")Long id){
        MovieResponse response=movieService.getById(id);
        if (response==null)return "redirect:/film";
        model.addAttribute("title","Film Güncelle");
        model.addAttribute("actors",actorService.getAll());
        model.addAttribute("media",mediaService.getAll());
        model.addAttribute("categories",categorySevice.getAll());
        model.addAttribute("movie",response);
        return "movie-form";
    }

    @PostMapping("/film/guncelle")
    public ModelAndView update(@Valid @ModelAttribute("movie")UpdateMovieRequest request,
                               BindingResult result){
        ModelAndView modelAndView=new ModelAndView();
        if (result.hasErrors()){
            result.getFieldErrors().stream()
                    .forEach(error -> modelAndView.addObject(error.getField(),error.getDefaultMessage()));
            modelAndView.addObject("title","Film Güncelle");
            modelAndView.addObject("movie",request);
            modelAndView.addObject("actors",actorService.getAll());
            modelAndView.addObject("media",mediaService.getAll());
            modelAndView.addObject("categories",categorySevice.getAll());
            modelAndView.setViewName("movie-form");
            return modelAndView;
        }
        movieService.update(request);
        modelAndView.setViewName("redirect:/film/"+request.getName()+"/"+request.getId());
        return modelAndView;
    }

    @GetMapping("/film/{name}/{id}")
    public String getMovie(ModelMap modelMap,@PathVariable("id") Long id){
        MovieResponse movie=movieService.getById(id);
        if (movie==null)return "redirect:/film";
        modelMap.addAttribute("movie",movie);
        if (movie.getCategoryId()!=null){
            CategoryResponse cat=categorySevice.getById(movie.getCategoryId());
            if (cat!=null)modelMap.addAttribute("category",cat);
        }
        if (movie.getMediaId()!=null){
            MediaResponse m=mediaService.getById(movie.getMediaId());
            if (m!=null)modelMap.addAttribute("media",m);
        }
        if (movie.getActorsId()!=null){
            modelMap.addAttribute("actors",movie.getActorsId().stream()
                    .filter(aId->{
                        ActorResponse actorResponse=actorService.getById(aId);
                        if (actorResponse==null)return false;
                        return true;
                    })
                    .map(aId->actorService.getById(aId))
                    .collect(Collectors.toList()));
        }
        modelMap.addAttribute("title",movie.getName());
        return "movie-detail";
    }

    @GetMapping("/film/sil/{id}")
    public String delete(@PathVariable("id")Long id){
        movieService.delete(id);
        return "redirect:/film";
    }


    private Page<MovieResponse> getPage(HashMap<String,Object> filter,int pageNumber,String sort){
        Pageable pageable=PageRequest.of(pageNumber-1,PAGE_SIZE);
        if (sort.equals("oldYear")){
            pageable=PageRequest.of(pageNumber-1,PAGE_SIZE,Sort.by("year").ascending());
        }
        else if (sort.equals("newYear")){
            pageable=PageRequest.of(pageNumber-1,PAGE_SIZE,Sort.by("year").descending());
        }
        return convertPage(movieService.getByFilter(filter,pageable));
    }
    private Page<MovieResponse> convertPage(org.springframework.data.domain.Page<MovieResponse> page){
        return new Page<>(page.getNumber(),page.getTotalPages(),page.getTotalElements(),page.getContent());
    }

}
