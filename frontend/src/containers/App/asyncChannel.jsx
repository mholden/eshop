import React, { useEffect } from 'react';
import AsyncChannelLogginIn from './_asyncChannel';
import { useAuth } from 'react-oidc-context';

const AsyncChannel = () => {
    const auth = useAuth();

    return (
        <>
        {
          auth.isAuthenticated && 
          <AsyncChannelLogginIn userId={auth.user?.profile.sub} />
        }
        </>
    );
}

export default AsyncChannel;