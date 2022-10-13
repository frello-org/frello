import styled from "styled-components";
import COLORS from "../../colors";

export const Container = styled.div`
  //text
  font-size: 12px;
  font-weight: 400;
  color: ${COLORS.Gray2};
  font-family: Poppins;

  // container
  padding: 4px 8px;
  display: flex;
  align-items: center;
  justify-content: center;

  border: 1px solid #d0ebdf;
  border-radius: 4px;

  margin-right: 8px;

  cursor: pointer;
  transition: .2s;

  &:hover {
    opacity: .8;
  }
`;
