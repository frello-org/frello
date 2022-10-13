import React, { useState, useEffect } from "react";
import API from "../../../axiosConfig";
import MiniTags from "../../../Components/MiniTags";
import Navbar from "../../../Components/Navbar";
import ProfileTextInput from "../../../Components/ProfileTextInput";
import TagAdded from "../../../Components/TagAdded";
import {
  ChangePasswordContainer,
  Container,
  InvalidPassword,
  PerfilContainer,
  PersonalContainer,
  SaveCTA,
  SaveCTADisabled,
  TagList,
} from "./styles";

// import { Container } from './styles';

const ProfilePrestador: React.FC = () => {
  // TextInput value
  const [nome, setNome] = useState<string>("");
  const [email, setEmail] = useState<string>("");

  // tags Value
  const [currentTag, setCurrentTag] = useState<string>("");
  const [tags, setTags] = useState<string[]>([]);

  // newPassword value
  const [senha, setSenha] = useState<string>("");
  const [repeatedPass, setRepeatedPass] = useState<string>("");
  const [senhaValida, setSenhaValida] = useState<boolean>(false);

  // password Check
  const handlePassword = (s: string) => {
    if (s == senha) {
      setSenhaValida(true);
    } else {
      setSenhaValida(false);
    }
  };

  // tagHandlerer
  const handleGetTag = (s: string) => {
    if (s.slice(-1) == " ") {
      if (!tags.includes(s)) {
        let tmpTag = tags;
        tmpTag.push(s);
        setTags(tmpTag);
      }

      setCurrentTag("");
    } else {
      setCurrentTag(s);
    }
  };

  const handleGetInfo = async () => {
    const token = localStorage.getItem("@frelloToken");

    if (token) {
      await API.get("/auth/me", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((data) => {
          setNome(data.data.data.user.username);
          setEmail(data.data.data.user.email);
          setCurrentTag("");

          setSenha("")
        })
        .catch(() => {
          console.log("error...");
        });
    } else {
      location.href = "/login";
    }
  };

  useEffect(() => {
    handleGetInfo();
  }, []);

  return (
    <>
      <Navbar current={2} />
      <Container>
        <PerfilContainer>
          <header>
            <h1>Informações do perfil</h1>
            <p>Nos fale um pouco sobre você!</p>
          </header>
          <PersonalContainer>
            {/** Nome */}
            <ProfileTextInput
              value={nome}
              setValue={(e: string) => setNome(e)}
              label="Nome completo"
              placeholder={"Seu nome"}
              customStyle={{ marginRight: 32 }}
            />
            {/** Email */}
            <ProfileTextInput
              value={email}
              setValue={(e: string) => setEmail(e)}
              label="Email"
              placeholder={"ex: email@email.com"}
            />
          </PersonalContainer>

          {/** Tags */}
          <div>
            <ProfileTextInput
              value={currentTag}
              setValue={(e: string) => handleGetTag(e)}
              label="Tags"
              placeholder={"ex: Java, C, C++"}
            />
            <TagList>
              {tags.map((tagValue) => (
                <TagAdded
                  key={tagValue}
                  name={tagValue}
                  onRemove={() => {
                    var filteredArray = tags.filter(
                      (e: string) => e !== tagValue
                    );

                    setTags(filteredArray);
                  }}
                />
              ))}
            </TagList>
            <div
              style={{
                display: "flex",
              }}
            >
              <SaveCTA>Salvar Informações</SaveCTA>
            </div>
          </div>
        </PerfilContainer>
        {/** Change new password! */}
        <ChangePasswordContainer>
          <header>
            <h1>Alterar Senha</h1>
          </header>
          <ProfileTextInput
            value={senha}
            setValue={(e: string) => setSenha(e)}
            label="Nova senha"
            isPassword
            placeholder={"* * * * * *"}
          />
          <ProfileTextInput
            value={repeatedPass}
            setValue={(e: string) => {
              setRepeatedPass(e);
              handlePassword(e);
            }}
            label="Digite novamente"
            isPassword
            placeholder={"* * * * * *"}
          />
          {!senhaValida && (
            <InvalidPassword>As senhas não coincidem</InvalidPassword>
          )}
          {/** Save password CTA */}
          <div
            style={{
              display: "flex",
            }}
          >
            {senhaValida && <SaveCTA>Salvar</SaveCTA>}
            {!senhaValida && <SaveCTADisabled>Salvar</SaveCTADisabled>}
          </div>
        </ChangePasswordContainer>
      </Container>
    </>
  );
};

export default ProfilePrestador;
