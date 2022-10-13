import styled from "styled-components";
import COLORS from "../../../colors";

export const Container = styled.div`
  max-width: 1170px;
  width: 100%;
  margin: 0px auto;
  margin-top: 48px;
  display: flex;
  align-items: flex-start;
  justify-content: space-between;

  @media (max-width: 1220px) {
    width: 100%;
    padding: 0px 24px;
  }

  @media (max-width: 700px) {
    flex-direction: column;
  }
`;

export const PerfilContainer = styled.div`
  width: 60%;
  padding: 24px;
  background: #ffffff;
  border: 1px solid #ebebed;
  border-radius: 12px;

  header {
    border-bottom: solid 1px #eeeeee;
    padding-bottom: 8px;

    h1 {
      font-size: 16px;
      font-family: Inter;
      font-weight: 600;
      color: ${COLORS.Black};
      margin-bottom: 4px;
    }

    p {
      font-size: 14px;
      color: ${COLORS.Gray2};
    }
  }

  @media (max-width: 1100px) {
    width: 50%;
  }

  @media (max-width: 700px) {
    width: 100%;
  }
`;

export const PersonalContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  width: 100%;

  @media (max-width: 975px) {
    flex-direction: column;
    align-items: flex-start;
  }
`;

export const TagList = styled.div`
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  flex-direction: row;
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

export const ChangePasswordContainer = styled.div`
  width: 30%;
  padding: 24px;
  background: #ffffff;
  border: 1px solid #ebebed;
  border-radius: 12px;

  header {
    border-bottom: solid 1px #eeeeee;
    padding-bottom: 8px;

    h1 {
      font-size: 16px;
      font-family: Inter;
      font-weight: 600;
      color: ${COLORS.Black};
      margin-bottom: 4px;
    }
  }

  @media (max-width: 1100px) {
    width: 40%;
  }

  @media (max-width: 700px) {
    width: 100%;
    margin-top: 48px;
  }
`;

export const InvalidPassword = styled.p`
  font-size: 14px;
  color: #ff0000;
  margin-top: 4px;
`;

export const SaveCTADisabled = styled.div`
  padding: 10px 24px;
  font-size: 15px;
  font-weight: 600;
  color: #fff;
  font-family: Poppins;

  background: ${COLORS.Gray};
  border-radius: 8px;
  margin-top: 24px;
`;
