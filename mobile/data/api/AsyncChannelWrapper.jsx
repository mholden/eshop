import React from 'react';
import AsyncChannel from './AsyncChannel';
import { useAuth } from '@/hooks/AuthProvider';

const AsyncChannelWrapper = () => {
    const auth = useAuth();

    //console.log("AsyncChannelWrapper() isLoggedIn",auth.isLoggedIn,"userInfo",auth.userInfo);

    return (
        <>
        {
          auth.isLoggedIn && 
          <AsyncChannel userId={auth.userInfo.sub} />
        }
        </>
    );
}

export default AsyncChannelWrapper;