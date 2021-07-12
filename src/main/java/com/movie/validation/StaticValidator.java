package com.movie.validation;

import com.movie.dto.UpdateActorRequest;
import com.movie.dto.UpdateCategoryRequest;
import com.movie.dto.UpdateMediaRequest;
import com.movie.entity.Actor;
import com.movie.entity.Category;
import com.movie.entity.Media;
import com.movie.repo.ActorRepository;
import com.movie.repo.CategoryRepository;
import com.movie.repo.MediaRepository;
import com.movie.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class StaticValidator {


    private static CategoryRepository categoryRepository;
    private static ActorRepository actorRepository;
    private static MediaRepository mediaRepository;

    @Autowired
    public void setMediaRepository(MediaRepository mediaRepository) {
        StaticValidator.mediaRepository = mediaRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        StaticValidator.categoryRepository=categoryRepository;
    }

    @Autowired
    public void setActorRepository(ActorRepository actorRepository) {
        StaticValidator.actorRepository = actorRepository;
    }

    public static HashMap<String,String> updateCategory(UpdateCategoryRequest request){
        HashMap<String,String> error=new HashMap<>();
        if (request.getName().equals(categoryRepository.findById(request.getId()).orElse(new Category(null,null)).getName()))
             error.put("name","Kategoride bir güncelleme yapılmadı!");
        else if(categoryRepository.findByName(request.getName()).orElse(null)!=null)
            error.put("name","Bu kategori kayıtlı!");
        return error;
    }

    public static HashMap<String,String> updateActor(UpdateActorRequest request){
        HashMap<String,String> error=new HashMap<>();
        if (request.getName().equals(actorRepository.findById(request.getId()).orElse(new Actor(null,null)).getName()))
            error.put("name","Aktörde bir güncelleme yapılmadı!");
        else if(actorRepository.findByName(request.getName()).orElse(null)!=null)
            error.put("name","Bu aktor kayıtlı!");
        return error;
    }

    public static  HashMap<String,String> updateMedia(UpdateMediaRequest request){
        HashMap<String,String> error=new HashMap<>();
        if (request.getName().equals(mediaRepository.findById(request.getId()).orElse(new Media(null,null)).getName()))
            error.put("name","Medyada bir güncelleme yapılmadı!");
        else if(mediaRepository.findByName(request.getName()).orElse(null)!=null)
            error.put("name","Bu medya kayıtlı!");
        return error;
    }

}
