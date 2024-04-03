import React, { useEffect } from 'react';
import { Col } from 'react-bootstrap';
import ProductItems from '@/shared/components/catalog/ProductItems';
import { useDispatch, useSelector } from 'react-redux';
import { fetchCatalogItems } from '@/redux/actions/catalogItemsActions';
import ErrorAlert from '@/shared/components/ErrorAlert';

const CatalogItems = () => {
  
  const { 
    catalogItems, catalogItemsAreLoading, catalogItemsError,
  } = useSelector(state => ({
    catalogItems: state.catalogItems.data,
    catalogItemsAreLoading: state.catalogItems.isFetching,
    catalogItemsError: state.catalogItems.error,
  }));

  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchCatalogItems());
  }, [dispatch]);

  return (
    <>
      <ErrorAlert subTitle="CatalogItems" error={catalogItemsError} />
      <Col md={12} lg={12}>
        <ProductItems 
          items={catalogItems}
          isLoading={catalogItemsAreLoading}
        />
      </Col>
    </>
  );
};

export default CatalogItems;
