import styled from "styled-components";
import COLORS from "../../../colors";

export const Container = styled.div`
  max-width: 1170px;
  width: 100%;
  margin: 0px auto;

  @media (max-width: 1220px) {
    width: 100%;
    padding: 0px 24px;
  }
`;

export const PessoasContainer = styled.div`
  padding: 24px;
  background: #ffffff;
  border: 1px solid #ebebed;
  border-radius: 12px;
  margin-top: 24px;
  header {
    border-bottom: solid 1px #eeeeee;
    padding-bottom: 8px;

    h1 {
      color: ${COLORS.Black};
      font-size: 16px;
      font-weight: 700;
      margin-bottom: 4px;
    }

    p {
      font-size: 14px;
      color: ${COLORS.Gray2};
      font-weight: 500;
    }
  }
`;

export const PessoasList = styled.div`
  display: grid;
  width: 100%;
  grid-template-columns: repeat(4, 23%);
  justify-content: space-between;
  margin-top: 16px;

  @media (max-width: 1100px) {
    width: 100%;
    grid-template-columns: repeat(3, 32%);
  }

  @media (max-width: 850px) {
    width: 100%;
    grid-template-columns: repeat(2, 49%);
  }

  @media (max-width: 600px) {
    width: 100%;
    grid-template-columns: 100%;
  }
`;
