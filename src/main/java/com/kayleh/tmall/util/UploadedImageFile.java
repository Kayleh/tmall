package com.kayleh.tmall.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Wizard
 * @Date: 2020/5/1 15:05
 */

public class UploadedImageFile {
    MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

}