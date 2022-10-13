import styled from "styled-components";
import COLORS from "../../../colors";

// Props
interface optionsProp {
  active?: boolean;
}

// Styles
export const Container = styled.div`
  max-width: 1170px;
  width: 100%;
  margin: 0px auto;
  margin-top: 32px;

  display: flex;
  align-items: flex-start;
  justify-content: space-between;

  @media (max-width: 1220px) {
    width: 100%;
    padding: 0px 24px;
  }

  @media (max-width: 830px) {
    flex-direction: column-reverse;
    align-items: center;
  }
`;

export const LeftSide = styled.div`
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  flex-direction: column;
  width: 33%;

  @media (max-width: 1000px) {
    width: 43%;
  }

  @media (max-width: 830px) {
    width: 100%;
    margin-top: 64px;
  }
`;

export const LeftOptions = styled.div`
  background: #ffffff;
  border: 1px solid #ebebed;
  border-radius: 12px;
  width: 100%;
  padding: 6px 8px;

  display: flex;
  align-items: center;
  justify-content: space-between;
`;

export const Option = styled.div<optionsProp>`
  padding: 10px 18px;
  background: ${(p) => (p.active ? "#E9F6F0" : "")};
  border-radius: 8px;

  // text
  color: ${(p) => (p.active ? COLORS.Primary : COLORS.Gray2)};
  font-size: 14px;
  font-weight: ${(p) => (p.active ? "600" : "500")};
  font-family: Inter;

  // effects
  transition: 0.2s;
  cursor: pointer;
  &:hover {
    opacity: 0.8;
  }
`;

export const RightSide = styled.div`
  display: flex;
  align-items: flex-end;
  justify-content: flex-end;
  flex-direction: column;
  margin-top: -24px;
  width: 63%;
  @media (max-width: 1000px) {
    width: 55%;
  }
  @media (max-width: 830px) {
    width: 100%;
  }
`;

export const Pesquisa = styled.div`
  background: #ffffff;
  border: 1px solid #ebebed;
  border-radius: 12px;
  width: 100%;
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

export const TagList = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin-left: 16px;

  padding-right: 12px;
`;

export const TagInput = styled.input`
  border: none;
  outline: none;
  height: 100%;
  border-left: solid 1px ${COLORS.Primary};
  padding-left: 12px;

  // text
  font-size: 14px;
  font-weight: 500;
  font-family: Poppins;
  color: ${COLORS.Gray2};

  ::placeholder {
    color: ${COLORS.Gray};
    font-weight: 400;
    opacity: 1;
  }

  @media (max-width: 1000px) {
    width: 130px;
  }
`;

export const CTASearch = styled.div`
  background-color: ${COLORS.Primary};
  height: 100%;
  width: 48px;
  border-radius: 0px 8px 8px 0px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: 0.2s;
  cursor: pointer;

  &:hover {
    opacity: 0.8;
  }
`;
