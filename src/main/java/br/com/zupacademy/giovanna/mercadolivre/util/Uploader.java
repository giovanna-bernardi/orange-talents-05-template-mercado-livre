package br.com.zupacademy.giovanna.mercadolivre.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface Uploader {

    /**
     *
     * @param imagens
     * @return urls para as imagens enviadas
     */
    Set<String> upload(Set<MultipartFile> imagens);
}
