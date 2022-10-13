import axios from "axios";
import React, { useState } from "react";
import { Link } from "react-router-dom";

//@ts-ignore
import freeloIcon from "../../assets/freeloIcon.svg";
import API from "../../axiosConfig";
import COLORS from "../../colors";
import ErrorMessage from "../../Components/ErrorMessage";
import LoginInput from "../../Components/LoginInput";
import ProfileTextInput from "../../Components/ProfileTextInput";
import TagAdded from "../../Components/TagAdded";
import Login from "../Login";

import {
  Container,
  Cta,
  Form,
  FormContainer,
  LeftSide,
  RegisterText,
  RightSide,
  TagListContainer,
  Title,
  UserOption,
} from "./styles";

const Register: React.FC = () => {
  // states
  const [nome, setNome] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  // kind of user
  const [tipoUser, setTipoUser] = useState<number>(0); // 0 = prestador / 1 = empregador

  // tags
  const [currentTag, setCurrentTag] = useState<string>("");
  const [tags, setTags] = useState<string[]>([]);

  const [err, setErr] = useState<any>(null);

  const handleTag = (e: string) => {
    // if has space
    if (e.slice(-1) == " ") {
      setCurrentTag("");
      if (!tags.includes(e)) {
        const tmpTag = tags;
        tmpTag.push(e);
        setTags(tmpTag);
      }
    } else {
      console.log("nao espaco");
      setCurrentTag(e);
    }
  };

  const handleRemoveTag = (tag: string) => {
    var filteredArray = tags.filter((e: string) => e !== tag);

    setTags(filteredArray);
  };

  // === Handle Register === //
  const handleRegister = async () => {
    setErr(null);
    await API.post("/auth/register", {
      firstName: nome,
      lastName: nome,
      username: nome,
      email: email,
      password: password,
      registerAsConsumer: tipoUser == 1,
      registerAsProvider: tipoUser == 0,
    })
      .then(() => {
        console.log("Criado com sucesso!");
        location.href = "/login";
      })
      .catch((err) => {
        setErr(err.response.data.data.message);
        console.log(err.response.data.data.message);
        console.log("Error!");
      });
  };

  return (
    <Container>
      {/** Right-Side */}

      <RightSide>
        {/** Form */}
        <Form>
          <img src={freeloIcon} />
          <Title>Bem vindo de volta!</Title>
          {/** Inputs! */}
          <FormContainer>
            <LoginInput
              label="Seu Nome"
              onChange={(e: string) => {
                setNome(e);
              }}
              value={nome}
              placeholder={"Seu nome"}
            />
            <LoginInput
              label="Seu email"
              onChange={(e: string) => {
                setEmail(e);
              }}
              value={email}
              placeholder={"ex: joao@email.com"}
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
          {/** User type Selector */}
          <UserOption>
            <div
              onClick={() => setTipoUser(0)}
              style={
                tipoUser == 0
                  ? {
                      fontWeight: 600,
                      border: `solid 2px ${COLORS.Primary}`,
                    }
                  : {}
              }
            >
              Sou <br />
              Freelancer
            </div>
            <div
              onClick={() => setTipoUser(1)}
              style={
                tipoUser == 1
                  ? {
                      fontWeight: 600,
                      border: `solid 2px ${COLORS.Primary}`,
                    }
                  : {}
              }
            >
              Tenho <br />
              um projeto
            </div>
          </UserOption>

          {/** If user is freelancer */}
          {tipoUser == 0 && (
            <div>
              <LoginInput
                label="Tags"
                onChange={(e: string) => {
                  handleTag(e);
                }}
                value={currentTag}
                placeholder={"ex: html, css, javascript"}
              />
              <TagListContainer>
                {tags.map((tagValue) => (
                  <TagAdded
                    name={tagValue}
                    onRemove={(e: string) => handleRemoveTag(e)}
                    key={tagValue}
                  />
                ))}
              </TagListContainer>
            </div>
          )}

          <Cta onClick={handleRegister}>Registrar</Cta>
          <RegisterText>
            Já possui uma conta?{" "}
            <Link
              to="/login"
              style={{
                color: COLORS.Gray2,
              }}
            >
              Entrar
            </Link>
          </RegisterText>

          {err && (
            <div
              style={{
                marginTop: 24,
              }}
            >
              <ErrorMessage msg={err} />
            </div>
          )}
        </Form>
      </RightSide>

      {/** Left-Side */}
      <LeftSide />
    </Container>
  );
};

export default Register;
