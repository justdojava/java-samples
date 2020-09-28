package com.thumbnails.service;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.geometry.Positions;

public interface IThumbnailsService {

    String changeSize(MultipartFile resource, int width, int height);

    String changeScale(MultipartFile resource, double scale);

    String watermark(MultipartFile resource, Positions p, MultipartFile shuiyin, float opacity);

    String rotate(MultipartFile resource, double rotate);

    String region(MultipartFile resource, Positions p, int width, int height);

}
