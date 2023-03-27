package br.com.leuxam.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.leuxam.config.FileStorageConfig;
import br.com.leuxam.exceptions.FileStorageException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		Path path = Paths.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath().normalize();
		
		this.fileStorageLocation = path;
		
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Não conseguiu criar o diretorio onde os arquivos serão armazenados", e);
		}
	}
	
	public String storeFile(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			if(filename.contains("..")) throw new FileStorageException("Arquivo está com caracteres errados! " + filename);
			
			Path targetLocation = this.fileStorageLocation.resolve(filename);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return filename;
		} catch (Exception e) {
			throw new FileStorageException("Não foi possivel criar o " + filename + ". Porfavor tente novamente!", e);
		}
	}
	
	
}
