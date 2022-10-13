import React from "react";
import MiniTags from "../MiniTags";

import {
  Container,
  CTADeletar,
  CTADesmarcar,
  CTAInteressado,
  Description,
  InfoSeparator,
  Nome,
  TagsContainer,
  Titulo,
  Valor,
} from "./styles";

interface Projeto {
  interessado?: boolean;
  author: string;
  title: string;
  description: string;
  price: string;
  tags: string[];
}

const ProjetoEmpregador: React.FC<Projeto> = ({
  author,
  title,
  description,
  price,
  tags,
}) => {
  return (
    <Container>
      <Nome>{author}</Nome>
      <Titulo>{title}</Titulo>

      <InfoSeparator>Descrição do projeto</InfoSeparator>
      <Description>{description}</Description>

      <InfoSeparator>Habilidades necessarias</InfoSeparator>
      <TagsContainer>
        {tags.map((tag) => (
          <MiniTags name={tag} />
        ))}
      </TagsContainer>

      <InfoSeparator>Remuneração</InfoSeparator>
      <Valor>R$ {price}</Valor>

      <div
        style={{
          display: "flex",
          alignItems: "center",
        }}
      >
        <CTADesmarcar>Editar</CTADesmarcar>
        <CTADeletar>desativar</CTADeletar>
      </div>
    </Container>
  );
};

export default ProjetoEmpregador;
