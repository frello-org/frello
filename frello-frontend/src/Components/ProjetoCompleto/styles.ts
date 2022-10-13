import styled from "styled-components";
import COLORS from "../../colors";

export const Container = styled.div`
  padding: 24px 32px;
  background: #ffffff;
  border: 1px solid #ebebed;
  border-radius: 12px;
  width: 100%;
  margin-top: 24px;
`;

export const Nome = styled.h3`
  font-size: 14px;
  font-weight: 600;
  color: ${COLORS.Gray2};
  margin-bottom: 4px;
`;

export const Titulo = styled.h1`
  font-family: Poppins;
  font-weight: 600;
  font-size: 20px;
  color: ${COLORS.Black};
`;

export const InfoSeparator = styled.h6`
  font-family: "Inter";
  font-weight: 700;
  font-size: 14px;
  color: ${COLORS.Black};
  margin-top: 24px;
`;

export const Description = styled.div`
  font-family: "Inter";
  font-weight: 400;
  font-size: 16px;
  color: ${COLORS.Gray2};
  margin-top: 4px;
`;

export const TagsContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin-top: 4px;
`;

export const Valor = styled.h5`
  color: ${COLORS.Primary};
  font-family: "Poppins";
  font-weight: 600;
  font-size: 16px;
  margin-top: 4px;
`;

export const CTAInteressado = styled.div`
  padding: 10px 24px;
  font-size: 15px;
  font-weight: 600;
  color: #fff;
  font-family: Poppins;

  background: ${COLORS.PrimaryDark};
  border-radius: 8px;
  margin-top: 24px;

  // animation
  transition: 0.2s;
  cursor: pointer;

  &:hover {
    opacity: 0.8;
    border-radius: 10px;
  }
`;

export const CTADesmarcar = styled.div`
  padding: 10px 24px;
  font-size: 15px;
  font-weight: 600;
  color: ${COLORS.Primary};
  font-family: Poppins;

  border: 1px solid ${COLORS.PrimaryDark};

  background: #fff;
  border-radius: 8px;
  margin-top: 24px;

  // animation
  transition: 0.2s;
  cursor: pointer;

  &:hover {
    opacity: 0.7;
    border-radius: 10px;
  }
`;
