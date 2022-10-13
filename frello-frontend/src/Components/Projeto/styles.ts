import styled from "styled-components";
import COLORS from "../../colors";

// Props
interface container {
    active?: boolean;
}


// Design
export const Container = styled.div<container>`
  background-color: #fff;
  padding: 16px 24px;
  border: 1px solid #ebebed;
  border-radius: 12px;
    width: 100%;

  //display
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  flex-direction: column;

  //animation
  transition: 0.3s;
  cursor: pointer;
  &:hover {
    opacity: 0.8;
    box-shadow: 0px 4px 12px rgba(124, 124, 124, 0.2);
  }
  margin-top: 16px;

  // if active
  ${p => p.active && `border: solid 1px ${COLORS.Primary}`}
`;

export const Price = styled.h1`
  color: ${COLORS.Primary};
  font-size: 20px;
  font-weight: 600;
  font-family: Poppins;
`;

export const Title = styled.h2`
  font-size: 16px;
  font-weight: 500;
  font-family: Poppins;
  color: ${COLORS.Black};
  margin-bottom: 4px;
`;

export const Description = styled.p`
  font-size: 14px;
  font-weight: 400;
  color: ${COLORS.Gray2};
  margin-bottom: 24px;
  font-family: Inter;
`;

export const TagContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  flex-wrap: wrap;
`;
