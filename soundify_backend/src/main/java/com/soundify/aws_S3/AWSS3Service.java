package com.soundify.aws_S3;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Service
public class AWSS3Service {

	private final AmazonS3 amazonS3;

	@Autowired
	public AWSS3Service(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	public String generatePreSignedUrl(String filePath, String bucketName, HttpMethod httpMethod) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 10); // validity of 10 minutes
		return amazonS3.generatePresignedUrl(bucketName, filePath, calendar.getTime(), httpMethod).toString();
	}

	public void getSongFileNames() {
		ListObjectsV2Result result = amazonS3.listObjectsV2("soundifymusicbucket");

		List<S3ObjectSummary> objects = result.getObjectSummaries();

		objects.stream().forEach(s3objectSummary -> {
			System.out.println(s3objectSummary.toString());
		});
	}

}
