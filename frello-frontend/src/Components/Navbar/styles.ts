import styled from "styled-components";
import COLORS from "../../colors";

// Props
interface navProp {
  active?: boolean;
}

// styles
export const Container = styled.div`
  background: #ffffff;
  border-bottom: 1px solid #ebebed;
  height: 64px;
  width: 100%;

  /* Display at middle */
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const InsideContainer = styled.div`
  max-width: 1170px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;

  @media (max-width: 1220px) {
    width: 100%;
    padding: 0px 24px;
  }
`;

export const LogoContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  img {
    height: 26px;
    width: 23px;
    margin-right: 7px;
  }

  font-size: 20px;
  font-family: Poppins;
  color: ${COLORS.Black};
  font-weight: 600;

  @media (max-width: 500px) {
    display: none;
  }
`;

export const NavsContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const Nav = styled.div<navProp>`
  margin-left: 28px;

  color: ${COLORS.Black};
  font-family: Inter;
  font-weight: 500;
  font-size: 14px;

  transition: 0.2s;

  // if it's active //
  ${(props) =>
    props.active &&
    `
        color: ${COLORS.Primary};
        font-weight: 600;
    `}

  // hover animation
  &:hover {
    opacity: 0.8;
  }
`;
