package com.thumbnails.controller;

import com.thumbnails.service.IThumbnailsService;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class ThumbnailsController {
	@Resource
	private IThumbnailsService thumbnailsService;

	/**
	 * 指定大小缩放
	 * @param resource
	 * @param width
	 * @param height
	 * @return
	 */
	@GetMapping("/changeSize")
	public String changeSize(MultipartFile resource, int width, int height) {
		return thumbnailsService.changeSize(resource, width, height);
	}

	/**
	 * 指定比例缩放
	 * @param resource
	 * @param scale
	 * @return
	 */
	@GetMapping("/changeScale")
	public String changeScale(MultipartFile resource, double scale) {
		return thumbnailsService.changeScale(resource, scale);
	}

	/**
	 * 添加水印 watermark(位置,水印,透明度)
	 * @param resource
	 * @param p
	 * @param shuiyin
	 * @param opacity
	 * @return
	 */
	@GetMapping("/watermark")
	public String watermark(MultipartFile resource, Positions p, MultipartFile shuiyin, float opacity) {
		return thumbnailsService.watermark(resource, Positions.CENTER, shuiyin, opacity);
	}

	/**
	 * 图片旋转 rotate(度数),顺时针旋转
	 * @param resource
	 * @param rotate
	 * @return
	 */
	@GetMapping("/rotate")
	public String rotate(MultipartFile resource, double rotate) {
		return thumbnailsService.rotate(resource, rotate);
	}

	/**
	 * 图片裁剪
	 * @param resource
	 * @param p
	 * @param width
	 * @param height
	 * @return
	 */
	@GetMapping("/region")
	public String region(MultipartFile resource, Positions p, int width, int height) {
		return thumbnailsService.region(resource, Positions.CENTER, width, height);
	}
}
