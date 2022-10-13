import React from "react";

import { Container, Input } from "./styles";

interface Login {
  label: string;
  value: string;
  onChange: any;
  placeholder: string;
  isPassword?: boolean;
}

const LoginInput: React.FC<Login> = ({
  label,
  value,
  onChange,
  placeholder,
  isPassword,
}) => {
  return (
    <Container>
      <p>{label}</p>
      <Input
        type={isPassword ? "password" : "text"}
        placeholder={placeholder}
        value={value}
        onChange={(e) => {
          onChange(e.target.value);
        }}
      />
    </Container>
  );
};

export default LoginInput;
