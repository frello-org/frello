import EmpregadorMain from "./Pages/CriadorRoute/Main";
import Login from "./Pages/Login";
import MainPrestador from "./Pages/PrestadorRoute/Main";
import ProfilePrestador from "./Pages/PrestadorRoute/Profile";

import { BrowserRouter, Routes, Route } from "react-router-dom";
import React from "react";
import PropostasPrestador from "./Pages/PrestadorRoute/Propostas";
import EmpregadorProjeto from "./Pages/CriadorRoute/Projeto";
import EmpregadorProfile from "./Pages/CriadorRoute/Profile";
import EmpregadorCriarProjeto from "./Pages/CriadorRoute/CriarProjeto";
import Register from "./Pages/Register";

function App() {
  return (
    <React.StrictMode>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          <Route path="/empregador" element={<EmpregadorMain />} />
          <Route
            path="/empregador/projeto/:id"
            element={<EmpregadorProjeto />}
          />
          <Route path="/empregador/profile" element={<EmpregadorProfile />} />
          <Route
            path="/empregador/novoprojeto"
            element={<EmpregadorCriarProjeto />}
          />

          <Route path="/prestador" element={<MainPrestador />} />
          <Route path="/prestador/propostas" element={<PropostasPrestador />} />
          <Route path="/prestador/profile" element={<ProfilePrestador />} />
        </Routes>
      </BrowserRouter>
    </React.StrictMode>
  );
}

export default App;
