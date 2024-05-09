import React from 'react';
import { Container, Row } from 'react-bootstrap';
import CatalogItems from './components/CatalogItems';

const Catalog = () => (
  <Container data-testid='catalog-items-container'>
    <Row>
      <CatalogItems />
    </Row>
  </Container>
);

export default Catalog;
