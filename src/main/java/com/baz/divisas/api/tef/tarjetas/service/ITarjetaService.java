package com.baz.divisas.api.tef.tarjetas.service;

import com.baz.divisas.general.dto.ResponseApi;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Tarjetas Interface.
 */
@Service
public interface ITarjetaService {

    /**
     * Carga archivo Tarjetas.
     *
     * @param file the file
     * @return the response api
     */
    ResponseApi solicitudTarjetaMasiva(MultipartFile file);
}
