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

  const [interessadas, setInteressadas] = useState<any[]>([]);

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

  const getApplieds = async () => {
    const token = localStorage.getItem("@frelloToken");
    if (token) {
      await API.get(`/service-requests/${id}/applied-providers`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((data) => {
          console.log(data.data.data);
          setInteressadas(data.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  useEffect(() => {
    getProjectInfo();
    getApplieds();
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
              <p>{interessadas.length} interesses</p>
            </header>
            {/** Listagem pessoas interessadas */}
            <PessoasList>
              {interessadas.map((item: any) => (
                <Interessado
                  key={item.id}
                  name={item.username}
                  phone="(31) 9 9959-3050"
                  mail={item.email}
                  tags={[]}
                />
              ))}
            </PessoasList>
          </PessoasContainer>
        </Container>
      )}
    </div>
  );
};

export default EmpregadorProjeto;
