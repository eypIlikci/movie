package com.movie.validation;

import com.movie.repo.ActorRepository;
import com.movie.repo.CategoryRepository;
import com.movie.repo.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueFieldValidator implements ConstraintValidator<UniqueField,String> {

    private UniqueField field;
    private CategoryRepository categoryRepository;
    private MediaRepository mediaRepository;
    private ActorRepository actorRepository;

    @Autowired
    public UniqueFieldValidator(CategoryRepository categoryRepository,
                                MediaRepository mediaRepository,
                                ActorRepository actorRepository) {
        this.categoryRepository = categoryRepository;
        this.mediaRepository = mediaRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public void initialize(UniqueField field) {
        this.field=field;
        ConstraintValidator.super.initialize(field);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        System.out.println("------------------GİRDI");

        if (field.className()== ClassName.ACTOR)
            return actorField(s);
        if (field.className()== ClassName.MEDIA)
            return mediaField(s);
        if (field.className()== ClassName.CATEGORY)
            return categoryField(s);
        return false;
    }

    private boolean actorField(String name){
        if (actorRepository.findByName(name).orElse(null)==null)
            return true;
        return false;
    }
    private boolean mediaField(String name){
        if (mediaRepository.findByName(name).orElse(null)==null)
            return true;
        return false;
    }
    private boolean categoryField(String name){
        if (categoryRepository.findByName(name).orElse(null)==null)
            return true;
        return false;
    }
}
