package com.fuchuang.facerecognition.utils;

import com.arcsoft.face.toolkit.ImageInfo;
import com.arcsoft.face.toolkit.ImageInfoEx;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.Base64;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

@Component
public class ImageUtils {


    //将base64转为image
    public byte[] base64TransformImg(String base64){

        // 去掉base64前缀 data:image/jpeg;base64,
        base64 = base64.substring(base64.indexOf(",", 1) + 1);
        int xx = base64.indexOf('+');
        // 解密，解密的结果是一个byte数组
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] imgbytes = decoder.decode(base64);
        for (int i = 0; i < imgbytes.length; ++i) {
            if (imgbytes[i] < 0) {
                imgbytes[i] += 256;
            }
        }
        return  imgbytes;
    }


    public static ImageInfoMeta packImageInfoEx(File img){
        ImageInfo imageInfo = getRGBData(img);
        return packImageInfoMeta(imageInfo);
    }


    public static ImageInfoEx GetImageInfoEx(byte[] img){
        ImageInfo imageInfo = getRGBData(img);
        return packImageInfoEx(imageInfo);
    }

    public static ImageInfoEx packImageInfoEx (ImageInfo imageInfo){
        ImageInfoEx imageInfoEx = new ImageInfoEx();
        imageInfoEx.setHeight(imageInfo.getHeight());
        imageInfoEx.setWidth(imageInfo.getWidth());
        imageInfoEx.setImageFormat(imageInfo.getImageFormat());
        imageInfoEx.setImageDataPlanes(new byte[][]{imageInfo.getImageData()});
        imageInfoEx.setImageStrides(new int[]{imageInfo.getWidth() * 3});
        return imageInfoEx;
    }

    /**
     * 处理 byte[] 的图片流
     * @param img
     * @return
     */
    public static ImageInfoMeta packImageInfoMeta(byte[] img){
        ImageInfo imageInfo = getRGBData(img);
        return packImageInfoMeta(imageInfo);
    }

    /**
     * 处理 InpuStream 的图片流
     * @param img
     * @return
     */
    public static ImageInfoMeta packImageInfoMeta(InputStream img){
        ImageInfo imageInfo = getRGBData(img);
        return packImageInfoMeta(imageInfo);
    }


    /**
     * 打包生成 ImageInfoMeta
     * @param imageInfo
     * @return
     */
    private static ImageInfoMeta packImageInfoMeta(ImageInfo imageInfo){
        ImageInfoMeta imageInfoMeta = new ImageInfoMeta(imageInfo);
        return imageInfoMeta;
    }

    /**
     * 对imageInfo 和 imageInfoEx 的打包对象
     //     * @param img
     * @return
     */
    @Data
    public static class ImageInfoMeta{
        private ImageInfo imageInfo;
        private ImageInfoEx imageInfoEx;

        public ImageInfoMeta(ImageInfo imageInfo) {
            this.imageInfo = imageInfo;
            imageInfoEx = new ImageInfoEx();
            imageInfoEx.setHeight(imageInfo.getHeight());
            imageInfoEx.setWidth(imageInfo.getWidth());
            imageInfoEx.setImageFormat(imageInfo.getImageFormat());
            imageInfoEx.setImageDataPlanes(new byte[][]{imageInfo.getImageData()});
            imageInfoEx.setImageStrides(new int[]{imageInfo.getWidth() * 3});
        }
    }

}

