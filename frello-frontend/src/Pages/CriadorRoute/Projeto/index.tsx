import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import API from "../../../axiosConfig";
import Interessado from "../../../Components/Interessado";
import Navbar from "../../../Components/Navbar";
import ProjetoEmpregador from "../../../Components/ProjetoEmpregador";
import { Container, PessoasContainer, PessoasList } from "./styles";

const EmpregadorProjeto: React.FC = () => {
  // id do projeto
  const { id } = useParams();
  const [loading, setLoading] = useState(true);
  const [project, setProject] = useState<any>();

  const getProjectInfo = async () => {
    const token = localStorage.getItem("@frelloToken");
    setLoading(true);
    if (token) {
      await API.get(`/service-requests/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }).then((data) => {
        let gam: string[] = [];

        data.data.data.categories.forEach((i: any) => {
          gam.push(i.name);
        });

        data.data.data.categories = gam;

        setProject(data.data.data);
        setLoading(false);
      });
    } else {
      location.href = "/login";
    }
  };

  useEffect(() => {
    getProjectInfo();
  }, []);

  return (
    <div>
      <Navbar current={0} empregador />
      {/** Informações do projeto */}
      {!loading && (
        <Container>
          <ProjetoEmpregador
            author={project.consumer.username}
            title={project.title}
            description={project.rawMarkdownPageBody}
            tags={project.categories}
            price={project.expectedPrice}
          />
          {/** Pessoas interessadas */}
          <PessoasContainer>
            <header>
              <h1>Pessoas Interessadas</h1>
              <p>24 interesses</p>
            </header>
            {/** Listagem pessoas interessadas */}
            <PessoasList>
              <Interessado
                name="Eduardo Lemos"
                phone="(31) 9 9959-3050"
                mail="eduardo@email.com"
                tags={["HTML", "Css", "JS"]}
              />
              <Interessado
                name="Daniel Capanema"
                phone="(31) 9 9959-3999"
                mail="daniel@email.com"
                tags={["C", "Java"]}
              />
              <Interessado
                name="Ana Carolina"
                phone="(11) 9 2159-3999"
                mail="ana@hotmail.com"
                tags={["UX", "UI", "React", "Node"]}
              />
            </PessoasList>
          </PessoasContainer>
        </Container>
      )}
    </div>
  );
};

export default EmpregadorProjeto;
