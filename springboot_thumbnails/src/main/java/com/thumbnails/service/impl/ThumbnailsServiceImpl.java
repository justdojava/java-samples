package com.thumbnails.service.impl;

import com.thumbnails.service.IThumbnailsService;
import com.thumbnails.util.ThumbnailsUtil;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

@Service
public class ThumbnailsServiceImpl implements IThumbnailsService {

    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    String string = System.getProperty("user.dir");
    String outputDir = string + "/src/main/resources/images/" + String.valueOf(year) + String.valueOf(month)
            + String.valueOf(day);

    @Override
    public String changeSize(MultipartFile resource, int width, int height) {
        String filePath = saveFile(resource);
        String fileSuffix = resource.getOriginalFilename().substring(resource.getOriginalFilename().lastIndexOf("."));
        String tofile = filePath.substring(0, filePath.lastIndexOf("/") + 1) + UUID.randomUUID() + fileSuffix;
        ThumbnailsUtil.changeSize(filePath, width, height, tofile);
        return tofile;
    }

    @Override
    public String changeScale(MultipartFile resource, double scale) {
        String filePath = saveFile(resource);
        String fileSuffix = resource.getOriginalFilename().substring(resource.getOriginalFilename().lastIndexOf("."));
        String tofile = filePath.substring(0, filePath.lastIndexOf("/") + 1) + UUID.randomUUID() + fileSuffix;
        ThumbnailsUtil.changeScale(filePath, scale, tofile);
        return tofile;
    }

    @Override
    public String watermark(MultipartFile resource, Positions p, MultipartFile shuiyin, float opacity) {
        String filePath = saveFile(resource);
        String shui = saveFile(shuiyin);
        String fileSuffix = resource.getOriginalFilename().substring(resource.getOriginalFilename().lastIndexOf("."));
        String tofile = filePath.substring(0, filePath.lastIndexOf("/") + 1) + UUID.randomUUID() + fileSuffix;
        ThumbnailsUtil.watermark(filePath, p, shui, opacity, tofile);
        return tofile;
    }

    @Override
    public String rotate(MultipartFile resource, double rotate) {
        String filePath = saveFile(resource);
        String fileSuffix = resource.getOriginalFilename().substring(resource.getOriginalFilename().lastIndexOf("."));
        String tofile = filePath.substring(0, filePath.lastIndexOf("/") + 1) + UUID.randomUUID() + fileSuffix;
        ThumbnailsUtil.rotate(filePath, rotate, tofile);
        return tofile;
    }

    @Override
    public String region(MultipartFile resource, Positions p, int width, int height) {
        String filePath = saveFile(resource);
        String fileSuffix = resource.getOriginalFilename().substring(resource.getOriginalFilename().lastIndexOf("."));
        String tofile = filePath.substring(0, filePath.lastIndexOf("/") + 1) + UUID.randomUUID() + fileSuffix;
        ThumbnailsUtil.region(filePath, p, width, height, tofile);
        return tofile;
    }

    public String saveFile(MultipartFile file) {
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + fileSuffix;
        String tofile = outputDir + "/" + fileName;
        try {
            file.transferTo(new File(outputDir, fileName));
        } catch (IOException e) {
            return "文件上传发生错误";
        }
        return tofile;
    }
}
