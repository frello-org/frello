import { createGlobalStyle } from "styled-components";
import COLORS from "./colors";

export default createGlobalStyle`
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    scroll-behavior: smooth;
}
body {
    background: ${COLORS.Background};
    font-family: Inter, sans-serif;
    scroll-behavior: smooth;
    color: ${COLORS.Black};
}
::-webkit-scrollbar{
    width: 0px;
}
`;
