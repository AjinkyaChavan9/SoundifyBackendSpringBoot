package com.soundify.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.soundify.aws_S3.AWSS3Config;




@RestController // =@Controller at cls level + @ResponseBody added over ret
@RequestMapping("/api/songs")
public class SongsController {
	@Value("${cloud.aws.credentials.access-key}")
    private String accessKeyId;

    @Value("${cloud.aws.credentials.secret-key}")
    private String accessKeySecret;

    @Value("${cloud.aws.region.static}")
    private String s3RegionName;
    
    @Value("${cloud.aws.bucketname}")
    private String s3BucketName;
	
	@Autowired
	AWSS3Config awsS3;
	
	
	
	
	
	@PostMapping(value = "/api/songs/aws",consumes = "multipart/form-data")
	public ResponseEntity<?> uploadSong(@RequestBody MultipartFile file) throws IOException{
		if(file != null) {
		ObjectMetadata obectMetadata = new ObjectMetadata();
		obectMetadata.setContentType(file.getContentType());
		awsS3.getAmazonS3Client().putObject(new PutObjectRequest(s3BucketName,file.getOriginalFilename(),file.getInputStream(),obectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Song uploaded");
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sorry coudn't upload your file");
	}
	


}

