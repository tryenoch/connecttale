import React from 'react';
import {Container} from "react-bootstrap";
import Nav from "./Nav";

function Header(props) {
  return (
    <Container fluid className={"shadow-sm mb-4"}>
      <Nav />
    </Container>
  )
}

export default Header;