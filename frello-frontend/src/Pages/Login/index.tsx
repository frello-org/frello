import React, { useState } from "react";
import { Link } from "react-router-dom";

//@ts-ignore
import freeloIcon from "../../assets/freeloIcon.svg";
import API from "../../axiosConfig";
import COLORS from "../../colors";
import ErrorMessage from "../../Components/ErrorMessage";
import LoginInput from "../../Components/LoginInput";
import ProfileTextInput from "../../Components/ProfileTextInput";

import {
  Container,
  Cta,
  CtaDisabled,
  Form,
  FormContainer,
  LeftSide,
  RegisterText,
  RightSide,
  Title,
} from "./styles";

const Login: React.FC = () => {
  // states
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const [loading, setLoading] = useState<boolean>(false);

  const [err, setErr] = useState<any>(null);

  const handleLogin = async () => {
    setLoading(true);
    setErr(null);
    await API.post("/auth/login", {
      username: username,
      password,
    })
      .then((data) => {
        setLoading(false);
        localStorage.setItem("@frelloToken", data.data.data.token);
        console.log("ok!");
        // is freelancer
        if (data.data.data.user.provider) {
          console.log("Freelancer!");
          location.href = "/prestador";
        }
        // is prestador
        if (data.data.data.user.consumer) {
          console.log("Criador de Projeto");
          location.href = "/empregador";
        }
      })
      .catch((error) => {
        setErr(error.response.data.data.message);
        setLoading(false);
      });
  };

  return (
    <Container>
      {/** Left-Side */}
      <LeftSide />
      {/** Right-Side */}

      <RightSide>
        {/** Form */}
        <Form>
          <img src={freeloIcon} />
          <Title>Bem vindo de volta!</Title>
          {/** Inputs! */}
          <FormContainer>
            <LoginInput
              label="Seu Usuario"
              onChange={(e: string) => {
                setUsername(e);
              }}
              value={username}
              placeholder={"ex: Joao31"}
            />
            <LoginInput
              label="Sua senha"
              isPassword
              onChange={(e: string) => {
                setPassword(e);
              }}
              value={password}
              placeholder={"*******"}
            />
          </FormContainer>
          {!loading && <Cta onClick={handleLogin}>Entrar</Cta>}
          {loading && <CtaDisabled>Entrar</CtaDisabled>}
          <RegisterText>
            Não tem uma conta?{" "}
            <Link
              to="/register"
              style={{
                color: COLORS.Gray2,
              }}
            >
              Registrar
            </Link>
          </RegisterText>
          {err && <ErrorMessage msg={err} />}
        </Form>
      </RightSide>
    </Container>
  );
};

export default Login;
