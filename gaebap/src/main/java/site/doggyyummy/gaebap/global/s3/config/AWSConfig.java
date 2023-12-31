package site.doggyyummy.gaebap.global.s3.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application-aws-credentials.properties")
public class AWSConfig {
    private final Environment env;

    @Autowired
    public AWSConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public AmazonS3 awsS3Client() {
        Regions regions = Regions.AP_NORTHEAST_2;

        String accessKey =env.getProperty("aws.s3.accessKey");

        String secretAccessKey = env.getProperty("aws.s3.secretKey");

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretAccessKey);

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(regions)
                .build();
    }


}
