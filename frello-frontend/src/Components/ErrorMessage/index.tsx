import React from "react";

// import { Container } from './styles';

interface Error {
  msg: string;
}

const ErrorMessage: React.FC<Error> = ({ msg }) => {
  return (
    <div
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        background: "#FFD3D3",
        width: "100%",
        padding: 12,
        borderRadius: 8,
        fontSize: 14,
        color: "#DC3B3B",
        border: "solid 1px #FFB4B4",
        fontWeight: 500
      }}
    >
      {msg}
    </div>
  );
};

export default ErrorMessage;
