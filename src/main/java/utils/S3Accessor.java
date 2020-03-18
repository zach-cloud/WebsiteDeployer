package utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class S3Accessor {

    private AmazonS3Client s3;
    private TransferManager tm;

    public S3Accessor(String access, String secret) {
        this.s3 = new AmazonS3Client(new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return access;
            }

            @Override
            public String getAWSSecretKey() {
                return secret;
            }
        });

        this.tm = new TransferManager(new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return access;
            }

            @Override
            public String getAWSSecretKey() {
                return secret;
            }
        });
    }

    public void writeFile(String bucket, String key, File file) {
        if(!s3.doesBucketExistV2(bucket)) {
            s3.createBucket(bucket);
        }
        s3.putObject(
                new PutObjectRequest(bucket, key, file)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public void removeFiles(String bucketName) {
        ObjectListing objectListing = s3.listObjects(bucketName);
        for(S3ObjectSummary s3ObjectSummary: objectListing.getObjectSummaries()) {
            removeFile(bucketName, s3ObjectSummary.getKey());
        }
    }

    public void removeFile(String bucketName, String key) {
        s3.deleteObject(bucketName, key);
    }

    public boolean bucketExists(String bucketName) {
        return s3.doesBucketExistV2(bucketName);
    }

    public void createBucket(String bucketName) {
        s3.createBucket(bucketName);
    }
}
