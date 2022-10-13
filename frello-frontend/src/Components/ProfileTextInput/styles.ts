import styled from "styled-components";
import COLORS from "../../colors";

export const Container = styled.div`
  display: flex;
  align-items: flex-start;
  flex-direction: column;
  justify-content: flex-start;
  margin-top: 24px;
  width: 200px;

`;

export const Label = styled.p`
  font-size: 14px;
  font-weight: 600;
  color: ${COLORS.Black};
`;

export const InputText = styled.input`
  padding: 6px 8px;
  font-size: 14px;
  font-weight: 500;
  font-family: Inter;
  color: ${COLORS.Black4};
  outline: none;
  background: #ffffff;
  border: 1px solid #dbdbdb;
  border-radius: 5px;
  margin-top: 4px;


  ::placeholder {
    color: ${COLORS.Gray};
    opacity: 1;
    font-weight: 400;
  }
`;
