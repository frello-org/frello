import styled from "styled-components";
import COLORS from "../../colors";

export const Container = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  background-color: #fff;
`;

export const LeftSide = styled.div`
  height: 100vh;
  width: 40%;
  background-color: #e9f6f0;

  @media (max-width: 920px) {
    display: none;
  }
`;

export const RightSide = styled.div`
  height: 100vh;
  width: 60%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;

  @media (max-width: 920px) {
    width: 100%;
  }
`;

export const Form = styled.div`
  max-width: 300px;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  flex-direction: column;
  margin-top: -200px;
`;

export const Title = styled.h1`
  color: ${COLORS.Black};
  font-size: 24px;
  font-weight: 600;
  border-bottom: 8px;
  margin-top: 16px;
  font-family: "Poppins";
`;

export const FormContainer = styled.div`
  margin-top: 32px;
`;

export const Cta = styled.div`
  width: 300px;
  padding: 12px;

  background-color: ${COLORS.PrimaryDark};
  border-radius: 5px;

  display: flex;
  align-items: center;
  justify-content: center;
  transition: 0.2s;

  color: ${COLORS.Background};
  font-weight: 600;

  cursor: pointer;

  &:hover {
    opacity: 0.8;
  }
`;

export const CtaDisabled = styled.div`
  width: 300px;
  padding: 12px;

  background-color: ${COLORS.Gray};
  border-radius: 5px;

  display: flex;
  align-items: center;
  justify-content: center;
  transition: 0.2s;

  color: ${COLORS.Background};
  font-weight: 600;

  cursor: default;
`;

export const RegisterText = styled.p`
  font-size: 14px;
  color: ${COLORS.Gray2};
  font-weight: 500;
  transition: 0.2s;
  margin-top: 6px;
  margin-bottom: 16px;

  span {
    text-decoration: underline;
    cursor: pointer;
  }

  &:hover {
    opacity: 0.8;
  }
`;
