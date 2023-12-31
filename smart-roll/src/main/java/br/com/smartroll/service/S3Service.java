package br.com.smartroll.service;

import io.github.cdimascio.dotenv.Dotenv;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.amazonaws.services.s3.model.ObjectMetadata;


public class S3Service {

    private AmazonS3 s3client;
    private String bucketName;

    public S3Service() {
        String awsAccessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String awsSecretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String awsRegion = System.getenv("AWS_REGION");

        this.bucketName = System.getenv("AWS_S3_BUCKET");

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        this.s3client = AmazonS3ClientBuilder.standard()
                            .withRegion(Regions.fromName(awsRegion))
                            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                            .build();
    }

    public String uploadBase64File(String base64File, String fileName, String mimeType) throws IOException {
        byte[] fileBytes = Base64.getDecoder().decode(base64File);
        InputStream fileInputStream = new ByteArrayInputStream(fileBytes);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileBytes.length);
        metadata.setContentType(mimeType);

        s3client.putObject(new PutObjectRequest(bucketName, fileName, fileInputStream, metadata));
        fileInputStream.close();

        return s3client.getUrl(bucketName, fileName).toString();
    }
}