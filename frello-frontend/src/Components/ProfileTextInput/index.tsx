import React, { useState } from "react";
import { Container, InputText, Label } from "./styles";

interface TextInput {
  label: string;
  value: string;
  setValue: any;
  placeholder: string;
  customStyle?: any;
  isPassword?: boolean;
}

const ProfileTextInput: React.FC<TextInput> = ({
  label,
  value,
  setValue,
  placeholder,
  customStyle,
  isPassword,
}) => {
  return (
    <Container style={customStyle}>
      <Label>{label}</Label>
      <InputText
        type={isPassword ? "password" : "text"}
        placeholder={placeholder}
        value={value}
        onChange={(e) => {
          setValue(e.target.value);
        }}
      />
    </Container>
  );
};

export default ProfileTextInput;
