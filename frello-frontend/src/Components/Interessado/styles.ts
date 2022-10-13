import styled from "styled-components";
import COLORS from "../../colors";

export const Container = styled.div`
  background: #ffffff;
  border: 1px solid #ebebed;
  border-radius: 3px;
  padding: 16px;
  margin-bottom: 24px;

  h1 {
    font-size: 16px;
    margin-bottom: 6px;
    color: ${COLORS.Black};
  }

  p {
    font-size: 14px;
    margin-bottom: 2px;
    color: ${COLORS.Gray2};
  }
`;

export const TagList = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  flex-wrap: wrap;
  margin-top: 16px;
`;
