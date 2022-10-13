import React from "react";
import MiniTags from "../MiniTags";

import {
  Container,
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
  onInteressado?: any;
  interessado?: boolean;
  author: string;
  title: string;
  description: string;
  price: string;
  tags: string[];
}

const ProjetoCompleto: React.FC<Projeto> = ({
  onInteressado,
  author,
  title,
  description,
  price,
  tags,
  interessado,
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
        }}
      >
        {!interessado && (
          <CTAInteressado onClick={onInteressado}>
            Estou interessado
          </CTAInteressado>
        )}
        {interessado && <CTADesmarcar>Não tenho mais interesse</CTADesmarcar>}
      </div>
    </Container>
  );
};

export default ProjetoCompleto;
