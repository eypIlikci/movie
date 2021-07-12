package com.movie.service;

import com.movie.dto.CreateMediaRequest;
import com.movie.dto.MediaResponse;
import com.movie.dto.UpdateMediaRequest;
import com.movie.entity.Media;
import com.movie.repo.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaService {
    private MediaRepository mediaRepository;
    @Autowired
    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public MediaResponse save(CreateMediaRequest request){
        Media media=new Media();
        media.setName(request.getName());
        return MediaResponse.getInstance(mediaRepository.save(media));
    }
    public List<MediaResponse> getAll(){
        return mediaRepository.findAll().stream()
                .map(media->new MediaResponse(media.getId(),media.getName()))
                .collect(Collectors.toList());
    }

    public Page<MediaResponse> getByPageable(Pageable pageable){
        return mediaRepository.findAll(pageable)
                .map(m->new MediaResponse(m.getId(),m.getName()));
    }

    public Page<MediaResponse> getByKeyword(String keyword, Pageable pageable){
        return mediaRepository.findByKeyword(keyword,pageable)
                .map(media -> {
                    return new MediaResponse(media.getId(),media.getName());
                  });
    }

    public MediaResponse getById(Long id){
        Media media=mediaRepository.findById(id).orElse(null);
        if (media!=null)
            return new MediaResponse(media.getId(),media.getName());
        return null;
    }

    public void update(UpdateMediaRequest request){
        mediaRepository.save(new Media(request.getId(),request.getName()));
    }

    public void delete(Long id){
        Media media=mediaRepository.findById(id).orElse(null);
        if (media!=null)
            mediaRepository.delete(media);
    }




}
