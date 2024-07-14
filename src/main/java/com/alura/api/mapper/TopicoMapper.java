package com.alura.api.mapper;

import com.alura.api.model.topico.Topico;
import com.alura.api.model.topico.TopicoCreacionDTO;
import org.springframework.stereotype.Component;

@Component
public class TopicoMapper {

    public static Topico dtoToEntityTopico(TopicoCreacionDTO topicoCreacionDTO){
        return new Topico(topicoCreacionDTO.titulo(),topicoCreacionDTO.mensaje(),topicoCreacionDTO.autor(),topicoCreacionDTO.curso());
    }

}
