package br.com.zupacademy.giovanna.mercadolivre.util.imagem;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary // preferência na ordem de injeção quando for passada a interface
public class UploaderFake implements Uploader{
    @Override
    public Set<String> upload(Set<MultipartFile> imagens) {
        return imagens.stream()
                .map(imagem -> "https://bucket.io/" +
                        imagem.getOriginalFilename())
                .collect(Collectors.toSet());
    }
}
