import React from "react";

// icons
import { FiX } from "react-icons/fi";
import COLORS from "../../colors";

// styles

import { Container } from "./styles";

interface Tag {
  onRemove: any;
  name: string;
}

const TagAdded: React.FC<Tag> = ({ onRemove, name }) => {
  return (
    <Container onClick={() => onRemove(name)}>
      {name}
      <FiX
        size={14}
        color={COLORS.PrimaryDark}
        style={{
          marginLeft: 4,
        }}
      />
    </Container>
  );
};

export default TagAdded;
