import React from 'react';
import { Container, Row } from 'react-bootstrap';
import CatalogItems from './components/CatalogItems';

const Catalog = () => (
  <Container>
    <Row>
      <CatalogItems />
    </Row>
  </Container>
);

export default Catalog;
