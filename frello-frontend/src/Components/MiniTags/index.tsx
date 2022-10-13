import React from "react";

import { Container } from "./styles";

interface Tags {
  name: string;
}

const MiniTags: React.FC<Tags> = ({ name }) => {
  return <Container>{name}</Container>;
};

export default MiniTags;
