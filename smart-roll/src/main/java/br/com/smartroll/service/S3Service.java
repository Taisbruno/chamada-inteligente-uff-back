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
        Dotenv dotenv = Dotenv.load();
        String awsAccessKey = dotenv.get("AWS_ACCESS_KEY_ID");
        String awsSecretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");
        String awsRegion = dotenv.get("AWS_REGION");

        this.bucketName = dotenv.get("AWS_S3_BUCKET");

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        this.s3client = AmazonS3ClientBuilder.standard()
                            .withRegion(Regions.fromName(awsRegion))
                            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                            .build();
    }

    public String uploadBase64File(String base64File, String fileName) throws IOException {
        byte[] fileBytes = Base64.getDecoder().decode(base64File);
        InputStream fileInputStream = new ByteArrayInputStream(fileBytes);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileBytes.length);
        metadata.setContentType("application/pdf"); // Supondo que seja PDF

        s3client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, fileInputStream, metadata));
        fileInputStream.close();

        return s3client.getUrl(BUCKET_NAME, fileName).toString();
    }
}