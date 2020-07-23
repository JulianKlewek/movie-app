package pl.klewek.filmapp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryImageUploader {

    private static Map uploadResult = new HashMap();
    private Cloudinary cloudinary;

    public CloudinaryImageUploader() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "digfadfada",
                "api_key", "55235235257523",
                "api_secret", "PF$Vv48c2k2FS23"));
    }

    public String getCloudinaryImageUrl(){
        return uploadResult.get("url").toString();
    }

    public void saveImageToCloudinary(String imagePath) throws IOException {
        File file = new File(imagePath);
        uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
    }

}
