package com.reacttutorial.awsimageupload.profile;

import com.reacttutorial.awsimageupload.buckets.BucketName;
import com.reacttutorial.awsimageupload.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {
    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<UserProfile> getUserProfiles(){
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileID, MultipartFile file) {
        if(file.isEmpty())
            throw new IllegalStateException("Can not upload an empty file [ "+ file.getSize()+ " ]");

        if(!(Arrays.asList(ContentType.IMAGE_GIF.getMimeType(), ContentType.IMAGE_PNG.getMimeType(),
                ContentType.IMAGE_JPEG.getMimeType()).contains(file.getContentType())))
            throw new IllegalStateException("File type is incompatible. Must be an image");

        UserProfile user = userProfileDataAccessService.getUserProfiles().stream()
                .filter(userProfile -> userProfile.getUserProfileID().equals(userProfileID))
                .findFirst().orElseThrow(() -> new IllegalStateException("User with profile ID " + userProfileID +
                        " not found"));

        Map<String,String> metadata = new HashMap<>();
        metadata.put("Content Type", file.getContentType());
        metadata.put("Content Type length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileID());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            user.setUserProfileImageLink(filename);
        }
        catch(IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] downloadUserProfileImage(UUID userProfileID) {
        UserProfile user = userProfileDataAccessService.getUserProfiles().stream()
                .filter(userProfile -> userProfile.getUserProfileID().equals(userProfileID))
                .findFirst().orElseThrow(() -> new IllegalStateException("User with profile ID " + userProfileID +
                        " not found"));
        String path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                user.getUserProfileID());
        return user.getUserProfileImageLink().map(key -> fileStore.download(path,key)).orElse (new byte[0]);
    }
}
