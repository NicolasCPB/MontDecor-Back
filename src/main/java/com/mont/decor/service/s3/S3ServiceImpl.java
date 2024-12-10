package com.mont.decor.service.s3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class S3ServiceImpl implements S3Service{
	
	@Autowired
    private AmazonS3 s3client;
	
    @Value("${aws.bucketName}")
    private String BUCKET_NAME;
    
    @Override
    public String uploadFile(MultipartFile file) {
        String rand = UUID.randomUUID().toString();
        String nomeArquivo = file.getName() + "-" + rand;
        try {
            File arquivo = convertToFile(file);
            s3client.putObject(BUCKET_NAME, nomeArquivo, arquivo);
            arquivo.delete();
            return s3client.getUrl(BUCKET_NAME, nomeArquivo).toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem no S3", e);
        }
    }

    public File convertToFile(MultipartFile file) throws IOException {
        File arquivo = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(arquivo)) {
            fos.write(file.getBytes());
        }
        return arquivo;
    }
}
