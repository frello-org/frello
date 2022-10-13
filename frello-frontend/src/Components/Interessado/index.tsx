import React from "react";
import MiniTags from "../MiniTags";

import { Container, TagList } from "./styles";

interface Interessado {
  name: string;
  phone: string;
  mail: string;
  tags: string[];
}

const Interessado: React.FC<Interessado> = ({ name, phone, mail, tags }) => {
  return (
    <Container>
      <h1>{name}</h1>
      <p>{phone}</p>
      <p>{mail}</p>
      <TagList>
        {tags.map((tagValue) => (
          <MiniTags name={tagValue} key={tagValue} />
        ))}
      </TagList>
    </Container>
  );
};

export default Interessado;
