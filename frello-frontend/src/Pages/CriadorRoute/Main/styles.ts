import styled from "styled-components";
import COLORS from "../../../colors";

export const Container = styled.div`
  max-width: 1170px;
  margin: 0px auto;
  margin-top: 32px;

  @media (max-width: 1220px) {
    width: 100%;
    padding: 0px 24px;
  }
`;

export const Header = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;

  h1 {
    color: ${COLORS.Black};
    font-size: 22px;
    font-weight: 700;
    margin-bottom: 4px;
  }

  p {
    font-size: 16px;
    color: ${COLORS.Gray2};
    font-weight: 500;
  }

  @media (max-width: 500px) {
    flex-direction: column;
    align-items: flex-start;
    justify-content: flex-start;
  }
`;

export const CriarProjetoCTA = styled.div`
  padding: 10px 24px;
  font-size: 15px;
  font-weight: 600;
  color: #fff;
  font-family: Poppins;

  background: ${COLORS.PrimaryDark};
  border-radius: 8px;

  // animation
  transition: 0.2s;
  cursor: pointer;

  &:hover {
    opacity: 0.8;
    border-radius: 10px;
  }

  @media (max-width: 500px) {
    margin-top: 16px;
}
`;

export const ProjetosList = styled.div`
  display: grid;
  width: 100%;
  grid-template-columns: repeat(3, 32%);
  justify-content: space-between;
  margin-top: 24px;

  @media (max-width: 970px) {
    grid-template-columns: repeat(2, 49%);
  }

  @media (max-width: 670px) {
    grid-template-columns: repeat(1, 100%);
  }
`;
