package esilveyra.repos.unitvsintegration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class UnitVsIntegrationApplicationTests {
	DockerImageName localStackImage = DockerImageName.parse("localstack/localstack:1.16.13");

	@Rule
	public LocalStackContainer localstack = new LocalStackContainer(localStackImage).withServices(LocalStackContainer.Service.S3);
	@Test
	void contextLoads() {
		AmazonS3 s3 = AmazonS3ClientBuilder
				.standard()
				.withEndpointConfiguration(localstack.getEndpointConfiguration(LocalStackContainer.Service.S3))
				.withCredentials(localstack.getDefaultCredentialsProvider())
				.build();

		s3.createBucket("foo");
		s3.putObject("foo", "bar", "baz");
	}

}
