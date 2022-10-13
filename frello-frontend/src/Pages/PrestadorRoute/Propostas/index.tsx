import React, { useState, useEffect } from "react";

// == icons == //
import { FiSearch } from "react-icons/fi";
import API from "../../../axiosConfig";

// == Components == //
import Navbar from "../../../Components/Navbar";
import Projeto from "../../../Components/Projeto";
import ProjetoCompleto from "../../../Components/ProjetoCompleto";
import TagAdded from "../../../Components/TagAdded";

// == Styles == //
import { Container, LeftOptions, LeftSide, Option, RightSide } from "./styles";

const PropostasPrestador: React.FC = () => {
  // API Load
  const [list, setList] = useState<any[]>([]);
  const [current, setCurrent] = useState<any>({});

  const handleGetData = async () => {
    const token = localStorage.getItem("@frelloToken");
    if (token) {
      await API.get("/services/my-services?mode=AS_PROVIDER", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }).then((data) => {
        data.data.data.forEach((i: any) => {
          let gamb: string[] = [];
          i.request.categories.forEach((c: any) => {
            gamb.push(c.name);
          });

          i.categories = gamb;
          i.expectedPrice = i.request.expectedPrice;
          i.title = i.request.title;
          i.rawMarkdownPageBody = i.request.rawMarkdownPageBody;
          i.parsedHTMLPageBody = i.request.parsedHTMLPageBody;
        });

        if (data.data.data.length > 0) {
          setCurrent(data.data.data[0]);
        }
        setList(data.data.data);

      });
    } else {
      location.href = "/login";
    }
  };

  useEffect(() => {
    handleGetData();
  }, []);

  return (
    <div>
      <Navbar current={1} />
      {/** Big container */}
      <Container>
        {/** Lista */}
        <LeftSide>
          {/** Options */}
          <LeftOptions>
            <Option active>Marcado como interessado</Option>
          </LeftOptions>
          {/** List */}
          <>
            {list.map((item, index) => (
              <div
                onClick={() => {
                  setCurrent(list[index]);
                  console.log(list[index]);
                }}
              >
                <Projeto
                  active={current.id == item.id}
                  key={item.id}
                  price={item.expectedPrice}
                  title={item.title}
                  description={`${String(item.rawMarkdownPageBody).slice(
                    0,
                    120
                  )}...`}
                  tags={item.categories}
                />
              </div>
            ))}
          </>
        </LeftSide>

        {/** Search */}
        <RightSide>
          {list.length > 0 && (
            <ProjetoCompleto
              interessado
              author={current.consumer.username}
              title={current.title}
              description={current.parsedHTMLPageBody}
              tags={current.categories}
              price={current.expectedPrice}
              onInteressado={async () => {
                console.log("INTERESSADO! ");
                const token = localStorage.getItem("@frelloToken");
                await API.post(
                  `/service-requests/${current.id}/apply-as-provider`,
                  {},
                  {
                    headers: {
                      Authorization: `Bearer ${token}`,
                    },
                  }
                )
                  .then(() => {
                    console.log("enviado com sucesso1");
                  })
                  .catch(() => {
                    alert("Error ao enviar proposta!");
                  });
              }}
            />
          )}
        </RightSide>
      </Container>
    </div>
  );
};

export default PropostasPrestador;
