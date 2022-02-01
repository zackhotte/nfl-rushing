import React from "react";
import { Col, Container, Row } from "react-bootstrap";

import RushingTable from "./Components/RushingTable";

const App = () => {
  return (
    <div className="m-5">
      <Container>
        <Row>
          <Col md={4}>
            <h1>NFL Rushing Stats</h1>
          </Col>
          <Col md={{ span: 4, offset: 4 }}></Col>
        </Row>
        <div>
          <RushingTable />
        </div>
      </Container>
    </div>
  );
};

export default App;
