import React, { useEffect } from 'react';
import { Container, Row } from 'react-bootstrap';
import { useLocation } from "react-router-dom";
import CatalogItemCard from './components/CatalogItemCard';
import { fetchCatalogItem } from '@/redux/actions/catalogItemActions';
import ErrorAlert from '@/shared/components/ErrorAlert';
import { useDispatch, useSelector } from 'react-redux';

const CatalogItem = () => {
    const location = useLocation();
    const catalogItemId = new URLSearchParams(location.search).get('id');

    const { 
        catalogItem, catalogItemIsLoading, catalogItemError,
    } = useSelector(state => ({
        catalogItem: state.catalogItem.data,
        catalogItemIsLoading: state.catalogItem.isFetching,
        catalogItemError: state.catalogItem.error,
    }));

    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(fetchCatalogItem(catalogItemId));
    }, [dispatch, catalogItemId]);

    return (
        <Container>
            <Row>
                <ErrorAlert subTitle="CatalogItem" error={catalogItemError} />
                <CatalogItemCard
                    item={catalogItem}
                    isLoading={catalogItemIsLoading}
                />
            </Row>
        </Container>
    );
};

export default CatalogItem;