package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaver {

	@Autowired
	private HttpServletRequest request;

	public String  write(String basefolder,MultipartFile file) {
		try {
			String realPath = request.getServletContext().getRealPath("/"+basefolder);
			String pathname = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(pathname));
			
			/*Essa String salva somente a pasta e o nome do arquivo com sua extensão para serem salvos no banco
			eliminando o caminho completo que é o mesmo e desnecessário ficar salvando no banco ele.
			Isso aconteceria caso o retorno do metodo fosse o pathname*/
			String justFolderFileName = basefolder + "/" + file.getOriginalFilename();
			 
			return justFolderFileName;
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
 		} 
	}
}
