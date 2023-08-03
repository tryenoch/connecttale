import React from 'react';
import {Container} from "react-bootstrap";
import Nav from "./Nav";

function Header(props) {
  return (
    <Container fluid>
      <Nav />
    </Container>
  )
}

export default Header;