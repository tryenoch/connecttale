import React from 'react';
import {Container, Navbar} from "react-bootstrap";
import {Link} from "react-router-dom";

function Nav(props) {
  return (
    <Navbar expand={"lg"}>
      <Container>
        <Link to={"/board"}>
          <h3>네비게이션 링크 이동 테스트</h3>
        </Link>
      </Container>
    </Navbar>
  )
}

export default Nav;