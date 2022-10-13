import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import API from "../../../axiosConfig";
import Navbar from "../../../Components/Navbar";
import Projeto from "../../../Components/Projeto";

import { Container, CriarProjetoCTA, Header, ProjetosList } from "./styles";

// import { Container } from './styles';

const EmpregadorMain: React.FC = () => {
  const [loading, setLoading] = useState<boolean>(true);

  /// se fuder, eu n vou fazer a interface gigante desses projeto pra usar uma vez n kkkkkkk
  const [projectList, setProjectList] = useState<any[]>([]);

  const handleLoadProjects = async () => {
    setLoading(true);

    const token = localStorage.getItem("@frelloToken");

    if (token) {
      await API.get("/service-requests/", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((data) => {
          data.data.data.forEach((i: any) => {
            let gambiarraBraba: any = [];
            i.categories.forEach((c: any) => {
              gambiarraBraba.push(c.name);
            });

            i.categories = gambiarraBraba;
          });

          setProjectList(data.data.data);
          //console.log(data.data.data);
        })
        .catch((error) => {
          console.log(error.response.data);
        });
    } else {
      location.href = "/login";
    }
  };

  useEffect(() => {
    handleLoadProjects();
  }, []);

  return (
    <>
      <Navbar current={0} empregador />
      <Container>
        {/** Header */}
        <Header>
          <div>
            <h1>Seus projetos ativos</h1>
            <p>{projectList.length} projetos criados</p>
          </div>
          <Link
            style={{
              textDecoration: "none",
            }}
            to={"/empregador/novoprojeto"}
          >
            <CriarProjetoCTA>Novo Projeto</CriarProjetoCTA>
          </Link>
        </Header>

        {/** Display Projetos */}
        <ProjetosList>
          {projectList.map((item: any) => (
            <Link
              key={item.id}
              to={`/empregador/projeto/${item.id}`}
              style={{
                textDecoration: "none",
              }}
            >
              <Projeto
                price={item.expectedPrice}
                title={item.title}
                description={item.rawMarkdownPageBody}
                tags={item.categories}
              />
            </Link>
          ))}
        </ProjetosList>
      </Container>
    </>
  );
};

export default EmpregadorMain;
