import React from "react";
import MiniTags from "../MiniTags";

//styles
import { Container, Description, Price, TagContainer, Title } from "./styles";

interface Projeto {
  active?: boolean;
  price: string;
  title: string;
  description: string;
  tags: string[];
}

const Projeto: React.FC<Projeto> = ({
  active,
  price,
  title,
  description,
  tags,
}) => {
  return (
    <Container active={active}>
      <Price>R$ {price}</Price>
      <Title>{title}</Title>
      <Description>{description}</Description>
      <TagContainer>
        {tags.map((item: string) => (
          <MiniTags name={item} key={item} />
        ))}
      </TagContainer>
    </Container>
  );
};

export default Projeto;
