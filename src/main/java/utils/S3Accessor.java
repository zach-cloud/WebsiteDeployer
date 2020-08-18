package utils;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.util.IOUtils;

import java.io.*;

/**
 * Access class for Amazon S3.
 * Wrapper over the SDK.
 */
public class S3Accessor {

    private AmazonS3Client s3;
    private TransferManager tm;

    /**
     * Required constructor. Makes a new S3 Accessor with credentials.
     *
     * @param access    S3 Access Key
     * @param secret    S3 Secret Key
     */
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

    /**
     * Writes a file from disk to S3.
     *
     * @param bucket    Bucket name to write to
     * @param key       Key name to write to
     * @param file      File on disk
     */
    public void writeFile(String bucket, String key, File file) {
        if(!s3.doesBucketExistV2(bucket)) {
            s3.createBucket(bucket);
        }
        s3.putObject(
                new PutObjectRequest(bucket, key, file)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public void download(String bucket, String rootDestination) {
        if(!rootDestination.endsWith("\\")) {
            rootDestination = rootDestination + "\\";
        }
        ObjectListing objects = s3.listObjects(bucket);
        for(S3ObjectSummary summary : objects.getObjectSummaries()) {
            try {
                File file = new File(rootDestination + summary.getKey());
                if(file.exists()) {
                    file.delete();
                }
                file.mkdirs();
                file.delete();
                PrintWriter fileWriter = new PrintWriter(file);
                S3Object object = s3.getObject(bucket, summary.getKey());
                fileWriter.println(IOUtils.toString(object.getObjectContent()));
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Removes all files from a bucket.
     *
     * @param bucketName    Bucket name to delete from.
     */
    public void removeFiles(String bucketName) {
        ObjectListing objectListing = s3.listObjects(bucketName);
        for(S3ObjectSummary s3ObjectSummary: objectListing.getObjectSummaries()) {
            removeFile(bucketName, s3ObjectSummary.getKey());
        }
    }

    /**
     * Removes one file from S3.
     *
     * @param bucketName    Bucket name to delete from.
     * @param key           Key to delete from
     */
    public void removeFile(String bucketName, String key) {
        s3.deleteObject(bucketName, key);
    }

    /**
     * Determines if a bucket exists.
     *
     * @param bucketName    Bucket name
     * @return              True if exists, false if not.
     */
    public boolean bucketExists(String bucketName) {
        return s3.doesBucketExistV2(bucketName);
    }

    /**
     * Makes a new bucket
     *
     * @param bucketName    Bucket name to create
     */
    public void createBucket(String bucketName) {
        s3.createBucket(bucketName);
    }
}
