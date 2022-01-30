import React from "react";
import { Col, Container, Row } from "react-bootstrap";

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
      </Container>
    </div>
  );
};

export default App;
