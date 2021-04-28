package com.reacttutorial.awsimageupload.buckets;

public enum BucketName {
    PROFILE_IMAGE ("reacttutorial-image-upload-123");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
