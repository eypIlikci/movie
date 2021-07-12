package com.movie.service;

import com.movie.dto.CategoryResponse;
import com.movie.dto.CreateCategory;
import com.movie.dto.UpdateCategoryRequest;
import com.movie.entity.Category;
import com.movie.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategorySevice {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategorySevice(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void save(CreateCategory request){
        Category category=new Category();
        category.setName(request.getName());
        categoryRepository.save(category);
    }

    public void update(UpdateCategoryRequest request){
        categoryRepository.save(new Category(request.getId(),request.getName()));
    }

    public List<CategoryResponse> getAll(){
        return categoryRepository.findAll()
                 .stream()
                 .map(this::convertToDTO)
                 .collect(Collectors.toList());
    }

    public Page<CategoryResponse> getByPageable(Pageable pageable){
        return categoryRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public Page<CategoryResponse> getAllByKeyword(String keyword, Pageable pageable){
        return categoryRepository.findByKeyword(keyword,pageable)
                .map(this::convertToDTO);
    }


    public CategoryResponse getById(Long id){
        Category category=categoryRepository.findById(id).orElse(null);
        if (category!=null)
            return new CategoryResponse(category.getId(),category.getName());
        return null;
    }

    public void delete(Long id){
        Category category=categoryRepository.findById(id).orElse(null);
        if (category!=null)
            categoryRepository.delete(category);
    }


    private CategoryResponse convertToDTO(Category category){
        return new CategoryResponse(category.getId(),category.getName());
    }

}
