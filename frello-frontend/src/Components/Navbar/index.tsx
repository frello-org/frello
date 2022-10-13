import React from "react";
import { Link } from "react-router-dom";

/* Assets */
//@ts-ignore
import freelo from "../../assets/freelo.svg";

/* Styles */
import {
  Container,
  InsideContainer,
  LogoContainer,
  Nav,
  NavsContainer,
} from "./styles";

interface Navbar {
  current: number;
  empregador?: boolean;
}

/* 
  Desculpa,
  Queria criar um componente navbar para cada um, mas fiquei com mta preguiça.
  =D
*/

const Navbar: React.FC<Navbar> = ({ current, empregador }) => {
  return !empregador ? (
    <Container>
      <InsideContainer>
        {/** Logo */}
        <LogoContainer>
          <img src={freelo} />
          Freelo
        </LogoContainer>

        {/** Navs Controller */}
        <NavsContainer>
          <Link
            style={{
              textDecoration: "none",
            }}
            to={"/prestador"}
          >
            <Nav active={current == 0}>Encontrar Projetos</Nav>
          </Link>
          <Link
            style={{
              textDecoration: "none",
            }}
            to={"/prestador/propostas"}
          >
            <Nav active={current == 1}>Propostas</Nav>
          </Link>
          <Link
            style={{
              textDecoration: "none",
            }}
            to={"/prestador/profile"}
          >
            <Nav active={current == 2}>Seu perfil</Nav>
          </Link>
        </NavsContainer>
      </InsideContainer>
    </Container>
  ) : (
    <Container>
      <InsideContainer>
        {/** Logo */}
        <LogoContainer>
          <img src={freelo} />
          Freelo
        </LogoContainer>

        {/** Navs Controller */}
        <NavsContainer>
          <Link
            style={{
              textDecoration: "none",
            }}
            to={"/empregador"}
          >
            <Nav active={current == 0}>Meus Projetos</Nav>
          </Link>
          <Link
            style={{
              textDecoration: "none",
            }}
            to={"/empregador/profile"}
          >
            <Nav active={current == 1}>Seu perfil</Nav>
          </Link>
        </NavsContainer>
      </InsideContainer>
    </Container>
  );
};

export default Navbar;
