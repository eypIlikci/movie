package com.movie.validation;

import com.movie.entity.Actor;
import com.movie.entity.Category;
import com.movie.entity.Media;
import com.movie.repo.ActorRepository;
import com.movie.repo.CategoryRepository;
import com.movie.repo.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUpdateFieldValidator implements ConstraintValidator<UniqueUpdateField,String> {

    private  ClassName name;
    private long id;
    private CategoryRepository categoryRepository;
    private MediaRepository mediaRepository;
    private ActorRepository actorRepository;

    @Autowired
    public UniqueUpdateFieldValidator(CategoryRepository categoryRepository,
                                      MediaRepository mediaRepository,
                                      ActorRepository actorRepository) {
        this.categoryRepository = categoryRepository;
        this.mediaRepository = mediaRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public void initialize(UniqueUpdateField constraintAnnotation) {
        this.id=constraintAnnotation.id();
        this.name=constraintAnnotation.className();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }

    private boolean actorField(String name){

        Actor actor=actorRepository.getById(this.id);
            if (!actor.getName().equals(name) && actorRepository.findByName(name).orElse(null)!=null)
                return false;
        return true;
    }
    private boolean mediaField(String name){
        Media media=mediaRepository.getById(this.id);
        if (!media.getName().equals(name) && mediaRepository.findByName(name).orElse(null)!=null)
            return false;
        return true;
    }
    private boolean categoryField(String name){
        Category cat=categoryRepository.getById(this.id);
        if (!cat.getName().equals(name) && categoryRepository.findByName(name).orElse(null)!=null)
            return false;
        return true;
    }
}
