package io.github.arthursilvagbs.bytebank.ByteBank.mappers;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaFisicaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.PessoaFisica;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PessoaFisicaMapper {

    PessoaFisica toEntity(PessoaFisicaCreateDTO dto);

}
