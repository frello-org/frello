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
  }
`;

export const TopContainer = styled.div`
  display: flex;

  @media (max-width: 600px) {
    flex-direction: column;
  }
`;

export const Description = styled.div`
  margin-top: 24px;

  p {
    font-size: 14px;
    font-weight: 600;
    color: ${COLORS.Black};
    margin-bottom: 6px;
  }

  textarea {
    outline: none;
    background: #ffffff;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    padding: 10px;
    resize: none;
    width: 42%;
    height: 200px;

    font-family: "Inter";
    font-weight: 500;
    font-size: 14px;

    ::placeholder {
      color: ${COLORS.Gray};
    }

    @media (max-width: 600px) {
      width: 100%;
    }
  }
`;

export const TagListContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  margin-top: 16px;
`;

export const SaveCTA = styled.div`
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
