import styled from "styled-components";
import COLORS from "../../colors";

export const Container = styled.div`
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  flex-direction: column;
  margin-bottom: 24px;

  p {
    font-size: 14px;
    font-weight: 500;
    color: ${COLORS.Gray2};
    margin-bottom: 4px;
  }
`;

export const Input = styled.input`
  outline: none;
  width: 300px;
  padding: 12px 16px;
  background: #fcfcfc;
  border: 1px solid #dbdbdb;
  border-radius: 5px;
  font-family: Inter;
  font-weight: 500;
  color: ${COLORS.Black};

  ::placeholder {
    font-weight: 400;
    font-size: 14px;
    line-height: 100%;
    color: ${COLORS.Gray};
  }
`;
