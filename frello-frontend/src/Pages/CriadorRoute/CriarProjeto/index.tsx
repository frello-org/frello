import React, { useState, useEffect } from "react";
import API from "../../../axiosConfig";
import Navbar from "../../../Components/Navbar";
import ProfileTextInput from "../../../Components/ProfileTextInput";
import TagAdded from "../../../Components/TagAdded";
import {
  Container,
  Description,
  PessoasContainer,
  SaveCTA,
  TagListContainer,
  TopContainer,
} from "./styles";

// import { Container } from './styles';

const EmpregadorCriarProjeto: React.FC = () => {
  // states
  const [title, setTitle] = useState<string>("");
  const [price, setPrice] = useState<string>("");
  const [dec, setDec] = useState<string>("");

  // tags
  const [currentTag, setCurrentTag] = useState<string>("");
  const [tagList, setTagList] = useState<string[]>([]);

  const [serverTags, setServerTags] = useState<any[]>([]);

  const handleTag = (e: string) => {
    // if space pressed....
    if (e.slice(-1) == " ") {
      setCurrentTag("");

      if (!tagList.includes(e)) {
        const tmpTag = tagList;
        tmpTag.push(e);
        setTagList(tmpTag);
      }
    } else {
      setCurrentTag(e);
    }
  };

  const handleRemoveTag = (tag: string) => {
    var filteredArray = tagList.filter((e: string) => e !== tag);

    setTagList(filteredArray);
  };

  const handleCreateNew = async () => {
    // locating TAGS that exists on serverTags
    let arrTags: string[] = [];
    tagList.forEach((i: string) => {
      serverTags.forEach((t: any) => {
        if (
          String(t.name).toLowerCase().replaceAll(" ", "") ==
          String(i).toLowerCase().replaceAll(" ", "")
        ) {
          arrTags.push(t.id);
        }
      });
    });

    // all the tags were located, now create!

    const expectedPrice = price.replace(",", ".");

    const token = localStorage.getItem("@frelloToken");

    await API.post(
      "/service-requests/",
      {
        expectedPrice,
        title,
        rawMarkdownPageBody: dec,
        categoryIDs: arrTags,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
      .then(() => {
        location.href = "/empregador";
      })
      .catch((err) => {
        alert("Houve um error ao criar!");
      });
  };

  const handleGetServerTags = async () => {
    const token = localStorage.getItem("@frelloToken");

    if (token) {
      await API.get("/service-categories/", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((data) => {
          setServerTags(data.data.data);
        })
        .catch(() => {
          location.href = "/login";
        });
    } else {
      location.href = "/login";
    }
  };

  useEffect(() => {
    handleGetServerTags();
  }, []);

  return (
    <div>
      <Navbar current={0} empregador />
      {/** Informações do projeto */}
      <Container>
        <PessoasContainer>
          <header>
            <h1>Novo projeto</h1>
          </header>
          {/** Informações do projeto */}

          <TopContainer>
            <ProfileTextInput
              label="Título"
              placeholder="Titulo do projeto"
              value={title}
              setValue={(e: string) => setTitle(e)}
              customStyle={{ marginRight: 48 }}
            />
            <ProfileTextInput
              label="Remuneração"
              placeholder="R$ .."
              value={price}
              setValue={(e: string) => setPrice(e)}
            />
          </TopContainer>

          {/** Descrição */}

          <Description>
            <p>Descrição do projeto</p>
            <textarea
              placeholder="Nos diga um pouco mais sobre o projeto"
              value={dec}
              onChange={(e) => {
                setDec(e.target.value);
              }}
            />
          </Description>

          {/** Tags */}
          <div>
            <ProfileTextInput
              label="Tags"
              placeholder="ex: Js, Html, Css..."
              value={currentTag}
              setValue={(e: string) => handleTag(e)}
            />
            <TagListContainer>
              {tagList.map((tagValue) => (
                <TagAdded
                  name={tagValue}
                  onRemove={(e: string) => handleRemoveTag(e)}
                  key={tagValue}
                />
              ))}
            </TagListContainer>
          </div>
          <div
            style={{
              display: "flex",
            }}
          >
            <SaveCTA onClick={handleCreateNew}>Criar Projeto</SaveCTA>
          </div>
        </PessoasContainer>
      </Container>
    </div>
  );
};

export default EmpregadorCriarProjeto;
