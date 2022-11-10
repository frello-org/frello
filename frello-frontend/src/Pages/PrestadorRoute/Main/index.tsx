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
import {
  Container,
  CTASearch,
  LeftOptions,
  LeftSide,
  Option,
  Pesquisa,
  RightSide,
  TagInput,
  TagList,
} from "./styles";

interface Cat {
  categories: string[];
}

// this is not efficient and i am not proud of it
function filter(list: Cat[], tagList: string[]): any[] {
  return list.filter((item) => {
    if (tagList.length == 0) {
      return true;
    }

    for (const cat of item.categories) {
      if (tagList.includes(cat)) {
        return true;
      }
    }
    return false;
  });
}

const MainPrestador: React.FC = () => {
  // states
  const [option, setOption] = useState(0);
  const [tagList, setTagList] = useState<string[]>([]);
  const [text, setText] = useState("");

  // API Load
  const [list, setList] = useState<any[]>([]);
  const [current, setCurrent] = useState<any>({});

  const loadFromApi = async () => {
    const token = localStorage.getItem("@frelloToken");

    if (token) {
      await API.get("/service-requests/", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then(async (data) => {
          data.data.data.forEach((i: any) => {
            let gamb: string[] = [];
            console.log(i.id);
            i.categories.forEach((c: any) => {
              gamb.push(c.name);
            });

            i.categories = gamb;
          });

          await API.get("/services/my-services?mode=AS_PROVIDER", {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }).then((datazinha) => {
            const array1 = data.data.data;
            const array2 = datazinha.data.data;

            const newArr = array1.filter((item: any) =>
              array2.every((item2: any) => item2.request.id != item.id)
            );

            if (newArr.length > 0) {
              setCurrent(newArr[0]);
            }
            setList(newArr);
          });
        })
        .catch(() => {
          console.log("error!");
        });
    } else {
      location.href = "/login";
    }
  };

  useEffect(() => {
    loadFromApi();
  }, []);

  const handleChangeOption = (value: number) => {
    setOption(value);
  };

  const handleAddTag = (tag: string) => {
    setText("");
    if (!tagList.includes(tag)) {
      const tmpTag = tagList;
      tmpTag.push(tag);
      setTagList(tmpTag);
    }
  };

  const handleRemoveTag = (tag: string) => {
    var filteredArray = tagList.filter((e: string) => e !== tag);

    setTagList(filteredArray);
  };

  return (
    <div>
      <Navbar current={0} />
      {/** Big container */}
      <Container>
        {/** Lista */}
        <LeftSide>
          {/** Options */}
          <LeftOptions>
            <Option active={option == 0} onClick={() => handleChangeOption(0)}>
              Parecidos
            </Option>
            <Option active={option == 1} onClick={() => handleChangeOption(1)}>
              Destaque
            </Option>
            <Option active={option == 2} onClick={() => handleChangeOption(2)}>
              Mais Recentes
            </Option>
          </LeftOptions>
          {/** List */}
          <>
            {filter(list, tagList).map((item, index) => (
              <div
                style={{ width: "100%" }}
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
          <Pesquisa>
            {/** Lista de tags digitadas */}
            <TagList>
              {tagList.map((tagValue) => (
                <TagAdded
                  name={tagValue}
                  onRemove={(e: string) => handleRemoveTag(e)}
                  key={tagValue}
                />
              ))}
              <TagInput
                value={text}
                onChange={(e) => {
                  setText(e.target.value);
                  // if space pressed....
                  if (e.target.value.slice(-1) == " ") {
                    handleAddTag(e.target.value.slice(0, -1));
                  }
                }}
                type={"text"}
                placeholder={"Digite suas tags"}
              />
            </TagList>
            <CTASearch>
              <FiSearch color="#fff" size={20} />
            </CTASearch>
          </Pesquisa>
          {list.length > 0 && (
            <ProjetoCompleto
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

export default MainPrestador;
